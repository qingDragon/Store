package com.big.yanzhuang.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
     private List<OrderList> data_list;
     private LayoutInflater inflater;
     public MyAdapter(Context context,List data_list){
         inflater = LayoutInflater.from(context);
         this.data_list = data_list;
     }


    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.add_item,parent,false);
            holder = new ViewHolder();
            holder.nameTV = (TextView) convertView.findViewById(R.id.name);
            holder.dateTV = (TextView) convertView.findViewById(R.id.date);
            holder.contentTV = (TextView) convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        } else {
            holder =(ViewHolder) convertView.getTag();
        }
        OrderList orderList = data_list.get(position);
        holder.nameTV.setText(orderList.getUsers());
        holder.dateTV.setText(orderList.getSignuptime());
        holder.contentTV.setText(orderList.getGoodscomments());
        return convertView;
    }
}
