package com.example.liugang.tugofwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static String myid = "";
    public static String myname = "";
    public static String roomid = "";
    public static String team = "";
    public static String roommaster = "0";
    public static String roomcount = "1";
    public static String flag = "0";
    public static String redteam[] = {"","","","",""}; //5个
    public static String blueteam[] = {"","","","",""};
    public static String result = "";
    public static int mycount = 0;
    public static int redcount = 0;
    public static int bluecount = 0;
    public static int time = 60;
    public static int my1scount = 0;
    public static int totalcount = 0;
    public static int rednumber = 0;
    public static int bluenumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signin_text_bt = (Button)findViewById(R.id.signin_text);
        Button login_text_bt = (Button)findViewById(R.id.login_text);
        //点击注册按键
        signin_text_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
        //点击登录按钮
        login_text_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
