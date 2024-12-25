package com.rlue.storesystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;
import com.rlue.storesystem.dao.MyDatabaseHelper;
import com.rlue.storesystem.entity.Manager;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPwd;
    private LinearLayout Username;
    private Button Submit;
    private LinearLayout Pwd;
    private Button Register;


    private ArrayList<Manager> managerlist;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initView();
    }

    //初始化视图
    private void initView() {

        //username
        Username = findViewById(R.id.login_username);
        etUsername = findViewById(R.id.username);

        //passwd
        Pwd = findViewById(R.id.login_pwd);
        etPwd = findViewById(R.id.password);

        //提交、注册
        Submit = findViewById(R.id.login_submit);
        Register = findViewById(R.id.login_register);


        //注册点击事件
        etUsername.setOnClickListener(this);
        Submit.setOnClickListener(this);
        Register.setOnClickListener(this);
        etPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_username) {
            etPwd.clearFocus();
            etUsername.setFocusableInTouchMode(true);
            etUsername.requestFocus();
        } else if (view.getId() == R.id.login_pwd) {
            etUsername.clearFocus();
            etPwd.setFocusableInTouchMode(true);
            etPwd.requestFocus();
        } else if (view.getId() == R.id.login_submit) {
            //登录
            loginRequest();
        } else if (view.getId() == R.id.login_register) {
            //注册
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }


    public void finish() {
        Log.d("database", "返回");

        Intent i = new Intent(LoginActivity.this, LoginActivity.class);
        //启动
        startActivity(i);
    }

    @SuppressLint("Range")
    //登录
    private void loginRequest() {
        String username = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();


        //查询是否存在
        dbHelper = new MyDatabaseHelper(LoginActivity.this, "storeSystem.db", null, 1);
        managerlist = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 查询Manager表中所有的数据，返回的查询结果放入Cursor对象中
        //Cursor对象就像有一个指针的表
        Cursor cursor = db.query("manager", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {//把指针移动到第一行，如果没有数据，返回false
            do {
                // 遍历Cursor对象，取出数据并打印
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Log.d("database", "名称" + name);
                String pwd1 = cursor.getString(cursor.getColumnIndex("pwd"));
                Log.d("database", "密码" + pwd1);
                String token = cursor.getString(cursor.getColumnIndex("token"));
                Manager st = new Manager(name, pwd1, token);
                managerlist.add(st);
            } while (cursor.moveToNext());//移动到下一行，如果是最后一条记录的后面，则返回false
        }

        for (int m = 0; m < managerlist.size(); m++) {
            if (username.equals(managerlist.get(m).getName())) {
                //判断用户名和密码是否正确（可以进行测试，将用户名和密码都定义为admin）
                if (username.equals(managerlist.get(m).getName()) && pwd.equals(managerlist.get(m).getPwd())) {
                    Toast.makeText(LoginActivity.this, "用户名和密码正确！", Toast.LENGTH_SHORT).show();
                    //查询数据库里的
                    if ("1".equals(managerlist.get(m).getToken())) {
                        Intent i = new Intent(LoginActivity.this, Activity_manager.class);
                        //启动
                        startActivity(i);
                        return;
                    } else {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        //启动
                        startActivity(i);
                        return;
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        cursor.close();
        db.close();
    }
}
