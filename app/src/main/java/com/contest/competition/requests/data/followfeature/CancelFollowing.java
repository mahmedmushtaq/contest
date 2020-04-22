package com.contest.competition.requests.data.followfeature;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.FollowFiles;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 6/27/2018.
 */

public class CancelFollowing {

    private cancelFollowingListener mCancelFollowingListener;

    public interface cancelFollowingListener{
        void onSuccess(String body);
        void onFail();
    }

    public void setCancelFollowingListener(cancelFollowingListener cancelFollowingListener){
        this.mCancelFollowingListener = cancelFollowingListener;
    }

    public void cancelFollowing(String username,String cancelUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username",username).add("cancel_username",cancelUsername).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FollowFiles.cancelFollowingFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mCancelFollowingListener.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String body = response.body().string();
               mCancelFollowingListener.onSuccess(body);
            }
        });
    }

    public  void cancelFollowingReq(String toUsername,String fromUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("to_username",toUsername).add("from_username",fromUsername).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+FollowFiles.cancelFollowingReqFile).post(body).build();
       Call call = client.newCall(request);
       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
                if(mCancelFollowingListener != null)
                    mCancelFollowingListener.onFail();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String body = response.body().string();
               if(mCancelFollowingListener != null)
                   mCancelFollowingListener.onSuccess(body);

           }
       });

    }

}
