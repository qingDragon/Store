package com.big.yanzhuang.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.jauker.widget.BadgeView;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    private String data;//http请求返回的数据
    private String order;//处理后的订单字符串
    public static OrderList orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button bt1 = (Button) findViewById(R.id.bt1);
        //添加角标通知
        BadgeView badgeView = new BadgeView(getBaseContext());
        badgeView.setTargetView(bt1);
        badgeView.setBadgeCount(1);
        badgeView.setTextSize(25);


        bt1.setOnClickListener(this);
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
                    System.out.println(data);
                    System.out.println(str2order(data).getUsers());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                startActivity(intent);
        }
    }
    public OrderList str2order(String string){
        JsonObject jsonObject = Tools.str2Json(string);
        String str = jsonObject.get("datas").toString();
        order = str.substring(1,str.length()-1);
        jsonObject = new JsonObject();
        jsonObject = Tools.str2Json(order);
        String users = jsonObject.get("users").getAsString();
        String signuptime = jsonObject.get("signuptime").getAsString();
        String goodscomments = jsonObject.get("goodscomments").getAsString();
        orderList = new OrderList(users,signuptime,goodscomments);
        return orderList;
    }

}
