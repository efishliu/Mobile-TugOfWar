package com.example.liugang.tugofwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class JoinRoomActivity extends AppCompatActivity {
    private EditText joinroom_roomid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
        Button joingame = (Button)findViewById(R.id.join_room_joinroom);
        joinroom_roomid = (EditText)findViewById(R.id.join_room_roomid);
        final RadioGroup radio_group = findViewById(R.id.radio_group);
        MainActivity.roommaster = "0";
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkbutton = (RadioButton)findViewById(radio_group.getCheckedRadioButtonId());
                String team = checkbutton.getText().toString();
                if(team.equals("红队"))
                    MainActivity.team = "red";
                else
                    MainActivity.team = "blue";
            }
        });
        joingame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.roomid = joinroom_roomid.getText().toString();
                String senddata = MainActivity.roomid + "," + MainActivity.myname + "," + MainActivity.team + ",";
                HttpUtil.sendHttpRequest("joinroom.jsp", senddata, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {

                        if(!response.equals("failed")) {
                            Intent intent = new Intent(JoinRoomActivity.this, RoomActivity.class);
                            startActivity(intent);
                        }
                        else{
                            TextView tip = findViewById(R.id.join_room_tip);
                            tip.setText("房间不存在！");
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        //
                        TextView tip = findViewById(R.id.join_room_tip);
                        tip.setText("房间不存在！");
                    }
                });
            }
        });
    }
}
