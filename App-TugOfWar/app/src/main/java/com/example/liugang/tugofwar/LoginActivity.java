package com.example.liugang.tugofwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity  {

    public TextView login_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_bt = (Button)findViewById(R.id.login_login_bt);
        login_tip = (TextView)findViewById(R.id.login_tip);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userid_et = (EditText)findViewById(R.id.login_userid);
                EditText passwd_et = (EditText)findViewById(R.id.login_passwd);
                String userid = userid_et.getText().toString();
                String passwd = passwd_et.getText().toString();
                String request_info = userid + "," + passwd + ",";
                HttpUtil.sendHttpRequest("login.jsp", request_info, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        String [] userinfo_buffer = response.split(",");
                        if(userinfo_buffer[0].equals("success")) //登录成功
                        {
                            MainActivity.myid = userinfo_buffer[1];
                            MainActivity.myname = userinfo_buffer[2];
                            Intent intent = new Intent(LoginActivity.this,GameMainActivity.class);
                            startActivity(intent);
                        }
                        else{

                            login_tip.setText("密码错误！");
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        login_tip.setText("登录失败");
                    }
                });
            }
        });
    }
}
