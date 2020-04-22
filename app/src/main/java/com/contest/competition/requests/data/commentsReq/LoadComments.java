package com.contest.competition.requests.data.commentsReq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadComments;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CommentData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.CommentFiles;
import com.contest.competition.utils.views.Toaster;

import org.json.JSONArray;
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

/**
 * Created by M Ahmed Mushtaq on 7/29/2018.
 */

public class LoadComments {
    private OnLoadComments mLoadComments;
    private Context mContext;
    private ArrayHolder mArrayHolder = new ArrayHolder();

    public LoadComments(Context context){
        this.mContext = context;
    }




    public void setLoadComments(OnLoadComments comments){
        this.mLoadComments = comments;
    }

    public void loadComments(String postId,String lastCommentId){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("post_id",postId).add("last_comment_id",lastCommentId).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ CommentFiles.loadComments).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 final String body = response.body().string();
                Log.e("load comments", "onResponse: "+body );

                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            final String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                int noOfComments = object.getInt("no_of_comments");
                                if(noOfComments > 0){

                                    loadNowComments(body,object);

                                }else{

                                       // mLoadComments.onFail();
                                }

                            }else{
                                if(mLoadComments != null) {
                                    ((Activity)mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toaster.setToaster(mContext, reason);
                                        }
                                    });

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void loadNowComments(String body,JSONObject object) throws JSONException {
        String commenterNames = object.getString("commenter_names");
        String profilePics = object.getString("profile_pics");
        String usernames = object.getString("usernames");
        String commentsBody = object.getString("comments_body");
        String dateTime = object.getString("date_time");
        double noOfComments = object.getDouble("no_of_comments");
        String commentsId = object.getString("comments_id");


        JSONArray commenterNamesArray = new JSONArray(commenterNames);
        JSONArray profilePicsArray = new JSONArray(profilePics);
        JSONArray usernamesArray = new JSONArray(usernames);
        JSONArray commentsBodyArray = new JSONArray(commentsBody);
        JSONArray dateTimeArray = new JSONArray(dateTime);

        JSONArray commentIdsArray = new JSONArray(commentsId);




        for(int i = 0; i < usernamesArray.length() ; i++){
            CommentData data = new CommentData(commentsBodyArray.getString(i),commenterNamesArray.getString(i),usernamesArray.getString(i),profilePicsArray.getString(i),dateTimeArray.getString(i),noOfComments,commentIdsArray.getInt(i));
            mArrayHolder.setCommentData(data);

        }

        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mLoadComments != null){
                    mLoadComments.onSuccess(mArrayHolder.getCommentData());
                }
            }
        });

    }
}
