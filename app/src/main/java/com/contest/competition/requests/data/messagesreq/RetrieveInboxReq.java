package com.contest.competition.requests.data.messagesreq;

import android.util.Log;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.messageswebfiles.MessageFiles;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RetrieveInboxReq {
    public static void retrieve(String loginUsername,String conversationId,String firstId){
        //first id is used to retrieve old data
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("application_secret_key", KeyStore.APPLICATION_SECRET_KEY)
                .add("conversation_id",conversationId)
                .add("login_username",loginUsername)
                .add("first_id",firstId)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Addresses.getWebAddress()+ MessageFiles.retrieveInbox)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body  = response.body().string();
                Log.e("retrieveInbox", "onResponse: body = "+body );

            }
        });
    }
}
