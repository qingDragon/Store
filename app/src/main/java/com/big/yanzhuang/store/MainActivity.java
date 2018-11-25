package com.big.yanzhuang.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.jauker.widget.BadgeView;

import cn.bingoogolapple.badgeview.BGABadgeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = (Button) findViewById(R.id.bt1);
        BadgeView badgeView = new BadgeView(getBaseContext());
        badgeView.setTargetView(bt1);
        badgeView.setBadgeCount(3);
        badgeView.setTextSize(20);

    }
}
