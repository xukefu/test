package com.xt.sampleffmpegrtspplay;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.xt.ijkplayer.rtsp.RtspPlayer;

public class RtspLiveActivity extends AppCompatActivity {
    private final static String TAG = RtspLiveActivity.class.getSimpleName();
    private String[]       permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private RtspPlayer mRtspPlayer;
    private static String globalUrl ;

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, RtspLiveActivity.class);
//        starter.putExtra("RTSP_URL", url);
        context.startActivity(starter);
        globalUrl = url;
        System.out.println("start:"+globalUrl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        requestPermission();
        initRtsp();
    }

    private void initRtsp() {
        mRtspPlayer = new RtspPlayer();
        mRtspPlayer.init(RtspLiveActivity.this, new RtspPlayer.BaseLoadingView() {
            @Override
            public void showLoading() {

            }

            @Override
            public void dismissLoading() {

            }
        },globalUrl);
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1234);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
        mRtspPlayer.startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRtspPlayer.stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRtspPlayer.release();
    }
}
