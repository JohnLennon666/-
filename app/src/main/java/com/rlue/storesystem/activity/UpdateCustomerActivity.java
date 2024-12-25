package com.rlue.storesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;

public class UpdateCustomerActivity extends AppCompatActivity {
    EditText name,company,address,email,phone;
    Button sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer);
        name = (EditText)findViewById(R.id.nametext);
        company=(EditText)findViewById(R.id.companytext);
        address=(EditText)findViewById(R.id.addresstext);
        email=(EditText)findViewById(R.id.youbiantext);
        phone = (EditText)findViewById(R.id.phonetext);

        final Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("name"));
        company.setText(bundle.getString("company"));
        address.setText(bundle.getString("address"));
        email.setText(bundle.getString("email"));
        phone.setText(bundle.getString("phone"));


        sure = (Button) findViewById(R.id.xiugai1);
        sure.setOnClickListener(v -> {
            String uname = name.getText().toString().trim();
            String ucompany = company.getText().toString().trim();
            String uaddress = address.getText().toString().trim();
            String uyoubian = email.getText().toString().trim();
            String uphone = phone.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("name", uname);
            intent.putExtra("company", ucompany);
            intent.putExtra("address", uaddress);
            intent.putExtra("email", uyoubian);
            intent.putExtra("phone", uphone);
            intent.putExtra("id",bundle.getString("id"));
            setResult(5, intent);
            finish();
        });
    }
}
