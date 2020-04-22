package com.contest.competition.requests.data.updatereq;

import android.app.Activity;
import android.content.Context;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.UpdateDataFiles;
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

public class UpdatePasswordOnlyReq {
    public static void updateData(String value, String username, final Context context){

        OkHttpClient client = new OkHttpClient();
        RequestBody body =  new FormBody.Builder()
                .add("login_username",username)
                .add("new_password",value)
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ UpdateDataFiles.updatePassword).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                JSONObject object = null;
                try {
                    object = new JSONObject(body);
                    String result = object.getString("result");
                    final String reason = object.getString("reason");
                    if(Validator.validateWebResult(result)){
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toaster.setToaster(context,reason);
                                ((Activity) context).finish();
                            }
                        });
                    }else{
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toaster.setToaster(context,reason);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
