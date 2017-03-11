package com.quanquan.autograph.Utils;

import android.util.Log;

import com.quanquan.autograph.callback.BooleanCallbackWithException;

import java.util.HashMap;

import okhttp3.FormBody;

/**
 * Created by Administrator on 2017-03-11.
 */

public class HttpUtlis {
    private static String APP_SIGN="http://101.200.174.235/api/app_set_digital_signature";
    public synchronized static void PostSendData(String login_user,String password,final byte[] bytes, final BooleanCallbackWithException callback) {
       String url=APP_SIGN+"?login_user="+login_user+"&password="+password;
        HttpIO.postDataByOkHttp(url, bytes, new BooleanCallbackWithException() {
            @Override
            public void onCallBack(Boolean b, Exception e) {
                if (e==null){
                    callback.onCallBack(true,null);
                }else {
                    callback.onCallBack(false,e);
                }
            }
        });





    }


}
