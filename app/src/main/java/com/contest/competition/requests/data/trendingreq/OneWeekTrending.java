package com.contest.competition.requests.data.trendingreq;

import android.util.Log;

import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.TrendingFiles;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OneWeekTrending {
    public static void checkOneWeekTrending(){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().post(body).url(Addresses.getWebAddress()+ TrendingFiles.oneWeekTrendingChecker).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("oneweektrending", "onResponse: trending = "+response.body().string() );
            }
        });
    }
}
