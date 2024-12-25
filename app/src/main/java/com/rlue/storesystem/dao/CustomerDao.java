package com.rlue.storesystem.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rlue.storesystem.entity.Customer;

import java.util.ArrayList;

public class CustomerDao {
    private ArrayList<Customer> customers;
    MyDatabaseHelper helper;
    SQLiteDatabase db;

    public CustomerDao(Context context) {
        customers = new ArrayList<>();
        helper = new MyDatabaseHelper(context, "storeSystem.db", null, 1);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from  customer", null);
        if (!cursor.moveToNext()) {
            Customer customer = new Customer(1, "zhangsan", "huayi", "ningbo", "7895465@qq.com", "123");
            addCustomer(customer);
        }
        db.close();
    }

    public void addCustomer(Customer customer) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into customer(name,company,address,email,phone) values('" + customer.getName() + "','" + customer.getCompany() + "','" + customer.getAddress() + "','" + customer.getEmail() + "','" + customer.getPhone() + " ')");
        db.close();
    }


    public void removeCustomer(int id) {
        //删除集合中的对象
        db = helper.getWritableDatabase();
        db.execSQL("delete from customer where id = " + id);
        db.close();
    }


    /**
     * 查询所有的商品
     */
    @SuppressLint("Range")
    public ArrayList<Customer> searchAll() {
        customers = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from customer", null);
        while (cursor.moveToNext()) {
            Customer customer = new Customer();
            customer.setId(cursor.getInt(cursor.getColumnIndex("id")));
            customer.setName(cursor.getString(cursor.getColumnIndex("name")));
            customer.setCompany(cursor.getString(cursor.getColumnIndex("company")));
            customer.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            customer.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            customer.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            customers.add(customer);
        }
        cursor.close();
        db.close();
        return customers;
    }

    public void updateCustomer(String name, String company, String address, String email, String phone, int id) {
        db = helper.getWritableDatabase();
        db.execSQL("update customer set name='" + name + "' ,company='" + company + "' ,address='" + address + "',email='" + email + "',phone='" + phone + "' where id=" + id);
        db.close();
    }
}
