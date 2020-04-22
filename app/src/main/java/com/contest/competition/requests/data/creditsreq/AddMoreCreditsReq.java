package com.contest.competition.requests.data.creditsreq;

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
import com.contest.competition.classes.models.CreditsData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.CreditsWebFiles;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 10/11/2018.
 */

public class AddMoreCreditsReq {
    public static void addMoreCredits(final Context context, CreditsData data, String loginUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("product_id",data.getProductId())
                .add("no_of_credits_earn",data.getNoOfCreditsEarn()+"")
                .add("login_username",loginUsername)
                .add("price",data.getPrice()+"")
                .build();

        final Request request = new Request.Builder().url(Addresses.getWebAddress()+ CreditsWebFiles.addMoreCredits).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body  = response.body().string();
                Log.e("credits", "onResponse: add more crecdits = "+body );
                try {
                    JSONObject object = new JSONObject(body);
                    String result = object.getString("result");
                    final String reason = object.getString("reason");
                    if(Validator.validateWebResult(result)){
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dialoger.dismissDialog();
                                Toaster.setToaster(context,reason);
                            }
                        });
                    }else{
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dialoger.dismissDialog();
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
