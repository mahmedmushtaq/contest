package com.contest.competition.requests.data.notificationsreq;

import android.util.Log;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.NotificationFiles;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 6/26/2018.
 */

public class NotificationsReq {

    public static void notificationReceived(String username){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ NotificationFiles.notificationReceived).post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e(KeyStore.TAG, "onResponse: notification body"+response.body().string() );

            }
        });
    }

    public static void notificationOpened(int id,String username){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("id",id+"").add("username",username)
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+NotificationFiles.notificationOpened).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.e(KeyStore.TAG, "run: "+response.body().string() );

            }
        });
    }
}
