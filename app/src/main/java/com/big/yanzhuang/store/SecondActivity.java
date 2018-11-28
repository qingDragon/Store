package com.big.yanzhuang.store;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.big.yanzhuang.store.R.layout.activity_second;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private List<OrderList> data_list;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_second);
//        LayoutInflater inflater = getLayoutInflater();
//
//        View view = inflater.inflate(R.layout.add_item,null);
//
//        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_01);
//        layout.addView(view);


        initView();
        initData(FirstActivity.orderList);
        System.out.println("hello");
//        System.out.println(str2order(data).getUsers());
//        Log.d("输出",str2order(data).toString());


    }


    public void initView(){

        listView = (ListView) findViewById(R.id.list_view);
    }


    public void initData(OrderList orderList){
        data_list = new ArrayList<OrderList>();
        //后面根据订单数量加循环
        data_list.add(orderList);
        myAdapter = new MyAdapter(this,data_list);
        listView.setAdapter(myAdapter);
    }


}
