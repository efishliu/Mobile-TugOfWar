package com.example.liugang.tugofwar;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyHistoryActivity extends AppCompatActivity {
    private final static int UPDATE_HIS = 1;
    private int meancount;
    private int winradio;
    private int wintimes;
    private String his1;
    private String his2;
    private String his3;
    private String  []roomid = {"0","0","0"};
    private String  []mycount = {"0","0","0"};
    private String  []myresult = {"0","0","0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        meancount = 0;
        winradio = 0;
        wintimes = 0;
        HttpUtil.sendHttpRequest("history.jsp", MainActivity.myname + ",", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                String []data = response.split("#");
                String []info = data[0].split(",");
                meancount = Integer.parseInt(info[0]);
                wintimes = Integer.parseInt(info[1]);
                winradio = Integer.parseInt(info[2]);
                String []his1 = data[1].split(",");
                String []his2 = data[2].split(",");
                String []his3 = data[3].split(",");
                roomid[0] = his1[0];
                roomid[1] = his2[0];
                roomid[2] = his3[0];
                mycount[0] = his1[1];
                mycount[1] = his2[1];
                mycount[2] = his3[1];
                myresult[0] = his1[2];
                myresult[1] = his2[2];
                myresult[2] = his3[2];
                for(int i = 0;i<3;i++)
                {
                    if(roomid[i].equals("0"))   roomid[i] = "";
                    if(mycount[i].equals("0"))  mycount[i] = "";
                    if(myresult[i].equals("0")) myresult[i] = "";
                    if(myresult[i].equals("win")) myresult[i] = "胜";
                    if(myresult[i].equals("lose")) myresult[i] = "败";
                }
                Message message = new Message();
                message.what = UPDATE_HIS;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        Button return_main =(Button)findViewById(R.id.his_returnmain);
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyHistoryActivity.this,GameMainActivity.class);
                startActivity(intent);

            }
        });
    }
    private void updatehistory()
    {
        TextView mytitle = (TextView)findViewById(R.id.his_mytitle);
        if(meancount<100)
            mytitle.setText("拔河新手");
        else if(meancount<200)
            mytitle.setText("拔河能手");
        else
            mytitle.setText("拔河小王子");
        TextView mymeancount = (TextView)findViewById(R.id.his_mycount);
        mymeancount.setText(meancount+"");
        TextView mywintimes = (TextView)findViewById(R.id.his_mywincount);
        mywintimes.setText(wintimes+"");
        TextView mywinradio = (TextView)findViewById(R.id.his_mywinradio);
        mywinradio.setText(winradio+"");

        TextView his_roomid1 = (TextView)findViewById(R.id.his_roomid1);
        TextView his_roomid2 = (TextView)findViewById(R.id.his_roomid2);
        TextView his_roomid3 = (TextView)findViewById(R.id.his_roomid3);
        TextView his_mycount1 = (TextView)findViewById(R.id.his_mycount1);
        TextView his_mycount2 = (TextView)findViewById(R.id.his_mycount2);
        TextView his_mycount3 = (TextView)findViewById(R.id.his_mycount3);
        TextView his_myresult1 = (TextView)findViewById(R.id.his_result1);
        TextView his_myresult2 = (TextView)findViewById(R.id.his_result2);
        TextView his_myresult3 = (TextView)findViewById(R.id.his_result3);

        his_roomid1.setText(roomid[0]);
        his_mycount1.setText(mycount[0]);
        his_myresult1.setText(myresult[0]);
        his_roomid2.setText(roomid[1]);
        his_mycount2.setText(mycount[1]);
        his_myresult2.setText(myresult[1]);
        his_roomid3.setText(roomid[2]);
        his_mycount3.setText(mycount[2]);
        his_myresult3.setText(myresult[2]);

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_HIS:
                    updatehistory();
                    break;
                default:
                    break;
            }
        }
    };
}
