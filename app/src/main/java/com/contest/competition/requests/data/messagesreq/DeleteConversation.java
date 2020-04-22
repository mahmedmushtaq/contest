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

public class DeleteConversation  {
    public static void deleteConversation(String id){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("application_secret_key", KeyStore.APPLICATION_SECRET_KEY)
                .add("id",id)
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Addresses.getWebAddress()+ MessageFiles.deleteConversation)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String body = response.body().string();
                Log.e("deleteConversation", "onResponse: request send = "+body );
            }
        });
    }
}
