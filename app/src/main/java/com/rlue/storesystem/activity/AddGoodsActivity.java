package com.rlue.storesystem.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rlue.storesystem.R;

public class AddGoodsActivity extends Activity {
    private EditText name,price,count;
    Button sure;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goods);
        name = (EditText) findViewById(R.id.ed_name);
        price = (EditText) findViewById(R.id.ed_price);
        count = (EditText) findViewById(R.id.ed_count);
        sure = (Button) findViewById(R.id.bt_add);
        sure.setOnClickListener(v -> {
            String thename = name.getText().toString().trim();
            String theprice = price.getText().toString().trim();
            String thecount = count.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("name", thename);
            intent.putExtra("price", theprice);
            intent.putExtra("count", thecount);
            setResult(1, intent);
            finish();
        });
        ImageButton re =  findViewById(R.id.back);
        re.setOnClickListener(v -> ret());

    }
    public void ret() {
        Intent i = new Intent(AddGoodsActivity.this, MainActivity.class);
        //启动
        startActivity(i);
    }
}
