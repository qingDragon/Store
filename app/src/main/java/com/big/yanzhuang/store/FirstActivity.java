package com.big.yanzhuang.store;
/**
 *@author Bibi
 *@date 2019/3/25 22:29
 *@params No such property: code for class: Script1
 *@decribe TODO
**/
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jauker.widget.BadgeView;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    private String data="";//http请求返回的数据
    BadgeView badgeView ;
    private int result= 0;
    ImageView img_view1;
    ImageView img_view3;
    ImageView img_view4;
    ImageView img_view5;
    ImageView img_view6;
    ImageView imageView3;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
             switch (msg.what) {
                 case 1:
                     if (result != 0) {
                         badgeView.setTargetView(img_view1);
                         badgeView.setBadgeCount(result);
                         badgeView.setTextSize(15);
                     }
             }
        }
    };
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        badgeView = new BadgeView(getBaseContext());
        imageView3 = (ImageView) findViewById(R.id.img3) ;
        img_view1 =  findViewById(R.id.img_view1);
        img_view3 =  findViewById(R.id.img_view3);
        img_view4 =  findViewById(R.id.img_view4);
        img_view5 =  findViewById(R.id.img_view5);
        img_view6 = findViewById(R.id.img_view6);
        //添加角标通知
        getOrderAmount();
        Log.d("hello","主线程");
        Log.d("result",String.valueOf(result));


        //添加按钮监听
        img_view1.setOnClickListener(this);
        img_view3.setOnClickListener(this);
        img_view4.setOnClickListener(this);
        img_view5.setOnClickListener(this);
        img_view6.setOnClickListener(this);
        imageView3.setOnClickListener(this);


    }

    
    
    

    /**
     *@author Bibi
     *@date 2019/3/25 22:50
     *@name
     *@params No such property: code for class: Script1
     *@returnType 
     *@decribe TODO
    **/
    public void getOrderAmount(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result = 0;
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
                    data = HttpSend.doPost("https://www.kpcodingoffice.com/api/orderdata", body2);

                    Log.d("data", data);

                    JsonObject jo = Tools.str2Json(data);
                    //判断服务器是否返回正确
                    if (!jo.get("code").getAsString().equals("0") ) {


                    } else {

                        JsonArray ja = jo.get("datas").getAsJsonArray();
                        for (int i =0;i<ja.size();i++){
                            result++;
                            Log.d("result",String.valueOf(result));
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);


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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_view1:
                //使用intent启动活动
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.img3:
                getOrderAmount();
                break;
            case R.id.img_view5:
                Intent intent2 = new Intent(FirstActivity.this,PhoneActivity.class);
                startActivity(intent2);
                break;
            case R.id.img_view3:
                Intent intent3 = new Intent(FirstActivity.this,LiveActivity.class);
                startActivity(intent3);
                break;
            case R.id.img_view4:
                Intent intent4 = new Intent(FirstActivity.this,OutActivity.class);
                startActivity(intent4);
            case R.id.img_view6:
                Intent intent6 = new Intent(FirstActivity.this,Instore.class);
                startActivity(intent6);
            default:
                break;
        }
    }
    //返回键
//    public void onBackPressed() {
//        finish();//注释掉这行,back键不退出activity
//
//    }

}
