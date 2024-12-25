package com.rlue.storesystem.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;
import com.rlue.storesystem.dao.MyDatabaseHelper;


public class RegisterActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText name;
    private EditText pwd;
    private EditText key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDatabaseHelper(RegisterActivity.this, "storeSystem.db", null, 1);

        Button finish = (Button) findViewById(R.id.register);
        finish.setOnClickListener(v -> finish());
        ImageButton re = (ImageButton) findViewById(R.id.back);
        re.setOnClickListener(v -> ret());
    }
    public void finish(){
        name =(EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        key =(EditText) findViewById(R.id.key);
        String names = name.getText().toString();
        Log.d("database", "注册" + names);

        String pwds = pwd.getText().toString();
        String keys = key.getText().toString();
        String token = "1";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // 注册
        values.put("name", names);
        values.put("pwd", pwds);
        values.put("token", token);
        if("admin".equals(keys)){
            db.insert("manager", null, values);
            Toast.makeText(RegisterActivity.this, "成功增加超级管理员", Toast.LENGTH_SHORT).show();
            db.close();
        }else{
            Toast.makeText(RegisterActivity.this, "请输入正确秘钥", Toast.LENGTH_SHORT).show();
            db.close();
        }
    }
    public void ret(){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        //启动
        startActivity(i);
    }
}