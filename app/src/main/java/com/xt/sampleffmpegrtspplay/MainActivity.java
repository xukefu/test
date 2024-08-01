package com.xt.sampleffmpegrtspplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private static final String PREFS_KEY_IS_LOGGED_IN = "IsLoggedIn2";
    private static final String PREFS_NAME = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isLoggedIn = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getBoolean(PREFS_KEY_IS_LOGGED_IN, false);

        if (!isLoggedIn) {
            showPasswordDialog();
        } else {
            initActivity();
        }
    }

    private void initActivity() {
        // 初始化控件
        etRtspUrl1 = findViewById(R.id.et_rtsp_url1);
        btnConnect1 = findViewById(R.id.btn_connect1);
        btnSave1 = findViewById(R.id.btn_save1);

        etRtspUrl2 = findViewById(R.id.et_rtsp_url2);
        btnConnect2 = findViewById(R.id.btn_connect2);
        btnSave2 = findViewById(R.id.btn_save2);

        etRtspUrl3 = findViewById(R.id.et_rtsp_url3);
        btnConnect3 = findViewById(R.id.btn_connect3);
        btnSave3 = findViewById(R.id.btn_save3);

        etRtspUrl4 = findViewById(R.id.et_rtsp_url4);
        btnConnect4 = findViewById(R.id.btn_connect4);
        btnSave4 = findViewById(R.id.btn_save4);

        //回显url
        initUrl();

        // btnConnect1
        btnConnect1.setOnClickListener(v -> {
            String url = etRtspUrl1.getText().toString();
            if (url.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "请先填写url", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "开始连接:" + url, Toast.LENGTH_SHORT);
            toast.show();

            play(url);

        });

        // btnConnect2
        btnConnect2.setOnClickListener(v -> {
            String url = etRtspUrl2.getText().toString();
            if (url.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "请先填写url", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            play(url);
        });

        // btnConnect4
        btnConnect3.setOnClickListener(v -> {
            String url = etRtspUrl3.getText().toString();
            if (url.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "请先填写url", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            play(url);
        });

        // btnConnect4
        btnConnect4.setOnClickListener(v -> {
            String url = etRtspUrl4.getText().toString();
            if (url.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "请先填写url", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            play(url);
        });

        // 为保存按钮设置点击事件
        btnSave1.setOnClickListener(v -> {
            // 这里可以保存RTSP URL到SharedPreferences或其他存储
            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("url1", etRtspUrl1.getText().toString());
            editor.apply();
            saveToast();
            hideKeyboard(v);
        });

        // 为保存按钮设置点击事件
        btnSave2.setOnClickListener(v -> {
            // 这里可以保存RTSP URL到SharedPreferences或其他存储
            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("url2", etRtspUrl2.getText().toString());
            editor.apply();
            saveToast();
            hideKeyboard(v);
        });

        // 为保存按钮设置点击事件
        btnSave3.setOnClickListener(v -> {
            // 这里可以保存RTSP URL到SharedPreferences或其他存储
            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("url3", etRtspUrl3.getText().toString());
            editor.apply();
            saveToast();
            hideKeyboard(v);
        });

        // 为保存按钮设置点击事件
        btnSave4.setOnClickListener(v -> {
            // 这里可以保存RTSP URL到SharedPreferences或其他存储
            SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("url4", etRtspUrl4.getText().toString());
            editor.apply();
            saveToast();
            hideKeyboard(v);
        });

        //默认播放上次的url
        SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
        String lastPlayUrl = sharedPreferences.getString("lastPlayUrl", "");
        if (lastPlayUrl != null && !lastPlayUrl.isEmpty()) {
            //上次播放狀態
            String lastPlayStatus = sharedPreferences.getString("lastPlayStatus", "");
            Log.e("上次播放狀態", lastPlayStatus);
            if (lastPlayStatus != null && lastPlayStatus.equals("false")) {
                Log.e("清除上次播放url", "");
                return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "继续播放上次的地址:" + lastPlayUrl, Toast.LENGTH_SHORT);
            toast.show();
            play(lastPlayUrl);
        }

    }

    private void saveToast() {
        Toast toast = Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void play(String url) {
        try {
            RtspLiveActivity.start(MainActivity.this, url);
            recordLastPlay(url);
        } catch (Exception e) {
            Log.e("Exception2222222222222", e.toString());
        }
    }

    private void recordLastPlay(String url) {
        SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastPlayUrl", url);
        editor.apply();
    }

    private void initUrl() {
        SharedPreferences sharedPreferences = getSharedPreferences("RtspUrlsPrefs", Context.MODE_PRIVATE);

        String savedUrl1 = sharedPreferences.getString("url1", "");
        if (savedUrl1 != null && !savedUrl1.isEmpty()) {
            etRtspUrl1.setText(savedUrl1);
        }

        String savedUrl2 = sharedPreferences.getString("url2", "");
        if (savedUrl2 != null && !savedUrl2.isEmpty()) {
            etRtspUrl2.setText(savedUrl2);
        }

        String savedUrl3 = sharedPreferences.getString("url3", "");
        if (savedUrl3 != null && !savedUrl3.isEmpty()) {
            etRtspUrl3.setText(savedUrl3);
        }

        String savedUrl4 = sharedPreferences.getString("url4", "");
        if (savedUrl4 != null && !savedUrl4.isEmpty()) {
            etRtspUrl4.setText(savedUrl4);
        }
    }

    private void showPasswordDialog() {
        final EditText passwordEditText = new EditText(this);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入激活码");
        builder.setView(passwordEditText);
        builder.setPositiveButton("确认", null);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(d -> {
            Button positiveButton = ((AlertDialog) d).getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String enteredPassword = passwordEditText.getText().toString();
                String tempPass = genTempPassword();
                if (enteredPassword.equals(tempPass)) {
                    // 密码正确，记住登录状态
                    Toast.makeText(getApplicationContext(), "激活成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean(PREFS_KEY_IS_LOGGED_IN, true);
                    editor.apply();
                    initActivity();
                    dialog.dismiss();
                    hideKeyboard(passwordEditText);
                } else {
                    hideKeyboard(v);
                    Toast toast = Toast.makeText(getApplicationContext(), "激活码错误", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    passwordEditText.setText("");
                }
            });
        });

        dialog.show();
    }

    private String genTempPassword() {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis()).substring(0, 8);
        long temp = Long.parseLong(currentTimeMillis) * 18;
        Log.e("hash", "MD5 temp: " + temp);
        String md5String = toHexString(md5(String.valueOf(temp))).substring(0, 8);
        Log.e("hash", "MD5 Hash: " + md5String);
        return md5String;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}


