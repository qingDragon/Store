package com.big.yanzhuang.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText user_num;
    EditText user_pwd;
    Button btn_login;
    public static String usernum;
    public static String userpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_num = (EditText) findViewById(R.id.edit_text1);
        user_pwd = (EditText) findViewById(R.id.edit_text2);
//        user_num.setText("662995");
//        user_pwd.setText("123456");
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                usernum = user_num.getText().toString();
                userpwd = user_pwd.getText().toString();
                Log.d("usernum",usernum);
                //用户名密码校验
                if(usernum.equals("662995")&&userpwd.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
                    startActivity(intent);
                }
        }
    }
}
