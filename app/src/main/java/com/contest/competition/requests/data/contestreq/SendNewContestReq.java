package com.contest.competition.requests.data.contestreq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.Validator;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 7/9/2018.
 */

public class SendNewContestReq {
    private static final MediaType MEDIA_TYPE= MediaType.parse("image/*");

    private static contestListener mContestListener;

    public interface contestListener{
        void onSuccessNewContest(String body);

        void onFailNewContest();

    }

    public static void setContestListener(contestListener listener){
        mContestListener = listener;
    }


    public static void createNewContest(final Context context,String creator, String leftSideUsername, String rightSideUsername, String tillTime, File leftSidePic){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("left_pic_upload",leftSidePic.getName(),RequestBody.create(MEDIA_TYPE,leftSidePic))
                .addFormDataPart("left_side_username",leftSideUsername)
                .addFormDataPart("right_side_username",rightSideUsername)
                .addFormDataPart("till_time",tillTime)
                .addFormDataPart("creator",creator)
                .build();

        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ContestFiles.createNewContestFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                 if(mContestListener != null)
                     mContestListener.onFailNewContest();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String body = response.body().string();
                Log.e("new contest", "onResponse: "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Dialoger.dismissDialog();
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason  = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                Toaster.setToaster(context,reason);
                                if(mContestListener != null)
                                    mContestListener.onSuccessNewContest(body);

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
