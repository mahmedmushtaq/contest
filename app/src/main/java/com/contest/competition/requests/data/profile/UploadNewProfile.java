package com.contest.competition.requests.data.profile;

import android.app.Activity;
import android.content.Context;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ProfileFiles;

import java.io.File;
import java.io.IOException;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 6/23/2018.
 */

public class UploadNewProfile {
    private static final MediaType MEDIA_TYPE= MediaType.parse("image/*");


    private static uploadProfileListener mUpload;

    public static interface uploadProfileListener{
        void onSuccess(String body);
        void onFail();
    }

    public static void setUploadListener(uploadProfileListener upload){
        mUpload = upload;
    }

    public static void uploadProfile(File file, String username, final Context context){

        String imageName = file.getName();
        final String path = file.getPath();
        String url = Addresses.getWebAddress()+ ProfileFiles.uploadNewProfile;
        OkHttpClient client = new OkHttpClient();


        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("profileUpload", imageName, RequestBody.create(KeyStore.MEDIA_TYPE,file))
                .addFormDataPart("username",username)
                .build();


        RequestBody body = ProgressHelper.withProgress(requestBody, new ProgressListener() {

            @Override
            public void onProgressStart(final long totalBytes) {
                super.onProgressStart(totalBytes);



            }

            @Override
            public void onProgressChanged(final long numBytes, final long totalBytes, final float percent, final float speed) {



            }

            @Override
            public void onProgressFinish() {
                super.onProgressFinish();



            }
        });

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         mUpload.onFail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mUpload.onSuccess(body);

                    }
                });
            }
        });


    }
}
