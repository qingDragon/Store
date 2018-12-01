package com.big.yanzhuang.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jauker.widget.BadgeView;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    private String data="";//http请求返回的数据


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
//        HttpDeal.getString();
//        Log.d("123213214",data);
        bt1.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                startActivity(intent);
        }
    }






}
