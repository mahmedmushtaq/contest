package com.contest.competition.requests.data.postreq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.SimplePostFiles;
import com.contest.competition.utils.activities.front.HomeActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 9/1/2018.
 */

public class PostSimplePost {
    public static void simplePost(final Context context, String status, String loginUsername, File imageFile){
        OkHttpClient client = new OkHttpClient();

        RequestBody  body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("login_username", loginUsername)
                    .addFormDataPart("status", status)
                    .addFormDataPart("single_post", imageFile.getName(), RequestBody.create(KeyStore.MEDIA_TYPE, imageFile))
                    .build();


        Request request = new Request.Builder().url(Addresses.getWebAddress()+ SimplePostFiles.uploadSimplePostFile).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
               // Log.e("uploadSimplePost", "onResponse: body "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                Dialoger.dismissDialog();
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
