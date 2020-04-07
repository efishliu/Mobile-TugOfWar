package com.example.liugang.tugofwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private final static int UPDATE_MYGAME = 1;
    Button return_main;
    Button history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.result.equals("win")){
            setContentView(R.layout.activity_result_win);
            return_main = (Button)findViewById(R.id.return_main_win);
            history = (Button)findViewById(R.id.view_history_win);

        }
        else
        {
            setContentView(R.layout.activity_result_lose);
            return_main = (Button)findViewById(R.id.return_main_lose);
            history = (Button)findViewById(R.id.view_history_lose);
        }
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,GameMainActivity.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,MyHistoryActivity.class);
                startActivity(intent);
            }
        });
        gameover_init();

    }
    public void gameover_init()
    {
        MainActivity.roomid = "";
        MainActivity.team = "";
        MainActivity.roommaster = "0";
        MainActivity.roomcount = "0";
        MainActivity.flag = "0";
        MainActivity.result = "";
        MainActivity.mycount = 0;
        MainActivity.redcount = 0;
        MainActivity.bluecount = 0;
        MainActivity.time = 60;
        MainActivity.my1scount = 0;
        MainActivity.totalcount = 0;
        MainActivity.rednumber = 0;
        MainActivity.bluenumber = 0;

    }

}
