package com.contest.competition.requests.forms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.FormsFiles;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.storage.sharedpreferences.sharedpreferencesetter.LoginSharedPreferenceSetter;
import com.contest.competition.utils.activities.front.HomeActivity;
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

public class LoginProcessRequest {


    private static boolean mRememberMe;

    public static void setRememberMe(boolean rememberMe){
        mRememberMe = rememberMe;
    }

    public static void setLoginProcessReq(final String email, final String password, final Context context){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("password",password)
                .build();

        Request request = new Request.Builder()
                .url(Addresses.getWebAddress()+ FormsFiles.logInFile)
                .post(body)
                .build();



        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                Log.e("loginprocessreq", "onResponse: "+body );

                try {
                    final JSONObject object = new JSONObject(body);

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        //    Dialoger.dismissDialog();
                            Dialoger.hideAlerter();



                            try {
                                String result = object.getString("result");
                                String reason = object.getString("reason");

                                if(Validator.validateWebResult(result)){
                                    String username = object.getString("username");

                                    String name = object.getString("name");
                                    int id = object.getInt("id");
                                    String profilePath = object.getString("profile_pic");
                                    String openFirePassword = object.getString("openfire_password");
//                                    String subscriptionTopic = object.getString("subscription_topic");


                                    LoginSharedPreferenceSetter.setLoginSharedPreference(context,username,name,id,profilePath,openFirePassword,email,password,"",mRememberMe);

                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }else{
                                    Toaster.setToaster(context,reason);
                                }


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
