package com.example.liugang.tugofwar;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Message;


public class RoomActivity extends AppCompatActivity  {
    private TextView roomid;
    private static TextView roomcount;
    private static TextView red1,red2,red3,red4,red5;
    private static TextView blue1,blue2,blue3,blue4,blue5;
    private TextView room_tip;
    public static final int UPDATE_TEXT = 1;
    public static final int READY = 2;
    public static final int START = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化ui
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Button room_playing = (Button)findViewById(R.id.room_playing);
        room_tip = (TextView)findViewById(R.id.room_tip);
        if(MainActivity.roommaster.equals("0"))
            room_playing.setText("准备开战");
        roomid = (TextView)findViewById(R.id.room_roomid);
        roomid.setText(MainActivity.roomid);
        roomcount = (TextView)findViewById(R.id.room_roomcount);
        roomcount.setText("1");
        initUser();
        if(MainActivity.team.equals("red"))
        {
            MainActivity.redteam[0] = MainActivity.myname;
            red1.setText(MainActivity.myname);
        }
        if(MainActivity.team.equals("blue")){
            MainActivity.blueteam[0] = MainActivity.myname;
            blue1.setText(MainActivity.myname);
        }

        handler.postDelayed(runnable,1000);

        //点击准备或开始
        room_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.roommaster.equals("0"))
                {
                    Message message = new Message();
                    message.what = READY;
                    handler2.sendMessage(message);
                }
                else
                {
                    String senddata = MainActivity.roomid + ",";
                    HttpUtil.sendHttpRequest("start.jsp", senddata, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            //
                        }

                        @Override
                        public void onError(Exception e) {
                            //
                        }
                    });
                }
            }
        });

    }





    //定时器更新

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //开始
            if(MainActivity.flag.equals("1")){
                Message message2 = new Message();
                message2.what = START;
                handler2.sendMessage(message2);
                Intent intent = new Intent(RoomActivity.this,PlayingActivity.class);
                startActivity(intent);
            }

            handler.postDelayed(this,1000);
            String senddata = MainActivity.roomid + ",";
            HttpUtil.sendHttpRequest("updateroom.jsp", senddata, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {

                    if(!response.equals("failed"))
                    {
                        String []data = response.split("#");
                        String redinfo = data[0];
                        String blueinfo = data[1];
                        String flag = data[2];
                        MainActivity.flag = flag;
                        String []red = redinfo.split(",");
                        String []blue = blueinfo.split(",");
                        String redcount = red[0];
                        int ired = Integer.parseInt(redcount);
                        MainActivity.rednumber = ired;
                        String bluecount = blue[0];
                        int iblue = Integer.parseInt(bluecount);
                        MainActivity.bluenumber = iblue;
                        MainActivity.totalcount = ired + iblue;
                        for(int i = 1;i<=ired;i++)
                            MainActivity.redteam[i-1] = red[i];
                        for(int j = 1;j<=iblue;j++)
                            MainActivity.blueteam[j-1] = blue[j];
                    }


                }

                @Override
                public void onError(Exception e) {
                    //
                }
            });

            //更新房间数据,发送消息
            //使用异步消息处理机制更新ui
            Message message = new Message();
            message.what = UPDATE_TEXT;
            handler2.sendMessage(message);


        }

    };

    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT:
                    updateUser();
                    break;
                case READY:
                    //更新标签为准备
                    room_tip.setText("已准备！");
                    break;
                case START:
                    handler.removeCallbacks(runnable);
                    break;
                default:
                    break;
            }
        }
    };


    public void initUser(){
        //初始化用户
        red1 = (TextView)findViewById(R.id.red1);
        red2 = (TextView)findViewById(R.id.red2);
        red3 = (TextView)findViewById(R.id.red3);
        red4 = (TextView)findViewById(R.id.red4);
        red5 = (TextView)findViewById(R.id.red5);
        blue1 = (TextView)findViewById(R.id.blue1);
        blue2 = (TextView)findViewById(R.id.blue2);
        blue3 = (TextView)findViewById(R.id.blue3);
        blue4 = (TextView)findViewById(R.id.blue4);
        blue5 = (TextView)findViewById(R.id.blue5);
    }
    public void updateUser(){
        red1.setText(MainActivity.redteam[0]);
        red2.setText(MainActivity.redteam[1]);
        red3.setText(MainActivity.redteam[2]);
        red4.setText(MainActivity.redteam[3]);
        red5.setText(MainActivity.redteam[4]);
        blue1.setText(MainActivity.blueteam[0]);
        blue2.setText(MainActivity.blueteam[1]);
        blue3.setText(MainActivity.blueteam[2]);
        blue4.setText(MainActivity.blueteam[3]);
        blue5.setText(MainActivity.blueteam[4]);
        roomcount.setText(MainActivity.totalcount+"");
    }

}
