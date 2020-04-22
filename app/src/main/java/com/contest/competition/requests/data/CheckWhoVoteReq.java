package com.contest.competition.requests.data;

import android.app.Activity;
import android.content.Context;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingCheckSawAndVoteData;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SawVoteCheckData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.VoteFiles;

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

public class CheckWhoVoteReq {
    private ArrayHolder mArrayHolder = new ArrayHolder();
    private OnLoadingCheckSawAndVoteData mOnLoadingCheckSawAndVoteData;

    public void setOnLoadingCheckSawAndVoteData(OnLoadingCheckSawAndVoteData onLoadingCheckSawAndVoteData) {
        mOnLoadingCheckSawAndVoteData = onLoadingCheckSawAndVoteData;
    }
    public void whoVotedData(final Context context, String postId, String lastId){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("post_id", postId).add("last_id",lastId).build();
        Request request = new Request.Builder().post(body).url(Addresses.getWebAddress()+ VoteFiles.whoVotedFile).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();

                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                String names = object.getString("names");
                                String usernames = object.getString("usernames");
                                String ids = object.getString("ids");
                                JSONArray namesArray = new JSONArray(names);
                                JSONArray usernamesArray = new JSONArray(usernames);
                                JSONArray idsArray = new JSONArray(ids);
                                for(int i = 0; i < namesArray.length(); i++){
                                    SawVoteCheckData data = new SawVoteCheckData(namesArray.getString(i),usernamesArray.getString(i),idsArray.getInt(i));
                                    mArrayHolder.setCheckWhoVotedData(data);

                                }

                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(mOnLoadingCheckSawAndVoteData != null)
                                            mOnLoadingCheckSawAndVoteData.load(mArrayHolder.getCheckWhoVotedData());
                                    }
                                });




                            }else{
                                // Toaster.setToaster(getContext(),reason);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }
}
