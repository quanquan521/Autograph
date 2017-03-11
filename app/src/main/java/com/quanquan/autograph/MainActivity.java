package com.quanquan.autograph;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanquan.autograph.Utils.HttpUtlis;
import com.quanquan.autograph.callback.BooleanCallbackWithException;
import com.quanquan.autograph.view.LinePathView;

import java.io.IOException;

import static android.R.attr.password;

public class MainActivity extends AppCompatActivity {
    private Button  clear;
    private  Button  save;
    private LinePathView myview;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               myview.clear();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        Environment.getExternalStorageDirectory().toString()+"/ECG-DATA/qm.png"
                View contentView = LayoutInflater.from(context).inflate(R.layout.layout_user, null,false);
                final  EditText login_user= (EditText) contentView.findViewById(R.id.et_login_user);
                final  EditText password= (EditText) contentView.findViewById(R.id.et_password);
                 new AlertDialog.Builder(context)
                        .setView(contentView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                      myview.send(login_user.getText().toString(), password.getText().toString(), false, 10, new BooleanCallbackWithException() {
                                          @Override
                                          public void onCallBack(Boolean b, Exception e) {
                                              if (e==null){
                                                  runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                                                          myview.clear();
                                                      }
                                                  });
                                              }else {
                                                  runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          Toast.makeText(context, "请检查用户名密码。", Toast.LENGTH_SHORT).show();

                                                      }
                                                  });

                                              }
                                          }
                                      });



                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();






            }
        });
    }

    private void init() {
        clear= (Button) findViewById(R.id.bt_clear);
        save= (Button) findViewById(R.id.bt_save);
        myview= (LinePathView) findViewById(R.id.my_view);
    }
}
