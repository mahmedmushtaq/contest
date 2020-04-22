package com.contest.competition.requests.data.contestreq;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ContestFiles;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

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

/**
 * Created by M Ahmed Mushtaq on 7/23/2018.
 */

public class AcceptNewContest {


    public  static  void contestReqAccepted(final Context context, final File imageFile, String id,String login_username, final ImageView updateView){
        OkHttpClient client = new OkHttpClient();


        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("contest_req_id",id)
                .addFormDataPart("upload_contest_img",imageFile.getName(),MultipartBody.create(KeyStore.MEDIA_TYPE,imageFile))
                .addFormDataPart("login_username",login_username)
                .build();


        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ContestFiles.acceptContest).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e("new contest", "onResponse: "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Dialoger.dismissDialog();
                            JSONObject object = new JSONObject(body);
                            String reason = object.getString("reason");
                            Toaster.setToaster(context,reason);
                            Uri uri = Uri.parse(imageFile.getPath());
                            updateView.setImageURI(uri);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
