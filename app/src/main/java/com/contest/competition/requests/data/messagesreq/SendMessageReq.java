package com.contest.competition.requests.data.messagesreq;

import android.util.Log;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.messageswebfiles.MessageFiles;
import com.contest.competition.utils.views.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendMessageReq {
    public static void sendMessageReq(String fromUsername,String toUsername,String sendToTopic,int secretKey,String messageBody,String fromTopic){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("from_username",fromUsername)
                .add("to_username",toUsername)
                .add("send_to_topic",sendToTopic)
                .add("secret_key",secretKey+"")
                .add("application_secret_key", KeyStore.APPLICATION_SECRET_KEY)
                .add("message_body",messageBody)
                .add("from_topic",fromTopic)
                .build();

        final Request request = new Request.Builder()
                .url(Addresses.getWebAddress()+ MessageFiles.sendMessage)
                .post(body)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                //Log.e("sendMessageReq", "onResponse: "+body );




            }
        });
    }


}
