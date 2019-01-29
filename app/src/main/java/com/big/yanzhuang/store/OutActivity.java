package com.big.yanzhuang.store;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OutActivity extends AppCompatActivity {
    private ArrayList<OrderList> orderLists = new ArrayList<>();
    private ListView listView;
    public String data;
    private  ArrayList order_goods =new ArrayList();
    private MyAdapter out_Adapter;
    private ArrayList<Goods> goodslists = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("orderLists",orderLists.toString());
                    out_Adapter = new MyAdapter(OutActivity.this, orderLists);
                    listView.setAdapter(out_Adapter);
                    //listview中item的点击事件
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            OrderList orderList3 = orderLists.get(position);
//                            showListDialog((ArrayList<Goods>) order_goods.get(position),orderList3);
//
//                        }
//                    });


            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        listView = findViewById(R.id.out_list_view);
        getServer_data();
    }
    public void getServer_data(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    orderLists = new ArrayList<>();
                    data = "";
                    System.out.println("begin");
                    Map<String, String> body = new HashMap<>();
                    Map<String, String> body2 = new HashMap<>();
                    body.put("usernum", LoginActivity.usernum);
                    body.put("userpwd", LoginActivity.userpwd);
                    String s = HttpSend.doPost("https://www.kpcodingoffice.com/api/index", body);
                    System.out.println(Tools.str2Json(s).get("token").getAsString());
                    body2.put("usernum", LoginActivity.usernum);
                    body2.put("token", Tools.str2Json(s).get("token").getAsString());
                    Log.d("body2", body2.toString());
                    //第二次请求获得的未审批订单内容
                    data = HttpSend.doPost("https://www.kpcodingoffice.com/api/getalldata", body2);

                    Log.d("data", data);

                    JsonObject jo = Tools.str2Json(data);
                    //判断服务器是否返回正确
                    if (!jo.get("code").getAsString().equals("0") ) {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        Looper.prepare();
                        Toast.makeText(OutActivity.this, "未查询到订单", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    } else {
                        JsonArray ja = jo.get("datas").getAsJsonArray();
                        //获得订单物品
                        JsonArray ja2;
                        JsonObject jo2;

                        for (int i = ja.size()-1; i >=0; i--) {
                            jo = ja.get(i).getAsJsonObject();
                            orderLists.add(new OrderList(jo.get("users").getAsString(), jo.get("signuptime").getAsString(), jo.get("goodscomments").getAsString(), jo.get("orderid").getAsString(),jo.get("usernum").getAsString()));
                            ja2 = ja.get(i).getAsJsonObject().get("datas").getAsJsonArray();
                            goodslists = new ArrayList<>();
                            for (int j = 0; j < ja2.size(); j++) {
                                jo2 = ja2.get(j).getAsJsonObject();
                                goodslists.add(new Goods(jo2.get("name").getAsString(), jo2.get("num").getAsString(), jo2.get("cata").getAsString()));
                                System.out.print(jo2.get("cata").getAsString());
                            }
                            order_goods.add(goodslists);
                            System.out.println();
                        }


                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }

                } catch(NoSuchProviderException e){
                    e.printStackTrace();
                } catch(NoSuchAlgorithmException e){
                    e.printStackTrace();
                } catch(KeyManagementException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
