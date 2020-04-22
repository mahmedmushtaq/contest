package com.contest.competition.requests.data.commentsReq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.CommentFiles;
import com.contest.competition.utils.views.Dialoger;
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

/**
 * Created by M Ahmed Mushtaq on 7/30/2018.
 */

public class InsertComment {
    private Context mContext;
    public InsertComment(Context context){
        mContext = context;
    }

    public void insertComment(String postId,String commentBody,String loginUsername,String commentType){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("post_id",postId)
                .add("comment_body",commentBody)
                .add("login_username",loginUsername)
                .add("comment_type",commentType)
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ CommentFiles.commentPost).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                  String body = response.body().string();
                Log.e("insert comment", "onResponse: body = "+body );
                try {
                    JSONObject object = new JSONObject(body);
                    String result = object.getString("result");
                    final String reason = object.getString("reason");
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dialoger.dismissDialog();
                            Toaster.setToaster(mContext,reason);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
