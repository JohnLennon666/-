package com.rlue.storesystem.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;
import com.rlue.storesystem.adapter.GoodsAdapter;
import com.rlue.storesystem.entity.Goods;
import com.rlue.storesystem.service.GoodsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_goods extends AppCompatActivity {
    private GoodsAdapter adapter;
    private GoodsService biz;
    private ArrayList<Goods> goods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        biz = new GoodsService(this);
        goods = biz.getAllGoods();

        ListView lv = findViewById(R.id.lv);
        adapter = new GoodsAdapter(this, goods);
        registerForContextMenu(lv);
        initDataList();
        lv.setAdapter(adapter);
        //注册菜单
        this.registerForContextMenu(lv);

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> ret());
    }

    private void initDataList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", "商品编号：" + i);
            map.put("name", "商品名称：");
            map.put("price", "商品单价：");
            map.put("count", "商品数量：");
            list.add(map);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("操作");
        // 添加上下文菜单
        menu.add(1, 0, 1, "详情");
        menu.add(1, 1, 1, "修改");
        menu.add(1, 2, 1, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 获取被长按项的数据
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Goods f = (Goods) adapter.getItem(position);

        switch (item.getItemId()) {
            case 0:
                Toast.makeText(Activity_goods.this, "你选择的是" + item.getTitle(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("详情").setCancelable(false);
                builder.setPositiveButton("确定", null).create();
                builder.setMessage("商品编号：" + f.getId() + "商品名称：" + f.getName() + "商品单价：" + f.getPrice() + "商品数量：" + f.getCount());
                builder.show();
                break;
            case 1:
                f = (Goods) adapter.getItem(position);
                Intent intent = new Intent(Activity_goods.this, UpdateGoodsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", f.getId() + "");
                bundle.putString("name", f.getName());
                bundle.putString("price", f.getPrice());
                bundle.putString("count", f.getCount());
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
                break;
            case 2:
                f = (Goods) adapter.getItem(position);
                biz.removeGoods(f.getId());
                goods = biz.getAllGoods();
                adapter.changeData(goods);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.menu_add) {
            intent = new Intent(Activity_goods.this, AddGoodsActivity.class);
            startActivityForResult(intent, 1);
        } else if (item.getItemId() == R.id.menu_exit) {
            finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                Bundle loginBundle = data.getExtras();
                String name = loginBundle.getString("name");
                String price = loginBundle.getString("price");
                String count = loginBundle.getString("count");
                Goods f = new Goods();
                f.setName(name);
                f.setPrice(price);
                f.setCount(count);
                biz.addGoods(f);
                Toast.makeText(Activity_goods.this, "添加成功", Toast.LENGTH_LONG).show();
                adapter.changeData(biz.getAllGoods());
                adapter.notifyDataSetChanged();
                break;
            case 2:
                Bundle uBundle = data.getExtras();
                String uname = uBundle.getString("name");
                String uprice = uBundle.getString("price");
                String ucount = uBundle.getString("count");
                String sid = uBundle.getString("id");
                int id = Integer.parseInt(sid);
                Toast.makeText(Activity_goods.this, uname + uprice + ucount + id, Toast.LENGTH_LONG).show();
                biz.updateGoods(uname, uprice, ucount, id);
                adapter.changeData(biz.getAllGoods());
                adapter.notifyDataSetChanged();

        }
    }

    public void ret() {
        Intent i = new Intent(Activity_goods.this, MainActivity.class);
        //启动
        startActivity(i);
    }
}
