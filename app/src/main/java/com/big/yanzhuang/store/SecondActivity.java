package com.big.yanzhuang.store;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.big.yanzhuang.store.R.layout.activity_second;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private List<OrderList> data_list;
    private MyAdapter myAdapter;
    private ArrayList<Goods> goodslists = new ArrayList<>();
    private ArrayList<OrderList> orderLists = new ArrayList<>();
    private OrderList orderList = new OrderList();
    private  ArrayList order_goods =new ArrayList();
    private JSONArray ja;
    private  String res="";
    //审批的物品代号list
    private ArrayList<String> goodscata = new ArrayList();

    public String data;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("orderLists",orderLists.toString());
                    myAdapter = new MyAdapter(SecondActivity.this, orderLists);
                    listView.setAdapter(myAdapter);
                    //listview中item的点击事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            OrderList orderList3 = orderLists.get(position);
                            dialogMoreChoice((ArrayList<Goods>) order_goods.get(position),orderList3);

                        }
                    });

                case 2:
                    myAdapter = new MyAdapter(SecondActivity.this, orderLists);
                    listView.setAdapter(myAdapter);


            }

        }
    };
//    private  void dialogMoreChoice(ArrayList orderLists){
//        String items[] = orderLists.
//    }



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
        String orderid = jsonObject2.get("orderid").getAsString();
        System.out.println(string);
        orderList = new OrderList(users,signuptime,goodscomments,orderid);
        System.out.println(users);
        return orderList;
    }

    /**
     * 与服务器交互
     */
    public void getServerdata(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    orderLists = new ArrayList<>();
                    data = "";
                    System.out.println("begin");
                    Map<String, String> body = new HashMap<>();
                    Map<String, String> body2 = new HashMap<>();
                    body.put("usernum", "662995");
                    body.put("userpwd", "123456");
                    String s = HttpSend.doPost("https://www.kpcodingoffice.com/api/index", body);
                    System.out.println(Tools.str2Json(s).get("token").getAsString());
                    body2.put("usernum", "662995");
                    body2.put("token", Tools.str2Json(s).get("token").getAsString());
                    Log.d("body2", body2.toString());
                    //第二次请求获得的未审批订单内容
                    data = HttpSend.doPost("https://www.kpcodingoffice.com/api/orderdata", body2);
//                    System.out.println("123456789:" + data);
                    Log.d("data", data);

                    JsonObject jo = Tools.str2Json(data);
                    //获得订单信息
                    if (!jo.get("code").getAsString().equals("0") ) {
                        Looper.prepare();
                        Toast.makeText(SecondActivity.this, "未查询到订单", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        JsonArray ja = jo.get("datas").getAsJsonArray();
                        //获得订单物品
                        JsonArray ja2;
                        JsonObject jo2;

                        for (int i = 0; i < ja.size(); i++) {
                            jo = ja.get(i).getAsJsonObject();
                            orderLists.add(new OrderList(jo.get("users").getAsString(), jo.get("signuptime").getAsString(), jo.get("goodscomments").getAsString(), jo.get("orderid").getAsString()));
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
                        //                   handler.sendEmptyMessage(1);
                        //                    Log.d("name",str2order(data).getUsers());
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

    /**
     * 审批物品dialog
     * @param goodslists
     */
    private void dialogMoreChoice(final ArrayList<Goods> goodslists, final OrderList orderList4) {
        final String items[] =new String[goodslists.size()];
        final boolean selected[] =new boolean[goodslists.size()];

        for (int i =0;i<goodslists.size();i++){
            System.out.println(goodslists.get(i).toString());
            items[i] = goodslists.get(i).getName()+"--"+goodslists.get(i).getNum();
            selected[i] = true;
        }

        @SuppressLint("ResourceType")
        AlertDialog.Builder builder = new AlertDialog.Builder(this,3);
        builder.setTitle("领料清单");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMultiChoiceItems(items, selected,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {

                        Toast.makeText(SecondActivity.this,
                                items[which] + isChecked, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                Toast.makeText(SecondActivity.this, "确定", Toast.LENGTH_SHORT)
//                        .show();
                // android会自动根据你选择的改变selected数组的值。
////                for (int i = 0; i < selected.length; i++) {
////                    Log.e("hongliang", "" + selected[i]);
////                }
                goodscata = new ArrayList<>();
                ja = new JSONArray();
                for(int i = 0;i< goodslists.size();i++){
                    System.out.println(selected[i]);

                    if(selected[i] == true){
                        Toast.makeText(SecondActivity.this, goodslists.get(i).getCate(), Toast.LENGTH_SHORT)
                                .show();
                        goodscata.add("\""+ goodslists.get(i).getCate() + "\"");

                        ja.put(goodslists.get(i).getCate());


                    }
                    System.out.println(ja);
                }
                System.out.println(ja.toString()+"1111");


                getServiceData2(orderList4);
                getServerdata();

            }
        });
        builder.create().show();
    }

    /**
     * 第三次http请求获取
     * @param orderList2
     */
    public void getServiceData2(final OrderList orderList2){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String order_id;
                String time;
                Map<String, String> body = new HashMap<>();
                Map<String, String> body2 = new HashMap<>();
                body.put("usernum", "662995");
                body.put("userpwd", "123456");
                try {
                    //获取订单token
                    String s = HttpSend.doPost("https://www.kpcodingoffice.com/api/index", body);
                    String token = Tools.str2Json(s).get("token").getAsString();
                    //获取订单id
                    order_id = orderList2.getOrderid();
                    Date day = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    time = df.format(day);

//                    JSONObject jO = new JSONObject();
//                    try {
//                        jO.put("order_id",orderList2.getOrderid());
//                        jO.put("checktime",time);
//                        jO.put("goods_cata",ja);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    Log.d("jO",jO.toString());
//                    JSONArray ja = new JSONArray();
//                    ja.put(jO);
//                    Log.d("ja",ja.toString());
                    Map body3 = new HashMap();
                    body3.put("usernum","662995");
                    body3.put("token",token);
                    body3.put("data",orderList2.getOrderid());
                    body3.put("checktime",time);
                    res = HttpSend.doPost("https://www.kpcodingoffice.com/api/checkorder", body3);
                    Log.d("body3",body3.toString());




                    if (Tools.str2Json(res).get("message").getAsString().equals("success") ) {
                        Log.d("tip","审批成功");
                    } else{
                        Log.d("tip","审批失败");
                    }
//                    Message msg = new Message();
//                    msg.what =1;
//                    handler.sendMessage(msg);




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
