package com.example.liugang.tugofwar;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class PlayingActivity extends AppCompatActivity implements SensorEventListener{
    public SensorManager mSensorManager;
    public final static int UPDATE_COUNT = 1;
    public final static int PLAY = 2;
    private CountdownView countdownView;
    private TextView mycount;
    private TextView redcount;
    private TextView bluecount;
    private MediaPlayer music_cd3s;
    private MediaPlayer music_kll101;
    private TextView text_red;
    private  TextView text_blue;
    private float redsize;
    private float bluesize;
    private int rednumber;
    private int bluenumber;
    private int totalnumber;
    private int myshakecount;

    private int threesecs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        threesecs = 3;
        MainActivity.my1scount = 0;
        MainActivity.mycount = 0;
        myshakecount = 0;
        totalnumber = MainActivity.rednumber+MainActivity.bluenumber;
        rednumber = MainActivity.redcount;
        bluenumber = MainActivity.bluecount;
        mycount = findViewById(R.id.playing_mycount);
        redcount = findViewById(R.id.playing_redcount);
        bluecount = findViewById(R.id.playing_bluecount);
        text_red = findViewById(R.id.text_red);
        text_blue = findViewById(R.id.text_blue);
        countdownView = findViewById(R.id.view_countdown);
        countdownView.setCountdown(MainActivity.time);

        handler_1s.postDelayed(runnable,1000);

        //摇一摇
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        //播放倒计时3s
        music_cd3s = MediaPlayer.create(PlayingActivity.this,R.raw.cd_3s);
        music_cd3s.start();

    }
    //handler定时器更新
    Handler handler_1s = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler_1s.postDelayed(this,1000);
            if(threesecs > 0){
                threesecs--;
            }
            else{
                if(MainActivity.time == 60){
                    Message message = new Message();
                    message.what = PLAY;
                    handler_ui.sendMessage(message);

                }
                MainActivity.time--;
                countdownView.setCountdown(MainActivity.time);
                String senddata = MainActivity.roomid + "," + MainActivity.team + "," + MainActivity.my1scount + ",";
                HttpUtil.sendHttpRequest("updatecount.jsp", senddata, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        MainActivity.my1scount = 0;
                        String []data = response.split(",");
                        MainActivity.redcount = Integer.parseInt(data[0]);
                        MainActivity.bluecount = Integer.parseInt(data[1]);

                        //异步更新UI
                        Message message = new Message();
                        message.what = UPDATE_COUNT;
                        handler_ui.sendMessage(message);

                    }
                    @Override
                    public void onError(Exception e) {
                        //
                    }
                });

                //游戏结束
                if(MainActivity.time <= 0){
                    music_kll101.stop();
                    if(MainActivity.team.equals("red")){
                        if(redsize > bluesize)
                            MainActivity.result = "win";
                        else
                            MainActivity.result = "lose";
                    }
                    else
                    {
                        if(redsize > bluesize)
                            MainActivity.result = "lose";
                        else
                            MainActivity.result = "win";
                    }
                    String senddata2 = MainActivity.roomid + "," + MainActivity.myname + "," + myshakecount + "," + MainActivity.result + ",";
                    HttpUtil.sendHttpRequest("finish.jsp", senddata2, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            //
                        }
                        @Override
                        public void onError(Exception e) {
                            //
                        }
                    });
                    handler_1s.removeCallbacks(runnable);
                    Intent intent = new Intent(PlayingActivity.this,ResultActivity.class);
                    startActivity(intent);
                }
            }
        }

    };

    private Handler handler_ui = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_COUNT:
                    //更新ui
                    mycount.setText(myshakecount+"");
                    redcount.setText(MainActivity.redcount+"");
                    bluecount.setText(MainActivity.bluecount+"");
                    if(MainActivity.rednumber == 0) MainActivity.rednumber = 1;
                    if(MainActivity.bluenumber == 0)MainActivity.bluenumber = 1;
                    redsize = (float)(MainActivity.redcount  / MainActivity.rednumber)  ;
                    bluesize = (float)(MainActivity.bluecount  / MainActivity.bluenumber) ;
                    if(redsize == 0.0) redsize = 1.0f;
                    if(bluesize == 0.0) bluesize = 1.0f;
                    LinearLayout.LayoutParams red_lp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,redsize);
                    LinearLayout.LayoutParams blue_lp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,bluesize);
                    text_red.setLayoutParams(red_lp);
                    text_blue.setLayoutParams(blue_lp);
                    break;
                case PLAY:
                    music_kll101 = MediaPlayer.create(PlayingActivity.this,R.raw.kll101);
                    music_kll101.start();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if(sensorType == Sensor.TYPE_ACCELEROMETER){
            if((Math.abs(values[0])>14||Math.abs(values[1])>14||Math.abs(values[2])>14)){
                MainActivity.my1scount++;
                MainActivity.mycount++;
                myshakecount++;
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //加速度传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onStop(){
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause(){
        mSensorManager.unregisterListener(this);
        super.onPause();
    }
}
