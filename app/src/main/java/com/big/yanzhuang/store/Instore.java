package com.big.yanzhuang.store;


import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhy.bean.Bean;
import zhy.bean.FileBean;
import zhy.tree.bean.Node;
import zhy.tree.bean.TreeListViewAdapter;
import zhy.tree_view.SimpleTreeAdapter;

public class Instore extends AppCompatActivity {
    /**
     * @author Bibi
     * @date 2019/3/25 23:37
     * @classname Instore
     * @decribe 入库活动
     **/
//    private boolean isInitial;
//    private NiceSpinner spinner;
//    private List<String> data_list;
//    private ArrayAdapter<String> arr_adapter;
    public static ArrayList<Item> itemList;
//    public static ArrayList<Item> firstList;
    private List<Bean> mDatas = new ArrayList<Bean>();
    private List<FileBean> mDatas2 = new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;
    public static int id;
    private String[] rack_Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instore);
//        spinner = findViewById(R.id.spinner);
        getInStoreData();
        		mTree = (ListView) findViewById(R.id.id_tree);



	}
        //数据




    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            switch (message.what) {
                case 1:
                    try
                    {
                        mAdapter = new SimpleTreeAdapter<FileBean>(mTree, Instore.this, mDatas2, 10);
                        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener()
                        {
                            @Override
                            public void onClick(Node node, int position)
                            {
                                if (node.isLeaf())
                                {
//                                    Toast.makeText(getApplicationContext(), node.getName(),
//                                            Toast.LENGTH_SHORT).show();
                                    Log.d("1239999999", String.valueOf(node.getId()));
                                    //获取选中物品的id，作为参数获取
                                    id = node.getId();
                                    //获取点击的待入库物品的货架号
                                    getRackNum();
//                                    showListDialog(rack_Num);
                                }
                            }

                        });

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    mTree.setAdapter(mAdapter);
                    break;
                case 2:
                    showListDialog(rack_Num);
                    break;
//                    data_list = new ArrayList<String>();
//                    firstList = Tools.first_list(itemList);
//                    if(!firstList.isEmpty()) {
//                        for (int i = 0; i < firstList.size(); i++) {
//                            data_list.add(firstList.get(i).getContents());
//                        }
//                    }
//                    Log.d("tip","正在handler中……");
//                    //适配器
//                    arr_adapter= new ArrayAdapter<String>(Instore.this, android.R.layout.simple_spinner_item, data_list);
//                    //设置样式
//                    arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    //加载适配器
//                    spinner.setAdapter(arr_adapter);
//                    MyListener listener = new MyListener();
//                    spinner.setOnItemSelectedListener(listener);
            }
        }
    };

//    class MyListener implements  AdapterView.OnItemSelectedListener {
//        /**
//         *@author Bibi
//         *@date 2019/3/25 23:44
//         *@classname MyListener
//         *@decribe TODO 入库选择物品类别添加监听器
//        **/
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////            Toast.makeText(Instore.this,
////                    "您选择了"+arr_adapter.getItem(position), Toast.LENGTH_LONG).show();
//            if(isInitial){
//                isInitial = false;
//                return;
//            }
//            String selected_str = arr_adapter.getItem(position);
//
//            if(selected_str.equals("空气开关")) {
//                showInDialog();
//            } else if(selected_str.equals("金具")){
//                showListDialog();
//            } else {
////                Toast.makeText(Instore.this,
////                        "您的操作有误，请联系管理员！", Toast.LENGTH_LONG).show();
//            }
//        }

