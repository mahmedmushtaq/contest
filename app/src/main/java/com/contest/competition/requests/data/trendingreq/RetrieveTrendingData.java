package com.contest.competition.requests.data.trendingreq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.TrendingFiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RetrieveTrendingData {
    private RetrieveNotificationCallBack mCallBack;
    private Context context;


    public interface RetrieveNotificationCallBack {
        void onFail();
        void onSuccess(ArrayList<PostData> trendingData);
    }

    public void setCallBack(RetrieveNotificationCallBack mCallBack){
        this.mCallBack = mCallBack;
    }

    public void setContext(Context context){
        this.context = context;
    }

//    public static void setClearArray(){
//        ArrayHolder.trendingData.clear();
//    }

    public  void retrieveTrendingData(String login_username){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("login_username",login_username).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+TrendingFiles.trendingPosts).post(body).build();
        Call call =  client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                        mCallBack.onFail();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
               Log.e("retrieve trending error", "onResponse: "+body );
                try {
                    JSONObject object = new JSONObject(body);
                    String result = object.getString("result");
                    String reason = object.getString("reason");
                    if(Validator.validateWebResult(result)){
                        putDataHere(object);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });
    }

    private void putDataHere(JSONObject object) throws JSONException{
        String checkData = object.getString("check_type");
        JSONArray checkDataArray = new JSONArray(checkData);
        for(int i=0;i<checkDataArray.length();i++){
            if(checkDataArray.getString(i).equals("contest")){
                putContestData(object,i);
            }else{

                putPostData(object,i);
            }
        }
    }

    private  void putContestData(JSONObject object,int position) throws JSONException{
        String leftSidePic = object.getString("left_side_pic");
        String rightSidePic = object.getString("right_side_pic");
        String leftSideName = object.getString("left_side_name");
        String rightSideName = object.getString("right_side_name");
        String tillTime = object.getString("till_time");
        String leftSideUsername = object.getString("left_side_username");
        String rightSideUsername = object.getString("right_side_username");

        String contestComplete = object.getString("contest_complete");
        String dateTime = object.getString("date_time");
        String creator = object.getString("posted_by");
        String creatorName = object.getString("posted_by_name");
        String creatorProfile = object.getString("posted_by_profile_pic");

        String leftPicNumUserVotes = object.getString("left_pic_num_user_votes");
        String rightPicNumUserVotes = object.getString("right_pic_num_user_votes");
        String contestId = object.getString("contest_id");

        String alreadyVote = object.getString("already_vote");//
        String totalVotes = object.getString("total_votes");
        String totalComments = object.getString("total_comments");
        String totalSeen = object.getString("total_seen");
        String postId = object.getString("post_id");
        String shareContestLink = object.getString("share_contest_link");


        JSONArray shareContestLinkArray = new JSONArray(shareContestLink);
        JSONArray leftSidePicArray = new JSONArray(leftSidePic);
        JSONArray rightSidePicArray = new JSONArray(rightSidePic);
        JSONArray leftSideNameArray = new JSONArray(leftSideName);
        JSONArray rightSideNameArray = new JSONArray(rightSideName);
        JSONArray tillTimeArray = new JSONArray(tillTime);
        JSONArray leftSideUsernameArray = new JSONArray(leftSideUsername);
        JSONArray rightSideUsernameArray = new JSONArray(rightSideUsername);

        JSONArray contestCompleteArray = new JSONArray(contestComplete);
        JSONArray dateTimeArray = new JSONArray(dateTime);
        JSONArray creatorArray = new JSONArray(creator);
        JSONArray creatorNameArray = new JSONArray(creatorName);
        JSONArray creatorProfileArray = new JSONArray(creatorProfile);
        JSONArray leftPicNumUserVotesArray = new JSONArray(leftPicNumUserVotes);
        JSONArray rightPicNumUserVotesArray = new JSONArray(rightPicNumUserVotes);
        JSONArray contestIdsArray = new JSONArray(contestId);
        JSONArray postIdsArray = new JSONArray(postId);
        JSONArray alreadyVoteArray = new JSONArray(alreadyVote);
        JSONArray totalVotesArray = new JSONArray(totalVotes);
        JSONArray totalCommentsArray = new JSONArray(totalComments);
        JSONArray totalSeenArray = new JSONArray(totalSeen);


        ContestData data = new ContestData();

        data.setLeftSideImage(leftSidePicArray.getString(position));
        data.setRightSideImage(rightSidePicArray.getString(position));
        data.setLeftSideName(leftSideNameArray.getString(position));
        data.setRightSideName(rightSideNameArray.getString(position));
        data.setTillTime(tillTimeArray.getString(position));
        data.setLeftSideUsername(leftSideUsernameArray.getString(position));
        data.setRightSideUsername(rightSideUsernameArray.getString(position));
        data.setContestComplete(contestCompleteArray.getString(position));
        data.setDateTime(dateTimeArray.getString(position));
        data.setCreator(creatorArray.getString(position));
        data.setCreatorName(creatorNameArray.getString(position));
        data.setCreatorProfile(creatorProfileArray.getString(position));
        data.setLeftPicNumUserVotes(leftPicNumUserVotesArray.getDouble(position));
        data.setRightPicNumUserVotes(rightPicNumUserVotesArray.getDouble(position));
        data.setContestId(contestIdsArray.getInt(position));
        data.setPostId(postIdsArray.getInt(position));
        data.setAlreadyVote(alreadyVoteArray.getInt(position));
        data.setTotalVotes(totalVotesArray.getDouble(position));
        data.setTotalComments(totalCommentsArray.getDouble(position));
        data.setTotalSeen(totalSeenArray.getDouble(position));
        data.setContestLink(shareContestLinkArray.getString(position));

        final ArrayList<PostData> tre = new ArrayList<>();


        tre.add(data);
       //  ArrayHolder.trendingData.add(data);
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mCallBack.onSuccess(tre);
            }
        });





    }
    private  void putPostData(JSONObject object,int position)throws JSONException{
        String postedBy = object.getString("posted_by");
        String singlePostedText = object.getString("single_posted_text");
        String singlePostedImage = object.getString("single_posted_image");
        String postId = object.getString("post_id");
        //  int postIdInteger = object.getInt("post_id");
        String totalVotes = object.getString("total_votes");
        String totalComments = object.getString("total_comments");
        String totalSeen = object.getString("total_seen");
        String postedByProfilePic = object.getString("posted_by_profile_pic");
        String postedByName = object.getString("posted_by_name");
        String alreadyVote = object.getString("already_vote");
        String dateTime = object.getString("date_time");
        String isPostBoosted = object.getString("is_post_boosted");
        String boostedId = object.getString("boosted_id");


        JSONArray postedByArray = new JSONArray(postedBy);
        JSONArray singlePostedTextArray = new JSONArray(singlePostedText);
        JSONArray singlePostedImageArray = new JSONArray(singlePostedImage);
        JSONArray postIdArray = new JSONArray(postId);
        JSONArray totalVotesArray = new JSONArray(totalVotes);
        JSONArray totalCommentsArray = new JSONArray(totalComments);
        JSONArray totalSeenArray = new JSONArray(totalSeen);
        JSONArray postedByProfilePicArray = new JSONArray(postedByProfilePic);
        JSONArray postedByNameArray = new JSONArray(postedByName);
        JSONArray alreadyVoteArray = new JSONArray(alreadyVote);
        JSONArray dateTimeArray = new JSONArray(dateTime);
        JSONArray isPostBoostedArray = new JSONArray(isPostBoosted);
       // JSONArray boostedIdArray = new JSONArray(boostedId);

        SimplePostData data = new SimplePostData();
        data.setPostedBy(postedByArray.getString(position));
        data.setPostedText(singlePostedTextArray.getString(position));
        data.setPostedImage(singlePostedImageArray.getString(position));
        data.setPostId(postIdArray.getInt(position));
        data.setTotalVotes(totalVotesArray.getDouble(position));
        data.setTotalComments(totalCommentsArray.getDouble(position));
        data.setTotalSeen(totalSeenArray.getDouble(position));
        data.setPostedProfile(postedByProfilePicArray.getString(position));
        data.setPostedName(postedByNameArray.getString(position));
        data.setPostedDateTime(dateTimeArray.getString(position));
        data.setAlreadyVote(alreadyVoteArray.getInt(position));
        data.setIsPostBoosted(isPostBoostedArray.getInt(position));

       // ArrayHolder.trendingData.add(data);

        final ArrayList<PostData> tre = new ArrayList<>();


        tre.add(data);


        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCallBack.onSuccess(tre);
            }
        });


    }
}
