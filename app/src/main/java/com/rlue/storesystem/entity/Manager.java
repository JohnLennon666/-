package com.rlue.storesystem.entity;

public class Manager {
    private String name;
    private String pwd;
    private String token;

    public Manager(String name, String pwd, String token) {
        this.name = name;
        this.pwd = pwd;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
