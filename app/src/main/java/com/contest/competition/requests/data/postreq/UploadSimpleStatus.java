package com.contest.competition.requests.data.postreq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
import com.contest.competition.classes.webfiles.SimplePostFiles;
import com.contest.competition.utils.activities.front.HomeActivity;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 9/9/2018.
 */

public class UploadSimpleStatus {
    public static void uploadSimpleStatus(final Context context, String status, String loginUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("status",status).add("login_username",loginUsername).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ SimplePostFiles.uploadSimpleStatus).post(body).build();
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
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                Toaster.setToaster(context,reason);
                                context.startActivity(new Intent(context, HomeActivity.class));

                                ((Activity)context).finish();
                            }else{
                                Toaster.setToaster(context,reason);
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
