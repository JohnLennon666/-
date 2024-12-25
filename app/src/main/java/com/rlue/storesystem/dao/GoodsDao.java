package com.rlue.storesystem.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rlue.storesystem.entity.Goods;

import java.util.ArrayList;

public class GoodsDao {
    private ArrayList<Goods> goods ;
    MyDatabaseHelper helper;
    SQLiteDatabase db;

    public GoodsDao(Context context){
        goods = new ArrayList<>();
        helper = new MyDatabaseHelper(context,"storeSystem.db",null,1);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from goods",null);
        if(!cursor.moveToNext()){
            Goods f = new Goods(1,"yifu" ,"120","100");
            addGoods(f);
        }
        db.close();
    }
    public void addGoods(Goods f){
        db = helper.getWritableDatabase();
        db.execSQL("insert into goods(name,price,count) values('"+f.getName()+"','"+f.getPrice()+"','"+f.getCount()+" ')");
        db.close();
    }



    public void removeGoods(int id){
        //删除集合中的对象
        db = helper.getWritableDatabase();
        db.execSQL("delete from goods where id = "+id);
        db.close();
    }


    /**
     * 查询所有的商品
     */
    @SuppressLint("Range")
    public ArrayList<Goods> searchAll(){
        goods = new ArrayList<>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from goods",null);
        while (cursor.moveToNext()){
            Goods f = new Goods();
            f.setId(cursor.getInt(cursor.getColumnIndex("id")));
            f.setName(cursor.getString(cursor.getColumnIndex("name")));
            f.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            f.setCount(cursor.getString(cursor.getColumnIndex("count")));
            goods.add(f);
        }
        cursor.close();
        db.close();
        return goods;
    }

    public void updateGoods(String name,String price,String count,int id){
        db = helper.getWritableDatabase();
        db.execSQL("update goods set name='"+name+"' ,price='"+price+"' ,count='"+count+"' where id="+id );
        db.close();
    }
}
