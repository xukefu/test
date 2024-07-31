package com.xt.sampleffmpegrtspplay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etRtspUrl1;
    private EditText etRtspUrl2;
    private EditText etRtspUrl3;
    private EditText etRtspUrl4;
    private Button btnConnect1;
    private Button btnSave1;
    private Button btnConnect2;
    private Button btnSave2;
    private Button btnConnect3;
    private Button btnSave3;
    private Button btnConnect4;
    private Button btnSave4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("aaaaaaa");
        // 初始化控件
        etRtspUrl1 = findViewById(R.id.et_rtsp_url1);
        btnConnect1 = findViewById(R.id.btn_connect1);
        btnSave1 = findViewById(R.id.btn_save1);

//        etRtspUrl2 = findViewById(R.id.et_rtsp_url2);
//        btnConnect2 = findViewById(R.id.btn_connect2);
//        btnSave2 = findViewById(R.id.btn_save2);

//        etRtspUrl3 = findViewById(R.id.et_rtsp_url3);
//        btnConnect3 = findViewById(R.id.btn_connect3);
//        btnSave3 = findViewById(R.id.btn_save3);
//
//        etRtspUrl4 = findViewById(R.id.et_rtsp_url4);
//        btnConnect4 = findViewById(R.id.btn_connect4);
//        btnSave4 = findViewById(R.id.btn_save4);

        // btnConnect1
        btnConnect1.setOnClickListener(v -> {
            String url = etRtspUrl1.getText().toString();
            if (url.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(), "请先填写url", Toast.LENGTH_SHORT);
                toast.show();
               return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "开始连接:"+url, Toast.LENGTH_SHORT);
            toast.show();

            play(url);

        });

        // btnConnect2
//        btnConnect2.setOnClickListener(v -> {
//            String url = etRtspUrl2.getText().toString();
//            play(url);
//        });

        // btnConnect4
//        btnConnect3.setOnClickListener(v -> {
//            String url = etRtspUrl3.getText().toString();
//            play(url);
//        });
//
//        // btnConnect4
//        btnConnect4.setOnClickListener(v -> {
//            String url = etRtspUrl4.getText().toString();
//            play(url);
//        });

        // 为保存按钮设置点击事件
        btnSave1.setOnClickListener(v -> {
            // 这里可以保存RTSP URL到SharedPreferences或其他存储
            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("url1", etRtspUrl1.getText().toString());
            editor.apply();
            Toast toast = Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT);
            toast.show();
        });

        // 为保存按钮设置点击事件
//        btnSave2.setOnClickListener(v -> {
//            // 这里可以保存RTSP URL到SharedPreferences或其他存储
//            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("url2", etRtspUrl2.getText().toString());
//            editor.apply();
//        });

        // 为保存按钮设置点击事件
//        btnSave3.setOnClickListener(v -> {
//            // 这里可以保存RTSP URL到SharedPreferences或其他存储
//            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("url3", etRtspUrl3.getText().toString());
//            editor.apply();
//        });
//
//        // 为保存按钮设置点击事件
//        btnSave4.setOnClickListener(v -> {
//            // 这里可以保存RTSP URL到SharedPreferences或其他存储
//            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("url4", etRtspUrl4.getText().toString());
//            editor.apply();
//        });
    }

    private void play(String url){
        RtspLiveActivity.start(MainActivity.this,url);
    }
}


