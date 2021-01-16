package com.contest.competition.requests.forms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.CreateDirectUserByFirebaseSignIn;
import com.contest.competition.classes.webfiles.FormsFiles;
import com.contest.competition.storage.sharedpreferences.sharedpreferencesetter.LoginSharedPreferenceSetter;
import com.contest.competition.utils.activities.forms.ConfirmEmailActivity;
import com.contest.competition.utils.activities.front.HomeActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateUserByGoogleSignIn  {

    public static  void sendReq(final String email, final String password, final String fullName, final String idToken, final Context context, final GoogleSignInClient googleSignInClient){


        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("email",email)
                .add("full_name",fullName)
                .add("password",password)

                .add("id_token",idToken)
                .build();

        final Request request = new Request.Builder()
                .url(Addresses.getWebAddress()+ CreateDirectUserByFirebaseSignIn.createUserByGoogleSignUp)
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
                Log.e("googleSignIn", "onResponse: "+body );
                try {
                    final JSONObject object = new JSONObject(body);
                    final String result = object.getString("result");
                    final String reason = object.getString("reason");





                            //  Dialoger.dismissDialog();
                            Dialoger.hideAlerter();

                            if(Validator.validateWebResult(result)) {
//                                    context.startActivity(new Intent(context, LoginActivity.class));
//                                    ((Activity) context).finish();
                                final String username = object.getString("username");
                                final int id = object.getInt("id");
                                String profilePic = object.getString("profile_pic");
                                String openFirePassword = object.getString("openfire_password");
                              //  String subscriptionTopic = object.getString("subscription_topic");




                                LoginSharedPreferenceSetter.setLoginSharedPreference(context,username,fullName,id,profilePic,openFirePassword,email,password,"",false);

                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Dialoger.hideAlerter();
                                        Intent intent = new Intent(context, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });


                            }else{
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FirebaseAuth.getInstance().signOut();
                                        if(googleSignInClient != null)
                                           googleSignInClient.signOut();

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
