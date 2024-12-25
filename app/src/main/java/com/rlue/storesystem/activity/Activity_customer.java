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
import com.rlue.storesystem.adapter.CustomerAdapter;
import com.rlue.storesystem.entity.Customer;
import com.rlue.storesystem.service.CustomerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_customer extends AppCompatActivity {
    private List<Map<String, Object>> list;
    private CustomerAdapter adapter;
    private CustomerService customerService;
    private ArrayList<Customer> customers;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        customerService=new CustomerService(this);
        customers = customerService.getAllCustomer();
        lv = findViewById(R.id.lv);
        adapter =new CustomerAdapter(this,customers);
        registerForContextMenu(lv);
        initDataList();
        lv.setAdapter(adapter);
        this.registerForContextMenu(lv);

        ImageButton re =  findViewById(R.id.back);
        re.setOnClickListener(v -> ret());
    }

    /**
     * 初始化数据
     */
    private void initDataList() {
        list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", "序号：" + i);
            map.put("name", "联系人：" );
            map.put("company","公司名称：");
            map.put("address","地址：");
            map.put("email","邮箱：");
            map.put("phone","号码：");
            list.add(map);
        }


    }

    /**
     * 添加上下文菜单
     *
     * @param menu The context menu that is being built
     * @param v The view for which the context menu is being built
     * @param menuInfo Extra information about the item for which the
     *            context menu should be shown. This information will vary
     *            depending on the class of v.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
        menu.setHeaderTitle("操作");
        menu.add(1, 0, 1, "详情");
        menu.add(1, 1, 1, "修改");
        menu.add(1, 2, 1,"删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * 根据选项执行相应功能
     *
     * @param item The context menu item that was selected.
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 获取被长按项的数据
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Customer f = (Customer)adapter.getItem(position);
        switch(item.getItemId()){
            case 0:
                //查询
                Toast.makeText(Activity_customer.this, "你选择的是"+item.getTitle(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setTitle("详情").setCancelable(false);
                builder.setPositiveButton("确定",null).create();
                builder.setMessage("   序号："+f.getId()+"   联系人："+f.getName()+"   公司："+f.getCompany()+"   地址："+f.getAddress()+"    邮箱："+f.getEmail()+"   电话："+f.getPhone());
                builder.show();
                break;
            case 1:
                //修改
                f = (Customer) adapter.getItem(position);
                Intent intent = new Intent(Activity_customer.this,UpdateCustomerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",f.getId()+"");
                bundle.putString("name",f.getName());
                bundle.putString("company",f.getCompany());
                bundle.putString("address",f.getAddress());
                bundle.putString("email",f.getEmail());
                bundle.putString("phone",f.getPhone());
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
                break;
            case 2:
                //删除
                f = (Customer) adapter.getItem(position);
                customerService.removeCustomer(f.getId());
                adapter.changeData(customerService.getAllCustomer());
                adapter.notifyDataSetChanged();
            default:
                break;
        }
        return super.onContextItemSelected(item);

    }

    /**
     * 创建选择菜单
     * @param menu The options menu in which you place your items.
     *
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * 根据选择执行相应功能
     * @param item The menu item that was selected.
     *
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.menu_add) {
            intent = new Intent(Activity_customer.this, AddCustomerActivity.class);
            startActivityForResult(intent, 1);
        } else if (item.getItemId() == R.id.menu_exit) {
            finish();
            return true;
        }
        return true;
    }

    /**
     * 进行数据操作
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                Bundle loginBundle = data.getExtras();
                String name = loginBundle.getString("name");
                String company = loginBundle.getString("company");
                String address = loginBundle.getString("address");
                String email=loginBundle.getString("email");
                String phone = loginBundle.getString("phone");
                Customer f = new Customer();
                f.setName(name);
                f.setCompany(company);
                f.setAddress(address);
                f.setEmail(email);
                f.setPhone(phone);
                customerService.addCustomer(f);
                Toast.makeText(Activity_customer.this, "添加成功", Toast.LENGTH_LONG).show();
                adapter.changeData(customerService.getAllCustomer());
                adapter.notifyDataSetChanged();
                break;
            case 2:
                Bundle uBundle = data.getExtras();
                String uname = uBundle.getString("name");
                String ucompany = uBundle.getString("company");
                String uaddress = uBundle.getString("address");
                String uyoubian = uBundle.getString("email");
                String uphone = uBundle.getString("phone");
                String sid = uBundle.getString("id");
                int id = Integer.parseInt(sid);
                Toast.makeText(Activity_customer.this,"更新成功",Toast.LENGTH_LONG).show();
                customerService.updateCustomer(uname,ucompany,uaddress,uyoubian,uphone,id);
                adapter.changeData(customerService.getAllCustomer());
                adapter.notifyDataSetChanged();
                break;
        }

    }
    public void ret(){
        Intent i = new Intent(Activity_customer.this, MainActivity.class);
        //启动
        startActivity(i);
    }
}
