package com.rlue.storesystem.service;

import android.content.Context;

import com.rlue.storesystem.dao.CustomerDao;
import com.rlue.storesystem.entity.Customer;

import java.util.ArrayList;

public class CustomerService {
    private CustomerDao dao;

    public CustomerService(Context context) {
        dao = new CustomerDao(context);
    }

    /**
     * 获取所有的客户信息
     */
    public void addCustomer(Customer f) {
        dao.addCustomer(f);
    }

    public ArrayList<Customer> getAllCustomer() {
        return dao.searchAll();
    }

    public void removeCustomer(int position) {
        dao.removeCustomer(position);
    }

    public void updateCustomer(String name, String company, String address, String email, String phone, int id) {
        dao.updateCustomer(name, company, address, email, phone, id);
    }
}
