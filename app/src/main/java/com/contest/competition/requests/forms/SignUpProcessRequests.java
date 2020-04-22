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
import com.contest.competition.utils.activities.forms.ConfirmEmailActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public class SignUpProcessRequests {

    //on line no 67 after completion of app will not send user to login activity instead send user to check email activity

    public static  void sendReq(final String email, String password, String fullName, final Context context){

        JSONObject object = new JSONObject();
        try {
            object.put("email",email);
            object.put("password",password);
            object.put("full_name",fullName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,object.toString());
        final Request request = new Request.Builder()
                .url(Addresses.getWebAddress()+ FormsFiles.signUpFile)
                .post(body)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
               String body = response.body().string();
                Log.e(KeyStore.TAG, "onResponse: "+body );
                Log.e("singupprocess", "onResponse: "+body );
                try {
                    final JSONObject object = new JSONObject(body);
                    final String result = object.getString("result");
                    final String reason = object.getString("reason");



                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              //  Dialoger.dismissDialog();
                                Dialoger.hideAlerter();

                                if(Validator.validateWebResult(result)) {
//                                    context.startActivity(new Intent(context, LoginActivity.class));
//                                    ((Activity) context).finish();
                                    String email = null;
                                    try {
                                        email = object.getString("email");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(context,ConfirmEmailActivity.class);
                                    intent.putExtra(KeyStore.PASS_EMAIL_FOR_CFRM,email);
                                    context.startActivity(intent);
                                    ((Activity) context).finish();
                                    Animatoo.animateSlideLeft(context);
                                }else{

                                    Toaster.setToaster(context,reason);
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
