package com.example.liugang.tugofwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameMainActivity extends AppCompatActivity {
    private TextView game_main_username;
    private TextView game_main_userid;
    private Button game_main_createroom;
    private Button game_main_joinroom;
    private Button history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        game_main_username = (TextView)findViewById(R.id.game_main_username);
        game_main_userid = (TextView)findViewById(R.id.game_main_userid);
        game_main_userid.setText(MainActivity.myid);
        game_main_username.setText(MainActivity.myname);
        Button joinroom_bt = (Button)findViewById(R.id.joinRoom);
        Button createroom_bt = (Button)findViewById(R.id.createRoom);
        Button history = (Button)findViewById(R.id.history);
        //点击创建房间
        createroom_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = MainActivity.myname + ",";
                HttpUtil.sendHttpRequest("createroom.jsp", username, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        MainActivity.roommaster = "1";
                        MainActivity.roomid = response;
                        MainActivity.roomcount = "1";
                        MainActivity.team = "red";
                        Intent intent = new Intent(GameMainActivity.this,RoomActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Exception e) {
                    //
                    }
                });
            }
        });
        //点击加入房间
        joinroom_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(GameMainActivity.this,JoinRoomActivity.class);
            startActivity(intent);

            }
        });
        //点击历史战绩
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameMainActivity.this,MyHistoryActivity.class);
                startActivity(intent);

            }
        });

    }
}
