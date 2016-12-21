package com.delta.smt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.delta.smt.ui.login.LoginActivity;


/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/12 16:13
 */


public class StartActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            StartActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        handler.sendEmptyMessageDelayed(1, 1000);
    }
}
