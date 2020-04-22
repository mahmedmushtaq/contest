package com.contest.competition.requests.data;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SearchData;
import com.contest.competition.classes.webfiles.Addresses;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public class SearchProcess {

    private SearchProcessListener mListener;
    private ArrayHolder mArrayHolder = new ArrayHolder();


    public interface SearchProcessListener{
        void onSuccess(ArrayHolder arrayHolder);
        void onFailure();
        void noResultFoundError(String reason);
    }

    public void setListener(SearchProcessListener listener){
        this.mListener = listener;
    }

    public void searchUser(final Context context, String value, String username, String lastId){


        String url =  Addresses.getWebAddress()+ "/utils/data/search.php?value=" + value.trim()+"&username="+username+"&lastId="+lastId;


        OkHttpClient client = new OkHttpClient();


        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String body = response.body().string();
               // Log.e("searchProcess", "onResponse: search result = "+body );
                try {
                    JSONObject object  = new JSONObject(body);
                    String result = object.getString("result");
                    String reason = object.getString("reason");
                    if(Validator.validateWebResult(result)){
                        afterSuccessfulResult(context,body,object);

                    }else {
                        mListener.noResultFoundError(reason);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }













            }
        });



    }

    private void afterSuccessfulResult(Context context,String body,final JSONObject object) {

                try {


                    String usernames = object.getString("usernames");
                    String names = object.getString("names");
                    String profilePics = object.getString("profile_pics");
                    String userPrivacy = object.getString("user_privacy");
                    String ids = object.getString("ids");
                    String previousSearchKeyword = object.getString("previous_search_keyword");

                    JSONArray usernamesArray = new JSONArray(usernames);
                    JSONArray namesArray = new JSONArray(names);
                    JSONArray profilePicsArray = new JSONArray(profilePics);
                    JSONArray userPrivacyArray = new JSONArray(userPrivacy);
                    JSONArray idsArray = new JSONArray(ids);

                    for (int i = 0; i < usernamesArray.length(); i++) {
                        SearchData models = new SearchData(namesArray.getString(i), usernamesArray.getString(i), profilePicsArray.getString(i), userPrivacyArray.getString(i), previousSearchKeyword, idsArray.getInt(i));

                        mArrayHolder.setSearchDataArrayList(models);

                    }

                    if(mArrayHolder.getSearchDataArrayList().size() > 0) {
                        mListener.onSuccess(mArrayHolder);
                    }else{
                        mListener.noResultFoundError("No result found");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }





}



