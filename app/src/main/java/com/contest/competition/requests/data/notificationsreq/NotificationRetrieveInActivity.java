package com.contest.competition.requests.data.notificationsreq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingNotification;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.utils.views.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationRetrieveInActivity {
    private Context mContext;
    private ArrayHolder mArrayHolder = new ArrayHolder();
    private OnLoadingNotification mOnLoadingNotification;


    public void setOnLoadingNotification(OnLoadingNotification onLoadingNotification) {
        mOnLoadingNotification = onLoadingNotification;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void retrieveNotification(String loginUsername,final String loadMore, int loadMoreLastId){
        Log.e("retrieveNotifInAc", "onSuccess: call" );
        RetrieveNotifications retrieveNotifications = new RetrieveNotifications();
        retrieveNotifications.setRetrieveNotificationListener(new RetrieveNotifications.retrieveNotificationListener() {
            @Override
            public void onFail() {
                Log.e("retrieveNotifInAc", "onSuccess: fail" );

            }

            @Override
            public void onSuccess(final JSONObject object) {

                        try {
                            Log.e("retrieveNotifInAc", "onSuccess: success" );

                            afterSuccessfulResult(object,loadMore);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


        });


        retrieveNotifications.retrieveNotifications(loginUsername,"",loadMore,loadMoreLastId);

        //Log.e(KeyStore.TAG, "retrieveNotification: "+User.getUsername() );

    }



    private void afterSuccessfulResult(JSONObject object,String loadMore) throws JSONException{

        String result = object.getString("result");
        final String reason = object.getString("reason");
        if(Validator.validateWebResult(result)){
            setNotificationData(object);
        }else{
            if(!loadMore.contains("yes")) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mOnLoadingNotification != null)
                            mOnLoadingNotification.onLoading(null);
                        Toaster.setToaster(mContext, reason);
                    }
                });

            }



        }

    }

//    private void notificationSeen(){
//        NotificationsReq.notificationReceived(mPrefer.getUsername());
//    }

    private void setNotificationData(JSONObject object) throws  JSONException{

        String usernames = object.getString("usernames");
        String names = object.getString("names");
        String profilePics = object.getString("profile_pics");
        String notification_messages = object.getString("notification_messages");
        String opened = object.getString("opened");
        String viewed = object.getString("received");
        String id = object.getString("ids");
        String links = object.getString("links");
        String timeMessage = object.getString("date_times");

        JSONArray usernamesArray = new JSONArray(usernames);
        JSONArray namesArray = new JSONArray(names);
        JSONArray profilePicsArray = new JSONArray(profilePics);
        JSONArray messagesArray = new JSONArray(notification_messages);
        JSONArray openedArray = new JSONArray(opened);
        JSONArray viewedArray = new JSONArray(viewed);
        JSONArray idArray = new JSONArray(id);
        JSONArray linksArray = new JSONArray(links);
        JSONArray timeMessagesArray = new JSONArray(timeMessage);


        for(int i = 0 ; i < usernamesArray.length() ; i++){
            NotificationData data = new NotificationData(namesArray.getString(i),usernamesArray.getString(i),messagesArray.getString(i),profilePicsArray.getString(i),linksArray.getString(i),openedArray.getString(i),viewedArray.getString(i),timeMessagesArray.getString(i),idArray.getInt(i));
            mArrayHolder.setNotificationData(data);
        }




        if(mOnLoadingNotification != null){
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mOnLoadingNotification.onLoading(mArrayHolder.getNotificationData());
                }
            });
        }



    }
}
