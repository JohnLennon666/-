package com.rlue.storesystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rlue.storesystem.R;

public class AddCustomerActivity extends Activity {
    private EditText name, company, address, email, phone;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);

        name = findViewById(R.id.nametext);
        company = findViewById(R.id.companytext);
        address = findViewById(R.id.addresstext);
        email = findViewById(R.id.youbiantext);
        phone = findViewById(R.id.phonetext);


        bt = findViewById(R.id.buttons);
        bt.setOnClickListener(v -> {
            String thename = name.getText().toString().trim();
            String thecompany = company.getText().toString().trim();
            String theaddress = address.getText().toString().trim();
            String theyoubian = email.getText().toString().trim();
            String thephone = phone.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("name", thename);
            intent.putExtra("company", thecompany);
            intent.putExtra("address", theaddress);
            intent.putExtra("email", theyoubian);
            intent.putExtra("phone", thephone);
            setResult(1, intent);
            finish();
        });

        //返回主页
        ImageButton re =  findViewById(R.id.back);
        re.setOnClickListener(v -> ret());
    }
    public void ret() {
        Intent i = new Intent(AddCustomerActivity.this, MainActivity.class);
        //启动
        startActivity(i);
    }
}
