package com.contest.competition.requests.data.postreq;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.classesforactivity.MainPageDataLoadingClass;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.models.SimpleTvData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.SimplePostFiles;
import com.contest.competition.classes.webfiles.TrendingFiles;

import com.contest.competition.utils.views.PostView;

/**
 * Created by M Ahmed Mushtaq on 9/4/2018.
 */

public class RetrieveAllPosts {

    private static RetrieveAllPostsDataListener mDataListener;
    private static ArrayList<Integer> contestIds = new ArrayList<>();
    private static ArrayList<Integer> postIds = new ArrayList<>();
    private static ArrayList<Integer> boostedIds = new ArrayList<>();
    private static int checkPost;




    public static void setCheckPost(int check_post){
        checkPost = check_post;
    }



    public interface RetrieveAllPostsDataListener{
        void onRetrieveSinglePostData(PostData data,int postId);
        void onRetrieveContestData(PostData data,int postId,int contestIds);
        void onRetrieveOnlyIds(ArrayList<Integer> contestIds,ArrayList<Integer> postIds);
        void updateView();
        void hidePb();
        void boostedId(ArrayList<Integer> boostedIds);
        void onEnd(PostData data);
        void onFail();
    }

    public static void setDataListener(RetrieveAllPostsDataListener listener){
        mDataListener = listener;
    }

    public static void retrievePost(final Context context, final String loginUsername, final String profileUsername, final String lastPostId, String lastContestId, final String firstPostId, final String firstContestId, final String lastBoostedId){
        contestIds.clear();
        postIds.clear();
        boostedIds.clear();

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login_username",loginUsername)
                .add("profile_username",profileUsername)
                .add("last_post_id",lastPostId)
                .add("last_contest_id",lastContestId)
                .add("first_post_id",firstPostId)
                .add("first_contest_id",firstContestId)
                .add("last_boosted_id",lastBoostedId)
                .build();

        Request request = new Request.Builder().post(body).url(Addresses.getWebAddress()+ SimplePostFiles.retrieveAllPostsFile).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(mDataListener != null)
                    mDataListener.hidePb();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String body = response.body().string();
              Log.e("posts", "onResponse: all Posts ====  = "+body );

                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(mDataListener != null)
                                mDataListener.hidePb();

                            if(Validator.validateWebResult(result)){

                                String checkType = object.getString("check_type");
                                JSONArray  checkTypeArray = new JSONArray(checkType);
                                for(int i = 0; i < checkTypeArray.length(); i++){
                                    String check = checkTypeArray.getString(i);
                                    if(check.equals("contest")){
                                        putContestData(object,i,context);
                                    }else{
                                        putPostData(object,i,context);
                                    }
                                }



                               if(mDataListener != null){

                                    mDataListener.boostedId(boostedIds);
                                    mDataListener.onRetrieveOnlyIds(contestIds,postIds);
                                    mDataListener.updateView();
                               }



                            }else if(result.equals("unSuccessful")){

                                if(mDataListener != null)
                                    mDataListener.onFail();
                            }else if(result.equals("furtherResult")){
                                //further result cannot found
                                SimpleTvData data = new SimpleTvData();
                                data.setSimpleTv(reason);
                                if(mDataListener != null) {

                                    mDataListener.onEnd(data);
                                    //mDataListener.onEnd(data);
                                    mDataListener.updateView();
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    private static void putContestData(JSONObject object, int position, final Context context) throws JSONException{
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





        JSONArray leftSidePicArray = new JSONArray(leftSidePic);
        JSONArray rightSidePicArray = new JSONArray(rightSidePic);
        JSONArray leftSideNameArray = new JSONArray(leftSideName);
        JSONArray rightSideNameArray = new JSONArray(rightSideName);
        JSONArray tillTimeArray = new JSONArray(tillTime);
        JSONArray leftSideUsernameArray = new JSONArray(leftSideUsername);
        JSONArray rightSideUsernameArray = new JSONArray(rightSideUsername);
        JSONArray shareContestLinkArray = new JSONArray(shareContestLink);

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

       // localArrayList.add(data);
        try {
            contestIds.add(contestIdsArray.getInt(position));
        }catch (IndexOutOfBoundsException e){

        }

       // loadImageByGlide(context,data.getLeftSideImage());
        //loadImageByGlide(context,data.getRightSideImage());

        if(mDataListener != null)
            mDataListener.onRetrieveContestData(data,postIdsArray.getInt(position),contestIdsArray.getInt(position));



      //  Log.e("RetrieveAllPosts", "putContestData: contest ids are = "+contestIdsArray.getInt(position));





    }
    private static void putPostData(JSONObject object, int position, final Context context)throws JSONException{
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
        JSONArray boostedIdArray = new JSONArray(boostedId);

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

        if(isPostBoostedArray.getInt(position) == PostView.IS_POST_BOOSTED){
            boostedIds.add(boostedIdArray.getInt(position));

        }

        // load the images into the cache

      // loadImageByGlide(context,data.getPostedImage());


        //localArrayList.add(data);
        try {

            postIds.add(postIdArray.getInt(position));
        }catch (IndexOutOfBoundsException e){

        }

        if(mDataListener != null)
            mDataListener.onRetrieveSinglePostData(data,postIdArray.getInt(position));





       // Log.e("RetrieveAllPosts", "putContestData: simple post  ids are = "+postIdArray.getInt(position));






    }

    private  static void loadImageByGlide(final Context context, String url){
        final Uri uri = Uri.parse(Addresses.getWebAddress()+url);
        try {
            Glide.with(context)
                    .downloadOnly()
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)) // Cache resource before it's decoded
                    .load(uri)
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get(); // Called on background thread
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        ((Activity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("loadImage", "run: load image " );
//                Glide.with(context)
//                        .load(uri)
//                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
//                        .preload();
//            }
//        });
    }
}
