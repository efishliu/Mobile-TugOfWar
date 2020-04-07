package com.example.liugang.tugofwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SigninActivity extends AppCompatActivity {
    private TextView signin_tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Button signin_bt = (Button)findViewById(R.id.signin_signin_bt);
        signin_tip = (TextView)findViewById(R.id.signin_tip);
        signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_et = (EditText)findViewById(R.id.signin_username);
                EditText passwd_et = (EditText)findViewById(R.id.signin_passwd);
                String username = username_et.getText().toString();
                String passwd = passwd_et.getText().toString();
                String request_info = username + "," + passwd + ",";
                HttpUtil.sendHttpRequest("signin.jsp", request_info, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //signin_tip.setText(response);
                        String [] userinfo_buffer = response.split(",");
                        if(userinfo_buffer[0].equals("success"))
                        {
                            MainActivity.myid = userinfo_buffer[1];
                            MainActivity.myname = userinfo_buffer[2];

                            Intent intent = new Intent(SigninActivity.this,GameMainActivity.class);

                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        signin_tip.setText("注册失败");
                    }
                });
            }
        });
    }

}
