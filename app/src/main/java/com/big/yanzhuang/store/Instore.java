package com.big.yanzhuang.store;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class Instore extends AppCompatActivity {
    /**
     *@author Bibi
     *@date 2019/3/25 23:37
     *@classname Instore
     *@decribe 入库活动
    **/
    private  boolean isInitial;
    private NiceSpinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instore);
        spinner = findViewById(R.id.spinner);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("北京");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        MyListener listener = new MyListener();
        spinner.setOnItemSelectedListener(listener);

    }

    class MyListener implements  AdapterView.OnItemSelectedListener {
        /**
         *@author Bibi
         *@date 2019/3/25 23:44
         *@classname MyListener
         *@decribe TODO 入库选择物品类别添加监听器
        **/
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(Instore.this,
//                    "您选择了"+arr_adapter.getItem(position), Toast.LENGTH_LONG).show();
            if(isInitial){
                isInitial = false;
                return;
            }
            String selected_str = arr_adapter.getItem(position);

            if(selected_str.equals("上海")) {
                showInDialog();
            } else if(selected_str.equals("广州")){
                showListDialog();
            } else {
//                Toast.makeText(Instore.this,
//                        "您的操作有误，请联系管理员！", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
        
        
        private void showInDialog(){
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
                    //调用扫码
                }
            });
            InDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            InDialog.show();
        }

        private void showListDialog(){
            final String[] items = {"1号货架","2号货架","3号货架"};
            final AlertDialog.Builder listDialog = new AlertDialog.Builder(Instore.this);
            listDialog.setTitle("已有库存");
//            listDialog.setMessage("库存物品所在货架信息");
            listDialog.setItems(items, new DialogInterface.OnClickListener() {
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

    }
}
