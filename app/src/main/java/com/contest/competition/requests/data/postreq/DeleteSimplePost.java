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
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.SimplePostFiles;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 9/10/2018.
 */

public class DeleteSimplePost {
    private static  DeleteSimplePostInterface deleteSimplePostInterface;
    public interface DeleteSimplePostInterface{
        void onSuccessful();
        void onFail();
    }

    public static void setDeleteInterface(DeleteSimplePostInterface deleteInterface){
        deleteSimplePostInterface = deleteInterface;
    }

    public static void delete(final Context context, String postId,String loginUsername){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("post_id",postId).add("login_username",loginUsername).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ SimplePostFiles.deletePost).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 final String body = response.body().string();
              //  Log.e("delete simple post", "onResponse: body = "+body );
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                if(deleteSimplePostInterface != null)
                                    deleteSimplePostInterface.onSuccessful();
                            }else{
                                Toaster.setToaster(context,reason);
                                if(deleteSimplePostInterface != null)
                                    deleteSimplePostInterface.onFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
