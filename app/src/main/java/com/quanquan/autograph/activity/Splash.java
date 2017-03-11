package com.quanquan.autograph.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quanquan.autograph.MainActivity;
import com.quanquan.autograph.R;

public class Splash extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_splash);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
