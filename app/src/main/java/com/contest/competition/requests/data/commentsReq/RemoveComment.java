package com.contest.competition.requests.data.commentsReq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.CommentFiles;
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
 * Created by M Ahmed Mushtaq on 7/29/2018.
 */

public class RemoveComment {
    public static void remove(String id,String postId, final Context context){
        OkHttpClient client = new OkHttpClient();
        final RequestBody body = new FormBody.Builder().add("id",id).add("post_id",postId).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ CommentFiles.removeComment).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseBody =response.body().string();
                    Log.e("remove comments", "onResponse: error = "+responseBody );
                    final JSONObject object = new JSONObject(responseBody);

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toaster.setToaster(context,object.getString("reason"));
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
