package com.rlue.storesystem.entity;

public class Goods {
    private int id;
    private String name;
    private String price;
    private String count;

    public Goods() {
    }

    public Goods(int id, String name, String price, String count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }


    @Override
    public String toString() {
        return super.toString();
    }

}