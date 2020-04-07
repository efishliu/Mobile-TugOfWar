package com.example.liugang.tugofwar;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static void sendHttpRequest(final String req_url,final String request_info,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    String request_url = "http://106.13.37.201:8000/" + req_url;
                    URL url = new URL(request_url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(request_info);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader((new InputStreamReader(in)));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String result = response.toString();
                    if(listener != null)
                    {
                        listener.onFinish(result);//回调方法
                    }

                } catch (Exception e) {
                    if(listener != null)
                        listener.onError(e);
                }finally {
                    if(connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }
}
