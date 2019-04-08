package com.big.yanzhuang.store;
/**
 * 登录界面
 */

import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.io.IOException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

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
    private Handler handler = new Handler() {
        public void handlerMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(LoginActivity.this,"用户名密码错误",Toast.LENGTH_LONG).show();
                    Log.d("handler","123");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                getLogin();

//                getLogin("http://118.25.40.2/api/login/",usernum,userpwd);
//                Map<String, String> body = new HashMap<>();
//                body.put("usernum", usernum);
//                body.put("userpwd", userpwd);
//                String s = null;
//                try {
//                    s = HttpSend.doPost("http://118.25.40.2/api/login/", body);
//                } catch (NoSuchProviderException e) {
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (KeyManagementException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Tools.str2Json(s).get("token").getAsString());
//                Log.d("usernum",usernum);
                //用户名密码校验
//                if(usernum.equals("662995")&&userpwd.equals("662662")) {
//                    Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
//                    startActivity(intent);
//                }
        }
    }
    private void getLogin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> post = new HashMap<>() ;
                usernum = user_num.getText().toString();
                userpwd = user_pwd.getText().toString();
                post.put("usernum",usernum);
                post.put("userpwd",userpwd);
                Log.d("usernum",usernum);
                String s = null;
                try {
                    s = HttpSend.doPost("http://118.25.40.2/api/login/",post);

                    Log.d("s-",s);
                    if(Tools.str2Json(s).get("message").getAsString().equals("success")){
                        Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
                        startActivity(intent);
                    } else {
    //                    Toast.makeText(LoginActivity.this, "用户名密码错误", Toast.LENGTH_SHORT).show();
                        Log.d("ll","12314");
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
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
