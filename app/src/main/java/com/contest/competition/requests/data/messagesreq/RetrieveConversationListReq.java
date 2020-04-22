package com.contest.competition.requests.data.messagesreq;

import android.util.Log;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.SimpleDateTimeClass;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.messagesModels.ConversationData;
import com.contest.competition.classes.models.messagesModels.MessagesArray;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.messageswebfiles.MessageFiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RetrieveConversationListReq {

    private retrieveConversationListener mRetrieveConversationListener;
    private ArrayList<ConversationData> mConversationData = new ArrayList<>();



    public interface retrieveConversationListener{
        void onRetrieve(ArrayList<ConversationData> conversationData);
    }

    public void setRetrieveConversationListener(retrieveConversationListener retrieveConversationListener){
        this.mRetrieveConversationListener = retrieveConversationListener;
    }

    public  void retrieveList(final String loginUsername){
        mConversationData.clear();

        OkHttpClient client = new OkHttpClient();
        Log.e("url", "sendMessageReq: message url = "+Addresses.getWebAddress()+ MessageFiles.retrieveConversationsList );
        RequestBody body = new FormBody.Builder()
                .add("login_username",loginUsername)
                .add("application_secret_key", KeyStore.APPLICATION_SECRET_KEY)
                .build();
        Request request = new Request.Builder().post(body)
                .url(Addresses.getWebAddress()+ MessageFiles.retrieveConversationsList)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                      String body = response.body().string();
                     // Log.e("conversationList", "onResponse: convesations List are "+body);
                try {
                    JSONObject object = new JSONObject(body);
                    String result = object.getString("result");
                    if(Validator.validateWebResult(result)){

                        tackleData(object);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private  void tackleData(JSONObject object) throws JSONException{
        String userProfiles = object.getString("user_profiles");
        String userNames = object.getString("user_names");
        String dateTime = object.getString("date_time");
        String lastMessagesBody = object.getString("last_messages_body");
        String userSubscriptions = object.getString("user_send_to_subscriptions");
        String ids = object.getString("ids");

        JSONArray userProfilesArray = new JSONArray(userProfiles);
        JSONArray userNamesArray = new JSONArray(userNames);
        JSONArray dateTimeArray = new JSONArray(dateTime);
        JSONArray lastMessagesBodyArray = new JSONArray(lastMessagesBody);
        JSONArray userSubscriptionsArray = new JSONArray(userSubscriptions);
        JSONArray idsArray = new JSONArray(ids);


      //  JSONArray userProfilesArray = new JSONArray(userProfiles);




        for(int i= 0; i < userNamesArray.length(); i++){
            ConversationData conversationData = new ConversationData();
            conversationData.setToSubscription(userSubscriptionsArray.getString(i));
            conversationData.setName(userNamesArray.getString(i));
            conversationData.setMessagesDateTime(dateTimeArray.getString(i));
            conversationData.setProfilePicPath(userProfilesArray.getString(i));
            conversationData.setConversationId(idsArray.getInt(i));
            lastMessageFurtherInterpret(conversationData,lastMessagesBodyArray.getString(i));
        }


        if(mRetrieveConversationListener != null){
            mRetrieveConversationListener.onRetrieve(mConversationData);
        }


    }

    private  void lastMessageFurtherInterpret(ConversationData data,String value){

        try {
            JSONObject object = new JSONObject(value);
            String toUsername = object.getString("to_username");
            String secretKey = object.getString("secret_key");
            String message_body = object.getString("message_body");
            String server_date_time = object.getString("date_time");
            data.setToUsername(toUsername);
            data.setLastMessageSecretKey(secretKey);
            data.setLastMessage(message_body);



            mConversationData.add(data);

            SimpleDateTimeClass.dateTimeComparison(server_date_time,SimpleDateTimeClass.getSimpleTime());





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
