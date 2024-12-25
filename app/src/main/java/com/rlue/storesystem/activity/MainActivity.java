package com.rlue.storesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt1,bt2,bt3,bt4,bt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1=findViewById(R.id.Goods);
        bt2=findViewById(R.id.customer);
        bt3=findViewById(R.id.kucun);
        bt4=findViewById(R.id.setting);
        bt5=findViewById(R.id.exit);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        System.out.println(v);
        if(v==bt1){
            startActivity(new Intent(MainActivity.this, Activity_goods.class));
        }else if(v==bt2){
            startActivity(new Intent(MainActivity.this, Activity_customer.class));
        } else if(v==bt3){
            startActivity(new Intent(MainActivity.this, Activity_store.class));
        }else if(v==bt4){
            startActivity(new Intent(MainActivity.this, Activity_music.class));
        } else if(v==bt5){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }
}
