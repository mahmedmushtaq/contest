package com.contest.competition.requests.data.postreq;

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
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.SimplePostFiles;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 9/1/2018.
 */

public class RetrieveSimplePost {
    private static RetrieveSinglePostListener mListener;
    public interface RetrieveSinglePostListener{
        void onRetrieve(SimplePostData data);
        void onFail();
    }

    public static void setRetrieveListener(RetrieveSinglePostListener listener){
        mListener = listener;
    }

    public static void retrieveSinglePost(final Context context, String postId, String loginUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("post_id",postId).add("login_username",loginUsername).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ SimplePostFiles.retrieveSinglePostFile).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
//                Log.e("RetrieveSimplePost", "onResponse: body "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                setRetrieveData(object);
                            }else{
                                Toaster.setToaster(context,reason);
                                if(mListener != null)
                                    mListener.onFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private static void setRetrieveData(JSONObject object) throws JSONException{
        String postedBy = object.getString("posted_by");
        String postedProfile = object.getString("posted_profile");
        String postedByName = object.getString("posted_by_name");
        String postedText = object.getString("posted_text");
        String postedImage = object.getString("posted_image");
        double totalVotes = object.getDouble("total_votes");
        double totalComments = object.getDouble("total_comments");
        double totalSeen = object.getDouble("total_seen");
        int postId = object.getInt("post_id");
        String dateTime = object.getString("date_time");
        int alreadyVote = object.getInt("already_vote");



        SimplePostData data = new SimplePostData();

        data.setPostedBy(postedBy);
        data.setPostedProfile(postedProfile);
        data.setPostedName(postedByName);
        data.setPostedText(postedText);
        data.setPostedImage(postedImage);
        data.setTotalVotes(totalVotes);
        data.setTotalComments(totalComments);
        data.setTotalSeen(totalSeen);
        data.setPostedDateTime(dateTime);
        data.setPostId(postId);
        data.setAlreadyVote(alreadyVote);

        if(mListener != null)
            mListener.onRetrieve(data);

    }
}
