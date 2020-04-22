package com.contest.competition.utils.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.contest.competition.R;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.requests.data.notificationsreq.RetrieveNotifications;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.storage.sharedpreferences.NoOfNotifications;
import com.contest.competition.utils.activities.front.NotificationActivity;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.contest.competition.utils.services.NotificationsReceive.CHANNEL_ID;
import static com.contest.competition.utils.services.NotificationsReceive.CHANNEL_NAME;

public class NotificationReceiver extends JobService {
    private final String TAG = NotificationReceiver.class.getSimpleName();

    private LoginSharedPrefer prefer ;
    private LocalBroadcastManager mManager;


    private CountDownTimer mTimer;

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {
        Log.e(TAG, "onStartCommand: services running" );


        prefer = new LoginSharedPrefer(getBaseContext());
        mTimer  = new CountDownTimerTask(5000,1000);
        //millisInFuture mean when count down timer ends

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver,filter);


        mTimer.start();
        return true;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }
    @Override
    public void onCreate() {
        super.onCreate();




    }




    @Override
    public void onDestroy() {
        super.onDestroy();
       try{
           unregisterReceiver(networkReceiver);
       }catch (IllegalArgumentException e){

       }


        Log.e(TAG, "onDestroy: destroy service" );
        cancelCountDownTimer();
    }


    private void notificationReceived(String username){
        NotificationsReq.notificationReceived(username);
    }





    private void cancelCountDownTimer(){
        if(mTimer != null)
            mTimer.cancel();

    }

    private void getAllNotifications(){

        RetrieveNotifications retrieveNotifications = new RetrieveNotifications();
        retrieveNotifications.setRetrieveNotificationListener(new RetrieveNotifications.retrieveNotificationListener() {
            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess(JSONObject object) {
                try {
                    afterSuccessfulResult(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        retrieveNotifications.retrieveNotifications(prefer.getUsername(),"no","",0);


    }

    private void afterSuccessfulResult(JSONObject object) throws JSONException{
        String result = object.getString("result");

        String reason = object.getString("reason");
        if (Validator.validateWebResult(result)) {
            int noOfNotifications = object.getInt("no_of_notifications");

            sendNoOfNotification(noOfNotifications);
            if (noOfNotifications > 0) {

                notificationBuilder(noOfNotifications);
            }
        }else mTimer.start();
    }

    private void notificationBuilder(int no){

        NoOfNotifications notifications = new NoOfNotifications(this);
        notifications.setNoOfNotifications(no);

//

        Log.e(TAG, "notificationBuilder: notification builder = ");



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Contest")
                .setContentText(no+" new notification received")
                .setColor(Color.parseColor("#009add"))
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);

        notificationManager.notify(0, mBuilder.build());


        notificationReceived(prefer.getUsername());
        mTimer.start();

    }



    private void sendNoOfNotification(int no){
        //send no of notifications to conversation activity for fast updation process
        Intent intent = new Intent("no_of_notifications");
        intent.putExtra(KeyStore.LOCAL_BROADCAST_RECEIVER,no);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private class CountDownTimerTask extends android.os.CountDownTimer{
        public CountDownTimerTask(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }



        @Override
        public void onFinish() {

            if(prefer.getUsername() != null) {
                getAllNotifications();

            }
        }
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                if(prefer.getUsername() != null) {
                    mTimer.start();
                }else {
                    cancelCountDownTimer();
                }




            }else{
                cancelCountDownTimer();
            }
        }
    };




}
