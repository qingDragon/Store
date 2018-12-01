package com.big.yanzhuang.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonArray;
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
    private ArrayList<OrderList> orderLists = new ArrayList<>();
    public  OrderList orderList = new OrderList();
    public String data;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println(msg.obj.toString());
                    myAdapter = new MyAdapter(SecondActivity.this, (ArrayList)msg.obj);
                    listView.setAdapter(myAdapter);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_second);
        initView();
        getServerdata();
        Log.d("hello","hello");



//        System.out.println(str2order(data).getUsers());
//        Log.d("输出",str2order(data).toString());


    }


    public void initView(){

        listView = findViewById(R.id.list_view);
    }


    public void initData(OrderList orderList){

    }
//    public void initData(){
//        data_list = new ArrayList<OrderList>();
//        OrderList orderList = new OrderList("yz","20180820","抢修");
//        data_list.add(orderList);
//        myAdapter = new MyAdapter(this,data_list);
//        listView.setAdapter(myAdapter);
//    }
    public OrderList str2order(String string){
        String order ;
        JsonObject jsonObject = Tools.str2Json(string);
        String str = jsonObject.get("datas").toString();
        order = str.substring(1,str.length()-1);
        JsonObject jsonObject2 ;
        jsonObject2 = Tools.str2Json(order);
        String users = jsonObject2.get("users").getAsString();
        String signuptime = jsonObject2.get("signuptime").getAsString();
        String goodscomments = jsonObject2.get("goodscomments").getAsString();
        System.out.println(string);
        orderList = new OrderList(users,signuptime,goodscomments);
        System.out.println(users);
        return orderList;
    }
    public void getServerdata(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    data = "";
                    System.out.println("begin");
                    Map<String, String> body = new HashMap<>();
                    Map<String, String> body2 = new HashMap<>();
                    body.put("usernum", "662995");
                    body.put("userpwd", "123456");
                    String s = HttpSend.doPost("https://www.kpcodingoffice.com/index", body);
                    System.out.println(Tools.str2Json(s).get("token").getAsString());
                    body2.put("usernum", "662995");
                    body2.put("token", Tools.str2Json(s).get("token").getAsString());
                    //第二次请求获得的String
                    data = HttpSend.doPost("https://www.kpcodingoffice.com/orderdata", body2);
//                    System.out.println("123456789:" + data);
                    Log.d("data", data);
                    JsonObject jo = Tools.str2Json(data);
                    JsonArray ja = jo.get("datas").getAsJsonArray();
                    for (int i = 0;i<ja.size();i++){
                        jo = ja.get(i).getAsJsonObject();
                        orderLists.add(new OrderList(jo.get("users").getAsString(),jo.get("signuptime").getAsString(),jo.get("goodscomments").getAsString()));
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = orderLists;
                    handler.sendMessage(msg);
 //                   handler.sendEmptyMessage(1);
                    //                    Log.d("name",str2order(data).getUsers());

                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
