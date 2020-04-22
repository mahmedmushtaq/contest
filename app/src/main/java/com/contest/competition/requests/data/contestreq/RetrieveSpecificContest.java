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
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ContestFiles;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 8/29/2018.
 */

public class RetrieveSpecificContest {
    private static RetrieveSpecificContestListener mDataListener;
    public interface RetrieveSpecificContestListener{
        void onRetrieveData(PostData data);
        void onFail();
    }

    public static void setDataListener(RetrieveSpecificContestListener listener){
        mDataListener = listener;
    }


    public static void loadSpecificContest(final Context context, String id,String loginUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("contest_id",id+"").add("login_username",loginUsername).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ContestFiles.retrieveSpecificContest).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e("retrieve", "onResponse: "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                loadSpecificContestData(object);
                            }else{
                                Toaster.setToaster(context,reason);
                                if(mDataListener != null)
                                    mDataListener.onFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });
    }


    private static void loadSpecificContestData(JSONObject object) throws JSONException {
        String leftSideName = object.getString("left_side_name");
        String rightSideName = object.getString("right_side_name");
        String leftSideUsername = object.getString("left_side_username");
        String rightSideUsername = object.getString("right_side_username");
        String creator = object.getString("creator");
        String leftSidePic = object.getString("left_side_pic");
        String rightSidePic = object.getString("right_side_pic");
        double leftPicNumUserVotes = object.getDouble("left_pic_num_user_votes");
        double rightPicNumUserVotes = object.getDouble("right_pic_num_user_votes");
        double totalVotes = object.getDouble("total_votes");
        double totalComments = object.getDouble("total_comments");
        double totalSeen = object.getDouble("total_seen");
        String dateTime = object.getString("date_time");
        String contestComplete = object.getString("contest_complete");
        String creatorName = object.getString("creator_name");
        String creatorProfile = object.getString("creator_profile");
        String tillTime = object.getString("till_time");
        int id = object.getInt("contest_id");
        int alreadyVote = object.getInt("already_vote");
        int postId = object.getInt("post_id");

        ContestData contestData = new ContestData();
        contestData.setLeftSideName(leftSideName);
        contestData.setRightSideName(rightSideName);
        contestData.setLeftSideUsername(leftSideUsername);
        contestData.setRightSideUsername(rightSideUsername);
        contestData.setCreator(creator);
        contestData.setLeftSideImage(leftSidePic);
        contestData.setRightSideImage(rightSidePic);
        contestData.setLeftPicNumUserVotes(leftPicNumUserVotes);
        contestData.setRightPicNumUserVotes(rightPicNumUserVotes);
        contestData.setTotalVotes(totalVotes);
        contestData.setTotalComments(totalComments);
        contestData.setTotalSeen(totalSeen);
        contestData.setDateTime(dateTime);
        contestData.setContestComplete(contestComplete);
        contestData.setCreatorName(creatorName);
        contestData.setCreatorProfile(creatorProfile);
        contestData.setTillTime(tillTime);
        contestData.setContestId(id);
        contestData.setAlreadyVote(alreadyVote);
        contestData.setPostId(postId);





        if(mDataListener != null){

            mDataListener.onRetrieveData(contestData);
        }





    }
}
