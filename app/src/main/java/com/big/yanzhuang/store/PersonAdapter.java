package com.big.yanzhuang.store;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    private int resourceId;
    public PersonAdapter(Context context,int textViewResourceId,List<Person> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position , View convertView, ViewGroup parent){
        Person person = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

//        ImageView personImage = (ImageView) view.findViewById(R.id.phone_img);
        TextView personName = (TextView) view.findViewById(R.id.phone_name);
        TextView personNum = (TextView) view.findViewById(R.id.phone_num);
//        personImage.setImageResource(person.getImageId());
        personName.setText(person.getName());
        personNum.setText(person.getNum());
        return  view;
    }
}
