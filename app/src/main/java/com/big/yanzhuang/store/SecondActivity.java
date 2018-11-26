package com.big.yanzhuang.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import static com.big.yanzhuang.store.R.layout.activity_second;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_second);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_item,null);

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_second);
        layout.addView(view);
        layout.addView(view);

    }
}
