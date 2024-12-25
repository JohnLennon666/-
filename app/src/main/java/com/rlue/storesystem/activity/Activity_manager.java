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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;
import com.rlue.storesystem.dao.MyDatabaseHelper;
import com.rlue.storesystem.entity.Manager;

import java.util.ArrayList;

public class Activity_manager extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText name;
    private EditText pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workermanage);
        dbHelper = new MyDatabaseHelper(Activity_manager.this, "storeSystem.db", null, 1);


        Button addsm = findViewById(R.id.addsm);
        addsm.setOnClickListener(v -> add());


        Button deletesm = findViewById(R.id.deletesm);
        deletesm.setOnClickListener(v -> minus());

        Button chaxunsm = findViewById(R.id.chaxunsm);
        chaxunsm.setOnClickListener(v -> query());


        ImageButton re = findViewById(R.id.ib_navigation_back);
        re.setOnClickListener(v -> ret());

    }

    public void ret() {
        Intent i = new Intent(Activity_manager.this, LoginActivity.class);
        //启动
        startActivity(i);
    }

    public void minus() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.minusmanager, null);
        name = view1.findViewById(R.id.name);

        //删除管理员操作
        alertDialog
                .setTitle("删除管理员")
                .setView(view1)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    //删除数据
                    String names = name.getText().toString();
                    Log.d("database", "manager name is " + names);
                    db.delete("manager", "name = ?", new String[]{names});
                    db.close();
                    Log.d("database", "删除数据");


                    Toast.makeText(Activity_manager.this, "已删除", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(Activity_manager.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alertDialog.show();


    }

    public void add() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.add_manager, null);
        name = view1.findViewById(R.id.name);
        pwd = view1.findViewById(R.id.pwd);

        //增加管理员操作
        alertDialog
                .setTitle("增加管理员")
                .setView(view1)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    String names = name.getText().toString();
                    String pwds = pwd.getText().toString();
                    String token = "2";

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    // 开始组装第一条数据
                    values.put("name", names);
                    values.put("pwd", pwds);
                    values.put("token", token);
                    //插入数据到表
                    db.insert("manager", null, values);
                    db.close();


                    Toast.makeText(Activity_manager.this, "已增加", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> Toast.makeText(Activity_manager.this, "已取消", Toast.LENGTH_SHORT).show())
                .create();
        alertDialog.show();


    }

    public void query() {
        ArrayList<Manager> managerlist = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 查询Manager表中所有的数据，返回的查询结果放入Cursor对象中
        //Cursor对象就像有一个指针的表
        Cursor cursor = db.query("manager", null, null, null, null, null, null);
        //把指针移动到第一行，如果没有数据，返回false
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                @SuppressLint("Range") String token = cursor.getString(cursor.getColumnIndex("token"));
                Manager st = new Manager(name, pwd, token);
                managerlist.add(st);
            } while (cursor.moveToNext());//移动到下一行，如果是最后一条记录的后面，则返回false
        }
        Log.d("database", "数组" + managerlist.get(0).getName());
        cursor.close();
        db.close();


        setContentView(R.layout.lookmanagerlist);
        //返回菜单
        Button Button1 = findViewById(R.id.button22);
        Button1.setOnClickListener(v -> {
            Intent i = new Intent(Activity_manager.this, Activity_manager.class);
            //启动
            startActivity(i);
        });
        TextView t1 = (TextView) findViewById(R.id.textView4);
        TextView t2 = (TextView) findViewById(R.id.textView5);
        t1.append("\n");
        t2.append("\n");
        //遍历managerlist的数据并通过append方法打印出来
        for (int i = 0; i < managerlist.size(); i++) {
            t1.append(managerlist.get(i).getName() + "    " + "\n");
            t2.append(managerlist.get(i).getPwd() + "    " + "\n");
        }
    }

}
