package com.contest.competition.requests.forms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.FormsFiles;
import com.contest.competition.utils.activities.forms.ConfirmEmailForFpActivity;
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
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public class ForgotPasswordProcessRequest {

    // on line 63 secret code enter and then again send request to database to check secret code is true then send user to reset password link page
    // this feature is not available yet but soon available
    public static void setForgotPasswordReq(final String email, final Context context){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("email",email).build();
        final Request request = new Request.Builder().url(Addresses.getWebAddress()+ FormsFiles.forgotPasswordFile).post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                Log.e(KeyStore.TAG, "onResponse: forgot password = "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                          //  Dialoger.dismissDialog();
                            Dialoger.hideAlerter();
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                Intent intent = new Intent(context, ConfirmEmailForFpActivity.class);
                                intent.putExtra(KeyStore.PASS_EMAIL_FOR_CFRM,email);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                                Animatoo.animateSlideLeft(context);

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