//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }


    private void showInDialog() {
        /**
         *@author Bibi
         *@date 2019/3/27 14:26
         *@name showInDialog
         *@returnType void
         *@decribe TODO 入库物品没有库存时提示
         **/
        final AlertDialog.Builder InDialog = new AlertDialog.Builder(Instore.this);
        //InDialog.setIcon(R.drawable.);
        InDialog.setTitle("库存为空");
        InDialog.setMessage("请扫码选择货架！");
        InDialog.setPositiveButton("扫码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        InDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        getRackNum();
        InDialog.show();
    }

    private void showListDialog(String[] rackNum) {
//        final String[] items = {"1号货架", "2号货架", "3号货架"};
            final AlertDialog.Builder listDialog = new AlertDialog.Builder(Instore.this);
            listDialog.setTitle("已有库存");
//            listDialog.setMessage("库存物品所在货架信息");
            listDialog.setItems(rackNum, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Instore.this, "请扫码选择货架", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            listDialog.setPositiveButton("扫码", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //调用扫码
                    new IntentIntegrator(Instore.this)
                            .setCaptureActivity(CustomCaptureActivity.class)
                            .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)// 扫码的类型,可选：一维码，二维码，一/二维码
                            .setPrompt("请对准二维码")// 设置提示语
                            .setCameraId(0)// 选择摄像头,可使用前置或者后置
                            .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                            .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                            .initiateScan();// 初始化扫码

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

//    }

    public void getInStoreData(){

        /**
         *@author Bibi
         *@date 2019/4/9 14:52
         *@name getInStoreData
         *@returnType void
         *@decribe TODO 向服务器发送http post请求获取物品目录
        **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> post = new HashMap<>();
                post.put("user_num",LoginActivity.usernum);
                post.put("user_token",LoginActivity.token);
                String res;
                try {
                    res = HttpSend.doPost("http://118.25.40.2/api/getallcata/",post);
                    JsonObject jo = Tools.str2Json(res);
                    Log.d("ressss",res);
                    JsonArray ja = jo.get("data").getAsJsonArray();
                    JsonObject jo2;
                    itemList = new ArrayList<>();
                    for(int i=0;i<ja.size();i++){
                        jo2 = ja.get(i).getAsJsonObject();
                        Log.d("id" ,String.valueOf(jo2.get("id").getAsInt()));
                        Log.d("contents",jo2.get("contens").getAsString());
                        Log.d("parent_id",jo2.get("parent_id").getAsString());
                        Log.d("is_leaf",jo2.get("is_leaf").getAsString());
                        Log.d("lay_num",jo2.get("lay_num").getAsString());
                        itemList.add(
                                new Item(
                                    jo2.get("id").getAsInt(), jo2.get("contens").getAsString(),jo2.get("parent_id").getAsString(),jo2.get("is_leaf").getAsString(),jo2.get("lay_num").getAsString()));
                        mDatas2.add(new FileBean(jo2.get("id").getAsInt(),jo2.get("parent_id").getAsInt(),jo2.get("contens").getAsString()));
                    }


                    for(int i=0;i<itemList.size();i++){
                        System.out.println(itemList.get(i).toString());
                        System.out.println(mDatas2.get(i).toString());
                    }
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    Log.d("end","执行结束");

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
    //发送http请求获取待入库物品货架号
    public void getRackNum(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> post_rack = new HashMap<>();
                Log.d("remember-token",LoginActivity.token);
                post_rack.put("remember_token",LoginActivity.token);
                post_rack.put("stack_name","黎城供电所应急库");
                post_rack.put("goods_id",String.valueOf(id));

                String res;
                try {
                    res = HttpSend.doPost("http://118.25.40.2/api/getgoodsrack/",post_rack);
                    Log.d("res",res);
                    JsonObject jo = new JsonObject();
                    jo = Tools.str2Json(res);
                    JsonArray ja = new JsonArray();
                    ja = jo.get("data").getAsJsonArray();
                    rack_Num = new String[ja.size()];
                    for(int i = 0;i<ja.size();i++){
                        rack_Num[i] = String.valueOf(ja.get(i).getAsJsonObject().get("where_is"));
                    }
                    Log.d("字符串数组",rack_Num[0]);
                    Message message = new Message();
                    message.what= 2;
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
}
