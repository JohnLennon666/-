package com.rlue.storesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rlue.storesystem.R;
import com.rlue.storesystem.entity.Goods;

import java.util.ArrayList;

public class GoodsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Goods> goods;
    public GoodsAdapter(Context context, ArrayList<Goods> goods){
        inflater = LayoutInflater.from(context);
        if(goods!=null){
            this.goods = goods;
        }else{
            this.goods = new ArrayList<>();
        }

    }
    public void removeItem(int position){
        if(position >=0 && position<= goods.size()){
            goods.remove(position);
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
        return goods.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return goods.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return goods.get(position).getId();
    }
    /**
     * 返回指定位置数据绑定的item convertView重用
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.tvId = convertView.findViewById(R.id.item_ID);
            holder.tvName =  convertView.findViewById(R.id.item_name);
            holder.tvPrice =  convertView.findViewById(R.id.item_price);
            holder.tvCount =  convertView.findViewById(R.id.item_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //获取数据
        Goods good =goods.get(position);
        // 将数据绑定到item界面上
        holder.tvId.setText("商品编码:"+good.getId() + "");
        holder.tvName.setText("商品名称:"+good.getName());
        holder.tvPrice.setText("商品单价:"+good.getPrice());
        holder.tvCount.setText("商品数量:"+good.getCount());
        return convertView;
    }
    class ViewHolder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvCount;

    }
    public void changeData(ArrayList<Goods>goods){
        this.goods=goods;
        this.notifyDataSetChanged();
    }
}
