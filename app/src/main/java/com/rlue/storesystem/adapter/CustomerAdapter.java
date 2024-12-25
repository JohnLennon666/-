package com.rlue.storesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rlue.storesystem.R;
import com.rlue.storesystem.entity.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Customer> customer;

    class ViewHolder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvCompany;
        private TextView tvAddress;
        private TextView tvEmail;
        private TextView tvPhone;
    }

    public CustomerAdapter(Context context, ArrayList<Customer> customer) {
        inflater = LayoutInflater.from(context);
        if (customer != null) {
            this.customer = customer;
        } else {
            this.customer = new ArrayList<>();
        }

    }

    public void removeItem(int position) {
        if (position >= 0 && position <= customer.size()) {
            customer.remove(position);
        }
        //删除之后通知listview更新数据
        this.notifyDataSetChanged();
    }

    /**
     * 返回当前数据集中的数据条数
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return customer.size();
    }

    @Override
    public Object getItem(int position) {
        return customer.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return customer.get(position).getId();
    }

    /**
     * 返回指定位置数据绑定的item convertView重用
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item1, null);
            holder = new ViewHolder();
            holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCompany = (TextView) convertView.findViewById(R.id.tv_company);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tvEmail = (TextView) convertView.findViewById(R.id.tv_youbian);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //获取数据
        Customer customers = customer.get(position);
        // 将数据绑定到item界面上
        holder.tvId.setText("序号:" + customers.getId() + "");
        holder.tvName.setText("联系人:" + customers.getName());
        holder.tvCompany.setText("公司:" + customers.getCompany());
        holder.tvAddress.setText("地址:" + customers.getAddress());
        holder.tvEmail.setText("邮箱:" + customers.getEmail());
        holder.tvPhone.setText("电话:" + customers.getPhone());
        return convertView;
    }


    public void changeData(ArrayList<Customer> customer) {
        this.customer = customer;
        this.notifyDataSetChanged();
    }
}
