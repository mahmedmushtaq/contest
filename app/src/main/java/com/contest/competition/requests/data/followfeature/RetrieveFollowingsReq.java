package com.contest.competition.requests.data.followfeature;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingFollowFeaturesData;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.FollowData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.FollowFiles;

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

public class RetrieveFollowingsReq {
    private ArrayHolder mArrayHolder = new ArrayHolder();
    private Context mContext;
    private OnLoadingFollowFeaturesData mOnLoadingFollowFeaturesData;

    public void setOnLoadingFollowFeaturesData(OnLoadingFollowFeaturesData onLoadingFollowFeaturesData) {
        mOnLoadingFollowFeaturesData = onLoadingFollowFeaturesData;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void getData(String username,final String last_id){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username",username)
                .add("last_id",last_id)
                .build();

        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FollowFiles.followingUserInfoFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e(KeyStore.TAG, "onResponse: "+body );

                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                String names = object.getString("names");
                                String usernames = object.getString("usernames");
                                String profiles = object.getString("profile_pics");
                                String ids = object.getString("ids");
                                String userId = object.getString("user_id");

                                JSONArray namesArray = new JSONArray(names);
                                JSONArray usernamesArray = new JSONArray(usernames);
                                JSONArray profilesArray = new JSONArray(profiles);
                                JSONArray idsArray = new JSONArray(ids);
                                JSONArray userIdArray = new JSONArray(userId);

                                for(int i = 0 ; i < namesArray.length() ; i++){
                                    FollowData data = new FollowData(HighLighter.capitalEachWordFirstLetter(namesArray.getString(i)),usernamesArray.getString(i),profilesArray.getString(i),null,idsArray.getInt(i),userIdArray.getInt(i));
                                    mArrayHolder.setFollowingData(data);
                                }

                            }



                            if(mOnLoadingFollowFeaturesData != null) {
                                ((Activity)mContext).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnLoadingFollowFeaturesData.onLoad(mArrayHolder.getFollowingData());
                                    }
                                });

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });



    }

}
