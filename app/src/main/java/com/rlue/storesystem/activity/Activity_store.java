package com.rlue.storesystem.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;
import com.rlue.storesystem.dao.MyDatabaseHelper;

public class Activity_store extends AppCompatActivity {
    private EditText name;
    private EditText addcou;
    private EditText outquantity;
    MyDatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        //返回箭头
        ImageButton re = findViewById(R.id.back);
        re.setOnClickListener(v -> ret());

        helper = new MyDatabaseHelper(Activity_store.this, "storeSystem.db", null, 1);
        //入库（增加商品数量）
        Button inshore = findViewById(R.id.in);
        inshore.setOnClickListener(v -> {
            db = helper.getWritableDatabase();
            inshorehouse();
            db.close();
        });
        //出库
        Button outshore = findViewById(R.id.out);
        outshore.setOnClickListener(v -> {
            //db = helper.getWritableDatabase();
            outshorehouse();
            //db.close();
        });

        Button queryButton = findViewById(R.id.chazhao);
        queryButton.setOnClickListener(v -> {
            db = helper.getWritableDatabase();
            chazhaoShore();
            db.close();
        });

    }

    //入库函数
    public void inshorehouse() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.goods_ruku, null);

        name = view1.findViewById(R.id.rname);
        addcou = view1.findViewById(R.id.rcount);


        //入库操作
        alertDialog
                .setTitle("入库")
                .setView(view1)
                .setPositiveButton("确定", (dialogInterface, i) -> {

                    //增加商品数量
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    String names = name.getText().toString();
                    Log.d("database", "goods name is " + names);
                    int addcous = Integer.parseInt(addcou.getText().toString());
                    Cursor cursor = db.query("goods", null, "name = ?", new String[]{names}, null, null, null);
                    if (cursor.moveToFirst()) {//把指针移动到第一行，如果没有数据，返回false
                        @SuppressLint("Range") int cous = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
                        Log.d("database", "查询count " + cous);

                        int addcouss = cous + addcous;
                        values.put("count", addcouss);
                    }
                    db.update("goods", values, "name = ?", new String[]{names});
                    db.close();
                    Log.d("database", "入库成功");
                    Toast.makeText(Activity_store.this, "已确定", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(Activity_store.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alertDialog.show();
    }

    //出库函数
    public void outshorehouse() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.goods_chuku, null);

        name = view1.findViewById(R.id.name);
        outquantity = view1.findViewById(R.id.outbound_quantity);

        //出库操作
        alertDialog
                .setTitle("出库")
                .setView(view1)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    //TODO 闪退
                    //增加商品数量
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    String names = name.getText().toString().trim();
                    values.put("name",names);
                    Log.d("database", "goods name is " + names);



                    int outquantitys = Integer.parseInt(outquantity.getText().toString());



                    Cursor cursor = db.query("goods", null, "name = ?",new String[]{names}, null, null, null);

                    Log.d("database","数量为 -> " + cursor.getCount());
                    if (cursor.moveToFirst()) {//把指针移动到第一行，如果没有数据，返回false
                        int cous = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                        Log.d("database", "查询count " + cous);
                        int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                        int outquantityss = Math.max(0,cous - outquantitys);
                        values.put("count", outquantityss);
                        values.put("price",price);
                    }
                    db.update("goods", values, "name = ?", new String[]{names});
                    cursor.close();
                    db.close();





                    Log.d("database", "更新数据");


                    Toast.makeText(Activity_store.this, "已确定", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(Activity_store.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alertDialog.show();
    }

    @SuppressLint("Range")
    public void chazhaoShore() {
        Log.d("database", "查询数据");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.search_goods, null);
        final SQLiteDatabase db = helper.getWritableDatabase();
        name = view1.findViewById(R.id.name);

        db.close();
        //查找操作
        //添加取消
        alertDialog
                .setTitle("查找商品")
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    SQLiteDatabase db1 = helper.getWritableDatabase();
                    String names = name.getText().toString();
                    Log.d("database", "goods name is " + names);
                    Cursor cursor = db1.query("goods", null, "name = ?", new String[]{names}, null, null, null);

                    String[] data = new String[4];
                    if (cursor.moveToFirst()) {//把指针移动到第一行，如果没有数据，返回false
                        do {
                            // 遍历Cursor对象，取出数据并打印
                            //根据列名获取列索引，根据列索引获取列字段值
                            String ids = cursor.getString(cursor.getColumnIndex("id"));
                            String names1 = cursor.getString(cursor.getColumnIndex("name"));
                            Double prices = cursor.getDouble(cursor.getColumnIndex("price"));
                            String counts = cursor.getString(cursor.getColumnIndex("count"));

                            data[0] = "商品编码 :      " + ids;
                            data[1] = "商品名称  :    " + names1;
                            data[2] = "商品单价  :  " + prices;
                            data[3] = "商品数量:  " + counts;
                        } while (cursor.moveToNext());//移动到下一行，如果是最后一条记录的后面，则返回false
                    }

                    //用listview，需注意：
                    setContentView(R.layout.goods_list);
                    //返回菜单
                    Button Button1 = (Button) findViewById(R.id.button2);
                    Button1.setOnClickListener(v -> {
                        Intent i1 = new Intent(Activity_store.this, Activity_store.class);
                        //启动
                        startActivity(i1);
                    });
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_store.this, android.R.layout.simple_list_item_1, data);
                    ListView listView = (ListView) findViewById(R.id.goodslist);
                    listView.setAdapter(adapter);

                    cursor.close();
                    Toast.makeText(Activity_store.this, "已确定", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(Activity_store.this, "已取消", Toast.LENGTH_SHORT).show())
                .setView(view1)
                .create();
        alertDialog.show();

    }

    public void ret() {
        Intent i = new Intent(Activity_store.this, MainActivity.class);
        //启动
        startActivity(i);
    }
}
