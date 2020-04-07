package com.example.liugang.tugofwar;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
