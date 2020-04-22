package com.contest.competition.requests.data.contestreq;

import android.app.Activity;
import android.content.Context;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ContestFiles;
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
 * Created by M Ahmed Mushtaq on 7/23/2018.
 */

public class CancelContestReq {
    public  static  void cancelContestReq(final Context context, String id){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("id",id).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ContestFiles.cancelContest).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
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
