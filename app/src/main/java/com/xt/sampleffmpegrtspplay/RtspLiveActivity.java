package com.xt.sampleffmpegrtspplay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

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
        context.startActivity(starter);
        globalUrl = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        requestPermission();
        initRtsp();

        TextView backHome = findViewById(R.id.button_back);
        backHome.setOnClickListener(v -> {
            Intent intent = new Intent(RtspLiveActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
//        setFullScreen();
    }

    @SuppressLint("InlinedApi")
    public void setFullScreen() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setAttributes(attrs);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
