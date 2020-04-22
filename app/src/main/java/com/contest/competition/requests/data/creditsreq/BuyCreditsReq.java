package com.contest.competition.requests.data.creditsreq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CreditsData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.CreditsWebFiles;

/**
 * Created by M Ahmed Mushtaq on 9/30/2018.
 */

public class BuyCreditsReq {
    private static BuyCreditReqListener mListener;
    public interface BuyCreditReqListener{
        void onRetrieve(ArrayList<CreditsData> creditsData);
    }
    public static  void setBuyCreditsListener(BuyCreditReqListener listener){
        mListener = listener;
    }
    public static void loadData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ CreditsWebFiles.buyCreditsDataFile).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              String body = response.body().string();
                try {
                    JSONObject object = new JSONObject(body);
                    String noOfCreditsEarn = object.getString("no_of_credits_earn");
                    String noOfCreditsEarnInTermOfK = object.getString("no_of_credits_earn_in_term_of_k");
                    String price = object.getString("price");
                    String itemId = object.getString("item_id");
                    JSONArray noOfCreditsEarnArray = new JSONArray(noOfCreditsEarn);
                    JSONArray noOfCreditsEarnInTermsOfKArray = new JSONArray(noOfCreditsEarnInTermOfK);
                    JSONArray priceArray = new JSONArray(price);
                    JSONArray itemIdArray = new JSONArray(itemId);
                    ArrayList<CreditsData> creditsData = new ArrayList<>();
                    for(int i = 0; i < priceArray.length(); i++){
                        CreditsData data = new CreditsData(noOfCreditsEarnArray.getInt(i),priceArray.getInt(i)
                        ,"only "+priceArray.getInt(i)+"$"
                        , noOfCreditsEarnInTermsOfKArray.getString(i)
                        ,itemIdArray.getString(i));

                        creditsData.add(data);

                    }

                    if(mListener != null)
                        mListener.onRetrieve(creditsData);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
