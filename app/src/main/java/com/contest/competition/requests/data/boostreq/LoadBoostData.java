package com.contest.competition.requests.data.boostreq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.BoostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.BoostFiles;

/**
 * Created by M Ahmed Mushtaq on 9/30/2018.
 */

public class LoadBoostData {
    private static LoadBoostDataListener mLoadListener;
    public interface LoadBoostDataListener{
        void onRetrieve(ArrayList<BoostData> boostData);
    }
    public static  void setBoostListener(LoadBoostDataListener dataListener){
        mLoadListener = dataListener;
    }

    public static void loadBoostData(){
        OkHttpClient client = new OkHttpClient();
        String url = Addresses.getWebAddress()+ BoostFiles.retrieveBoostData;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                try {
                    JSONObject object = new JSONObject(body);
                    String noOfViews = object.getString("no_of_views_int");
                    String noOfCreditsUsed = object.getString("no_of_credits_used");
                    JSONArray noOfViewsArray = new JSONArray(noOfViews);
                    JSONArray noOfCreditsUsedArray = new JSONArray(noOfCreditsUsed);

                    ArrayList<BoostData> boostArraydata = new ArrayList<>();
                    for(int i= 0; i < noOfCreditsUsedArray.length(); i++){
                        BoostData data = new BoostData(noOfViewsArray.getInt(i)+" views"
                                ,noOfCreditsUsedArray.getInt(i)+" credits"
                                ,noOfViewsArray.getInt(i)
                                ,noOfCreditsUsedArray.getInt(i));


                        boostArraydata.add(data);
                    }

                    if(mLoadListener != null)
                        mLoadListener.onRetrieve(boostArraydata);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
