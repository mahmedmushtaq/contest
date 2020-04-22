package com.contest.competition.requests.data.contestreq;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.webfiles.Addresses;

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
import com.contest.competition.classes.webfiles.VoteFiles;

/**
 * Created by M Ahmed Mushtaq on 7/26/2018.
 */

public class Vote {

   private static voteListener mVoteListener;

    public interface voteListener{
        void onSuccess(String result,String reason);
    }

    public static void setVoteListener(voteListener voteListener){
        mVoteListener = voteListener;
    }

    public static void vote(final Context context, String loginUsername, final String postId, String leftPicVote, String rightPicVote,String voteType){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login_username",loginUsername)
                .add("post_id",postId)
                .add("left_pic_vote",leftPicVote)
                .add("right_pic_vote",rightPicVote)
                .add("vote_type",voteType)
                .build();

        Request request = new Request.Builder()
                .url(Addresses.getWebAddress()+ VoteFiles.voteFile)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String body = response.body().string();
                Log.e("body", "onResponse: body vote contest = "+body );

                try {
                    final JSONObject object = new JSONObject(body);

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                String result = object.getString("result");
                                String reason = object.getString("reason");
                                if(mVoteListener != null)
                                    mVoteListener.onSuccess(result,reason);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });



    }
}
