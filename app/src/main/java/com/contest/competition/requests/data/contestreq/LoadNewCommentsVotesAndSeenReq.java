package com.contest.competition.requests.data.contestreq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

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
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ContestFiles;

/**
 * Created by M Ahmed Mushtaq on 8/21/2018.
 */

public class LoadNewCommentsVotesAndSeenReq {

    private  static  onLoadNewContestData mOnLoadNewContestData;

    public interface onLoadNewContestData{
        void onSuccess(int totalComments,int totalVotes,int totalSeen);
        void onFail(String reason);
    }

    public static void setOnLoadNewContestData(onLoadNewContestData loadNewContestData){
        mOnLoadNewContestData = loadNewContestData;
    }

    public static void loadNewData(String postId, final Context context){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("post_id",postId).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ContestFiles.loadNewCommentsVotesAndSeen).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String body = response.body().string();
                Log.e("new data", "onResponse: body"+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                int totalComments = object.getInt("total_comments");
                                int totalVotes = object.getInt("total_votes");
                                int totalSeen = object.getInt("total_seen");
                                if(mOnLoadNewContestData != null)
                                    mOnLoadNewContestData.onSuccess(totalComments,totalVotes,totalSeen);
                            }else{
                                if(mOnLoadNewContestData!= null)
                                    mOnLoadNewContestData.onFail(reason);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
