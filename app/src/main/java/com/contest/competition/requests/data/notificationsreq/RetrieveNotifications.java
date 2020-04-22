package com.contest.competition.requests.data.notificationsreq;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.NotificationFiles;

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

/**
 * Created by M Ahmed Mushtaq on 6/18/2018.
 */

public class RetrieveNotifications {

    private retrieveNotificationListener mRetrieveNotificationListener;

    public void setRetrieveNotificationListener(retrieveNotificationListener retrieveNotificationListener){
        this.mRetrieveNotificationListener = retrieveNotificationListener;
    }

    public interface retrieveNotificationListener{
        void onFail();
        void onSuccess(JSONObject object);
    }

    public void retrieveNotifications(String username,String received,String loadMore,int loadMoreLastId){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("received",received)
                .add("load_more",loadMore)
                .add("load_more_id",loadMoreLastId+"")
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ NotificationFiles.retrieveNotificationFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mRetrieveNotificationListener.onFail();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              String body = response.body().string();



                try {
                    final JSONObject object = new JSONObject(body);

                    mRetrieveNotificationListener.onSuccess(object);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
