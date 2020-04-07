package com.example.liugang.tugofwar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.app.Activity;

public class ShakeCapture extends Activity implements SensorEventListener{
    //定义sensor管理器
    public SensorManager mSensorManager;
    public void InitSernsor(){
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //加速度传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    @Override
    protected void onResume(){
        super.onResume();

        //加速度传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
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
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        int sensorType = event.sensor.getType();

        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;

        if(sensorType == Sensor.TYPE_ACCELEROMETER){

            /*因为一般正常情况下，任意轴数值最大就在9.8~10之间，只有在你突然摇动手机
             *的时候，瞬时加速度才会突然增大或减少。
             *所以，经过实际测试，只需监听任一轴的加速度大于14的时候，改变你需要的设置
             *就OK了~~~
             */
            if((Math.abs(values[0])>14||Math.abs(values[1])>14||Math.abs(values[2])>14)){
                //num++;
                //摇动手机后，设置button上显示的字为空

            }
        }
    }
}
