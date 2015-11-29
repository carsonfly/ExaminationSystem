package com.oms.examinationsystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.oms.examinationsystem.R;

/**
 * Created by carson on 2015/7/24.
 */
public class LauncherActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 4000; //延迟6秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mainIntent = new Intent(LauncherActivity.this, MainActivity.class);
                LauncherActivity.this.startActivity(mainIntent);
                LauncherActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
