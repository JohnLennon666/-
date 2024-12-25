package com.rlue.storesystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rlue.storesystem.R;

public class UpdateGoodsActivity extends Activity {
    EditText name,price,count;
    Button sure;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_goods);
        name=(EditText)findViewById(R.id.nametext);
        price=(EditText)findViewById(R.id.pricetext);
        count=(EditText)findViewById(R.id.counttext);

        final Bundle bundle=getIntent().getExtras();
        name.setText(bundle.getString("name"));
        price.setText(bundle.getString("price"));
        count.setText(bundle.getString("count"));

        sure = (Button) findViewById(R.id.xiugai);
        sure.setOnClickListener(v -> {
            String uname = name.getText().toString().trim();
            String uprice = price.getText().toString().trim();
            String ucount = count.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("id",bundle.getString("id"));
            intent.putExtra("name",uname );
            intent.putExtra("price", uprice);
            intent.putExtra("count",ucount);
            setResult(3, intent);
            finish();
        });
    }
}
