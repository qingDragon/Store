package com.big.yanzhuang.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.big.yanzhuang.store.HttpSend.doPost;
import static com.big.yanzhuang.store.R.layout.activity_second;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private List<OrderList> data_list;
    private MyAdapter myAdapter;
    private String data;
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String,String> body = new HashMap<>();
                    Map<String,String> body2 = new HashMap<>();
                    body.put("usernum","662995");
                    body.put("userpwd","123456");

                    System.out.println("lalla");
                    String s = HttpSend.doPost("https://www.kpcodingoffice.com/index",body);
                    System.out.println(s);

                    System.out.println(Tools.str2Json(s).get("token").getAsString());
                    body2.put("usernum","662995");
                    body2.put("token",Tools.str2Json(s).get("token").getAsString());
                    //第二次请求获得的String
                    data= HttpSend.doPost("https://www.kpcodingoffice.com/orderdata",body2);

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
        initView();
        initData(data);


    }


    public void initView(){
        listView = (ListView) findViewById(R.id.list_view);
    }
    public void initData(String str){
        String str1,str2,str3;
        JsonObject jsonObject = new JsonObject();
        jsonObject = (JsonObject)Tools.str2Json(str).get("datas");
        str1 = jsonObject.get("users").getAsString();
        str2 = jsonObject.get("signuptime").getAsString();
        str3 = jsonObject.get("goodscomments").getAsString();
        System.out.println(str1);
        data_list = new ArrayList<OrderList>();
        //后面根据订单数量加循环
        OrderList orderList = new OrderList(str1,str2,str3);
        data_list.add(orderList);
        myAdapter = new MyAdapter(this,data_list);
        listView.setAdapter(myAdapter);
    }



}
