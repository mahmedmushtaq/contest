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
 * Created by M Ahmed Mushtaq on 7/22/2018.
 */

public class CheckFollowingReq {

    private FollowReqCheckListener mCheckFollowingReq;

    public interface FollowReqCheckListener{
        void onCheck(String body);
    }

    public void setCheckFollowingReq(FollowReqCheckListener followingReq){
        this.mCheckFollowingReq = followingReq;
    }
    public void setCheck(String id){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("id",id).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FollowFiles.retrieveFollowReq).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
               if(mCheckFollowingReq != null)
                   mCheckFollowingReq.onCheck(body);
            }
        });
    }


}
