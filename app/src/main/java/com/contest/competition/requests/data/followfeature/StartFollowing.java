package com.contest.competition.requests.data.followfeature;

import android.util.Log;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.FollowFiles;

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
 * Created by M Ahmed Mushtaq on 6/17/2018.
 */

public class StartFollowing {

    private addFollowListener mAddFollowListener;

    public void setStartFollowingProcessListener(addFollowListener addFollowListener){
        this.mAddFollowListener = addFollowListener;
    }

    public interface addFollowListener{
        void onSuccess(JSONObject object);
        void onFailure();
    }

    public void startFollowing(String followUsername,String followerUsername,String check){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("follow_username",followUsername)
                .add("follower_username",followerUsername)
                .add("check",check)
                .build();


        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FollowFiles.followFile).post(body).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(mAddFollowListener != null)
                mAddFollowListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Log.e("start following", "onResponse: "+body );
                try {

                    Log.e(KeyStore.TAG, "onResponse: "+body );
                    JSONObject object = new JSONObject(body);
                    if(mAddFollowListener != null)
                    mAddFollowListener.onSuccess(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }




}
