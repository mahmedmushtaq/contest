package com.contest.competition.requests.data.contestreq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ContestFiles;
import com.contest.competition.utils.views.Dialoger;

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
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 8/8/2018.
 */

public class DeleteContest {
    public static void deleteContest(final Context context, String contestId,String loginUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("contest_id",contestId).add("login_username",loginUsername).build();
        Request request = new Request.Builder().post(body).url(Addresses.getWebAddress()+ ContestFiles.deleteContest).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                  final String body = response.body().string();
                Log.e("delete contest", "onResponse: body are = "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Dialoger.dismissDialog();
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
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
