package com.quanquan.autograph.Utils;

import android.util.Log;

import com.quanquan.autograph.callback.BooleanCallbackWithException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Administrator on 2017-03-11.
 */

public class HttpIO {
    private static final MediaType DATA
            = MediaType.parse("multipart/related; charset=utf-8");
    private static OkHttpClient client;


    static {
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(5000, TimeUnit.MILLISECONDS);
        b.readTimeout(5000, TimeUnit.MILLISECONDS);
        b.writeTimeout(100000, TimeUnit.MILLISECONDS);
        client = b.build();
    }
    synchronized static void postDataByOkHttp(final String url, final byte[] data, final BooleanCallbackWithException callback) {

        if (data == null || data.length == 0) {
            return;
        }
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return DATA;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(data);
            }
        };

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onCallBack(false,e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String res = response.body().string();
                            Log.i("what_happend",res);
                            JSONObject jsonObject = new JSONObject(res);
                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                callback.onCallBack(true,null);
                            } else {
                                callback.onCallBack(false, new Exception(jsonObject.getString("message")));
                            }
                        } catch (IOException | JSONException e) {
                            callback.onCallBack(false,e);
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

}
