package com.contest.competition.requests.data.boostreq;

import android.app.Activity;
import android.content.Context;

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
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.BoostFiles;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 9/13/2018.
 */

public class BoostPost {
    public static void boostPost(final Context context, String postId, String loginUsername, String noOfCreditsUsed, final String reachedLimit){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("post_id",postId)
                .add("login_username",loginUsername)
                .add("reached_limit",reachedLimit)
                .add("no_of_credits_used",noOfCreditsUsed)
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ BoostFiles.boostPost).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
              
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Dialoger.dismissDialog();
                            JSONObject object = new JSONObject(body);
                            String reason = object.getString("reason");
                            Toaster.setToaster(context,reason);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
