package com.big.yanzhuang.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;


/**
 *@author Bibi
 *@date 2019/4/25 21:17
 *@classname CustomCaptureActivity
 *@decribe TODO 自定义的扫码活动，用来扫二维码选择货架
**/
public class CustomCaptureActivity extends AppCompatActivity {
    private CaptureManager captureManager;
    private DecoratedBarcodeView barcodeScannerView;
    private static Button button;
    private static String rackNum;//货架号
    private ArrayList<String> resultList;//已扫码的物品列表
    private int i = 0;//计数器，用来记录扫描物品个数
    private boolean isRackNum ;//判断是否已扫码选择号货架
    public String[] goodsIdList;//物品id数组
    private String resId;//服务器返回code
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_capture);
        barcodeScannerView = findViewById(R.id.dbv_custom);
        button = findViewById(R.id.btn_num);
        rackNum = "";
        resultList = new ArrayList<>();
        isRackNum = false;
        captureManager = new CaptureManager(this, barcodeScannerView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.setmResultCallBack(new CaptureManager.ResultCallBack() {
            @Override
            public void callBack(int requestCode, int resultCode, Intent intent) {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
                if(null != result && null != result.getContents()) {

                    if(isRackNum){
                        i++;
                        button.setText("已扫码数量为"+ i+"，点击显示详情");
                        //将二维码扫描结果存储到resultList
                        resultList.add(result.getContents());

                        Log.d("result",result.getContents()+"---"+i);
                        /**
                         *
                         */
                        captureManager.onResume();
                        captureManager.decode();
                    }
                    else{
                        Toast.makeText(CustomCaptureActivity.this, "已选择货架号"+result.getContents(), Toast.LENGTH_SHORT).show();
                        button.setText("开始入库");
                        rackNum = result.getContents();

                    }

                }
            }
        });
        captureManager.decode();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否选择货架号成功
                if(button.getText().equals("请扫码选择货架号")){
                    Toast.makeText(CustomCaptureActivity.this, "您还未扫码选择货架", Toast.LENGTH_SHORT).show();
                }else if(button.getText().equals("开始入库")){
                    isRackNum = true;
                    button.setText("已扫码数量为0");
                    //选取货架号成功后开始扫码入库
                    captureManager.onResume();
                    captureManager.decode();
                } else {
                    captureManager.onPause();
                    Toast.makeText(CustomCaptureActivity.this, "已扫码数量为"+ i, Toast.LENGTH_SHORT).show();
                    showListDialog(list2string(resultList));
                }
            }

        });


    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    if(resId.equals("0")){
                        Toast.makeText(CustomCaptureActivity.this, "已入库"+i+"件", Toast.LENGTH_SHORT).show();
                        captureManager.closeAndFinish();
                    } else {
                        //打印服务器返回信息
                    }
                    break;
                default:
                    break;
            }
        }
    };


    //已扫码物品详情列表
    private void showListDialog(String[] goodsIdList) {
//        final String[] items = {"1号货架", "2号货架", "3号货架"};
        final AlertDialog.Builder listDialog = new AlertDialog.Builder(CustomCaptureActivity.this);
        listDialog.setTitle("已扫码物品列表");
//            listDialog.setMessage("库存物品所在货架信息");
        listDialog.setItems(goodsIdList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //扩展，增加删除条目功能
            }
        });
        listDialog.setPositiveButton("确认入库", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //发送http请求，获取返回码后，提示用户是否入库成功，成功返回树形目录界面，错误提示错误信息
                sendGoodsIdList(resultList);

            }
        });
        listDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        listDialog.show();
    }
    //list转数组
    public String[] list2string (ArrayList<String> list){
        goodsIdList = new String[list.size()];
        for(int i=0;i<list.size();i++){
            goodsIdList[i] = list.get(i);
        }
        return goodsIdList;
    }
    //发送http请求，将待入库的物品提交给服务器。
    public void sendGoodsIdList(final ArrayList<String> list){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> post_goodsIdList = new HashMap<>();
                post_goodsIdList.put("remember_token",LoginActivity.token);
                post_goodsIdList.put("stack_name","黎城供电所应急库");
                post_goodsIdList.put("goods_id",String.valueOf(Instore.id));
                post_goodsIdList.put("enter_time",getTime());
                post_goodsIdList.put("data",list.toString());
                post_goodsIdList.put("rack_id",rackNum);
                Log.d("post",post_goodsIdList.toString());
                try {
                    String res = HttpSend.doPost("http://118.25.40.2/api/enterstack/",post_goodsIdList);
                    Log.d("res",res+"12231243312451512561");
                    JsonObject jo = new JsonObject();
                    jo = Tools.str2Json(res);
                    resId = jo.get("code").getAsString();
                    Log.d("resid",resId+"+++++++++++++++++++++++++");
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);


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

    //获取时间方法
    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event)||super.onKeyDown(keyCode,event);
    }


}
