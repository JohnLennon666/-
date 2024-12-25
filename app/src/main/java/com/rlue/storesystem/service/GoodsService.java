package com.rlue.storesystem.service;

import android.content.Context;

import com.rlue.storesystem.dao.GoodsDao;
import com.rlue.storesystem.entity.Goods;

import java.util.ArrayList;

public class GoodsService {
    private GoodsDao dao ;
    public GoodsService(Context context){
        dao = new GoodsDao(context);
    }

    public void addGoods(Goods f){
        dao.addGoods(f);
    }
    public ArrayList<Goods> getAllGoods(){
        return dao.searchAll();
    }
    public void removeGoods(int position){
        dao.removeGoods(position);
    }
    public void updateGoods(String name,String price,String count,int id){
        dao.updateGoods(name,price,count,id);
    }
}
