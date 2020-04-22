package com.contest.competition.utils.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
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
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;

import com.contest.competition.BuildConfig;
import com.contest.competition.R;

import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.requests.data.notificationsreq.RetrieveNotifications;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.storage.sharedpreferences.NoOfNotifications;
import com.contest.competition.utils.activities.front.NotificationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import co.chiragm.sbn.StatusBarNotifier;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by M Ahmed Mushtaq on 6/18/2018.
 */

public class NotificationsReceive extends Service {
    private final String TAG = NotificationsReceive.class.getSimpleName();

    private LoginSharedPrefer prefer ;
    private LocalBroadcastManager mManager;


    private CountDownTimer mTimer;
    public static final String CHANNEL_ID = "CONTEST_NOTIFICATIONS";
    public static final String CHANNEL_NAME= "CONTEST";



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
       // registerReceiver(networkReceiver, filter);

     //   LocalBroadcastManager.getInstance(this).registerReceiver(networkReceiver,filter);
        Log.e(TAG, "onStartCommand: services running" );


        prefer = new LoginSharedPrefer(getBaseContext());
        mTimer  = new CountDownTimerTask(5000,1000);
        //millisInFuture mean when count down timer ends

        mTimer.start();


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver,filter);



    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
        Log.e(TAG, "onDestroy: destroy service" );
        cancelCountDownTimer();
    }


    private void notificationReceived(String username){
        NotificationsReq.notificationReceived(username);
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
        //      Notification.Builder builder = new Notification.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher_round);
//        builder.setContentTitle("Notifications");
//        builder.setContentTitle("New "+no+" received");
        //short form


        NoOfNotifications notifications = new NoOfNotifications(this);
        notifications.setNoOfNotifications(no);

//
//        NotificationCompat notification =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(HighLighter.capitalEachWordFirstLetter(prefer.getName()))
//                        .setContentText(no+" new notifications received")
//                        .setContentIntent(notificationOnClick())
//                        .setDefaults(Notification.DEFAULT_SOUND)
//                        .setAutoCancel(true)
//                        .setChannelId("notification_no")
//                        .build();


//        final StatusBarNotifier statusBarNotifier = new StatusBarNotifier
//                .Builder()
//                .setAutoHide(false) // default true
//                 // default 3000 ms (3 seconds)
//                .build();


//        Notification.Builder builder = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.icon_contest)
//                .setContentTitle(HighLighter.capitalEachWordFirstLetter(prefer.getName()))
//                .setContentText(no+" new notifications received")
//                .setContentIntent(notificationOnClick())
//                .setDefaults(Notification.DEFAULT_SOUND)
//
//                .setAutoCancel(true);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            int random = (int )(Math. random() * 50 + 1);
//            Notification.Builder builder26 = new Notification.Builder(this,"notification_id"+random)
//                    .setSmallIcon(R.drawable.icon_contest)
//                    .setContentTitle(HighLighter.capitalEachWordFirstLetter(prefer.getName()))
//                    .setContentText(no+" new notifications received")
//                    .setContentIntent(notificationOnClick())
//                    .setDefaults(Notification.DEFAULT_SOUND)
//
//                    .setAutoCancel(true);
//        }
        Log.e(TAG, "notificationBuilder: notification builder = ");

       // Notification notification = builder.build();
        // startForeground(11,notification); this notification cannot be clear by clear method of notification bar so therefore prohibit in our this function

       // NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
       // mNotificationManager.notify(KeyStore.NOTIFICATION_ID,builder.build());

     //  ShortcutBadger.applyCount(getApplicationContext(), no); //for 1.1.4+

//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                    CHANNEL_NAME,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("About new notification");
//            mNotificationManager.createNotificationChannel(channel);
//        }
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
//                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
//                .setContentTitle("Contest") // title for notification
//                .setContentText(no+" new notification received")// message for notification
//                .setAutoCancel(true); // clear notification after click
//        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pi);
//        mNotificationManager.notify(0, mBuilder.build());


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

    private PendingIntent notificationOnClick(){
        Intent mainIntent = new Intent(this, NotificationActivity.class);
        PendingIntent intent = PendingIntent.getActivities(this, KeyStore.OPEN_ACTIVITY_BY_NOTIFICATION, new Intent[]{mainIntent},0);


        return intent;
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





}
