package com.contest.competition.requests.data.profile;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.ProfileHeaderData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ProfileFiles;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 6/21/2018.
 */

public class ProfileInfoProcess {

    private profileInfoListener mProfileInfoListener;


    public interface profileInfoListener{
        void onSuccess(ProfileHeaderData data);
        void onFail();
    }

    public void setProfileInfoListener(profileInfoListener profileInfoListener){
        this.mProfileInfoListener = profileInfoListener;

    }

    public void getProfileInfo(final String username, String checkUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody body =  new FormBody.Builder().add("username",username)
                .add("check_username",checkUsername)
                .build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ProfileFiles.profileInfo).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mProfileInfoListener.onFail();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e(KeyStore.TAG, "onResponse: profile info data = "+body );

                        try {
                            JSONObject object = new JSONObject(body);
                            getProfileData(object,username);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                 });

    }

    private void getProfileData(JSONObject object,String username) throws JSONException{



        String result  = object.getString("result");
        String reason = object.getString("reason");
        if(Validator.validateWebResult(result)){
            String profilePic = object.getString("profile_pic");
            String name = object.getString("name");
            String userFollow = object.getString("user_following");

            String userFollowing ;
            double noOfFollowingUser = object.getDouble("no_of_following_users");
            double noOfFollowerUser = object.getDouble("no_of_follower_users");
            double noOfContests = object.getDouble("num_posts");
            double noOfCredits = object.getDouble("num_credits");
            double noOfContestWin = object.getDouble("num_contest_win");
            double noOfContestLose = object.getDouble("num_contest_lose");
            String subscriptionTopic = object.getString("subscription_topic");

            if(userFollow.length() == 0){
                userFollowing = null;
            }else userFollowing = userFollow;



            ProfileHeaderData data = new ProfileHeaderData(profilePic,username,name,userFollowing,noOfFollowingUser,noOfFollowerUser,noOfContests,noOfCredits,noOfContestWin,noOfContestLose,subscriptionTopic);


            if(mProfileInfoListener != null)
                mProfileInfoListener.onSuccess(data);

        }else{
            if(mProfileInfoListener != null)
                mProfileInfoListener.onFail();

        }

    }

}
