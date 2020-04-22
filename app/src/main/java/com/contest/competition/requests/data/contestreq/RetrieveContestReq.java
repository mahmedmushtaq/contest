package com.contest.competition.requests.data.contestreq;

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
import com.contest.competition.classes.models.ContestReqData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ContestFiles;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 8/29/2018.
 */

public class RetrieveContestReq {
    private static RetrieveContestReqListener mListener;
    public  interface RetrieveContestReqListener{
        void onRetrieveData(PostData data);
    }
    public static void setRetrieveContestReqListener(RetrieveContestReqListener retrieveContestReqListener){
        mListener = retrieveContestReqListener;
    }

    public static void loadContestReq(final Context context, String contestReqId){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("contest_req_id",contestReqId).build();
        Request request = new Request.Builder().post(requestBody).url(Addresses.getWebAddress()+ ContestFiles.retrieveContestReq).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String body = response.body().string();
                Log.e("RetrieveContestReq", "onResponse: retrieve contest req = "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                String leftSideName = object.getString("left_side_name");
                                String leftSideUsername = object.getString("left_side_username");
                                String rightSideName = object.getString("right_side_name");
                                String rightSideUsername = object.getString("right_side_username");
                                String leftSidePic = object.getString("left_side_pic");
                                String rightSidePic = object.getString("right_side_pic");
                                String dateTime = object.getString("date_time");
                                String creatorName = object.getString("creator_name");
                                String creatorProfile = object.getString("creator_profile");
                                String till_time = object.getString("till_time");

                                ContestReqData data = new ContestReqData();
                                data.setLeftSideName(leftSideName);
                                data.setLeftSideUsername(leftSideUsername);
                                data.setRightSideName(rightSideName);
                                data.setRightSideUsername(rightSideUsername);
                                data.setLeftSideImage(leftSidePic);
                                data.setRightSideImage(rightSidePic);
                                data.setDateTime(dateTime);
                                data.setCreatorName(creatorName);
                                data.setCreatorProfile(creatorProfile);
                                data.setTillTime(till_time);



                                if(mListener != null)
                                    mListener.onRetrieveData(data);

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
