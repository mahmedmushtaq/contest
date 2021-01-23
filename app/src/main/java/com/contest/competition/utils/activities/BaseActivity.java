package com.contest.competition.utils.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.contest.competition.R;


import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Network;

import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.storage.sharedpreferences.NoOfNotifications;
import com.contest.competition.utils.activities.front.HomeActivity;
import com.contest.competition.utils.activities.front.NotificationActivity;
import com.contest.competition.utils.activities.front.ProfileActivity;
import com.contest.competition.utils.activities.front.SearchActivity;
import com.contest.competition.utils.activities.front.ExploreActivity;
import com.contest.competition.utils.services.NotificationReceiver;
import com.contest.competition.utils.views.Toaster;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public abstract  class BaseActivity extends AppCompatActivity {

    protected AHBottomNavigation mNavigation;
    private NoOfNotifications mNoOfNotifications;
    private LoginSharedPrefer mPrefer;

    protected static final int CONNECTED_WIFI = 1;
    protected static final int DISCONNECTED_WIFI = 0;
    protected int HOME = 1;
    protected int EXPLORE = 0;
    protected int SEARCH = 2;
    protected int NOTIFICATION = 3;
    protected int PROFILE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
        setContentView(R.layout.activity_base);

        LinearLayout baseLl = findViewById(R.id.baseActivity_Ll);
        View view = getLayoutInflater().inflate(setLayout(),null);
        baseLl.addView(view);


       mNavigation = findViewById(R.id.AHbottom_navigation);

        mNoOfNotifications = new NoOfNotifications(this);
        mPrefer = new LoginSharedPrefer(this);

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        Job job = dispatcher.newJobBuilder()
                .setTag("notification-job-schedular")
                .setService(NotificationReceiver.class)
                .setReplaceCurrent(true)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0,1))
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .build();




     //   dispatcher.mustSchedule(job);

        if(BaseActivity.this instanceof NotificationActivity){

            mNoOfNotifications.removeNotifications();
           // dispatcher.cancel("notification-job-schedular");

        }else {
          //  Intent intent = new Intent(this, NotificationsReceive.class);
            //  startService(intent);
            dispatcher.mustSchedule(job);
        }

       setNavigation();


      //  ShortcutBadger.removeCount(getApplicationContext());

        Log.e("package", "onCreate: package name"+getPackageName() );


    }



    @Override
    protected void onResume() {
        super.onResume();
        if(BaseActivity.this instanceof NotificationActivity){
            mNoOfNotifications.removeNotifications();

        }else {
//            Intent intent = new Intent(this, NotificationsReceive.class);
//            startService(intent);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("no_of_notifications"));

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(openFireBroadCast,filter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        unregisterReceiver(openFireBroadCast);

    }

    private void setNavigation(){
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Explore", R.drawable.explore,  R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Home", R.drawable.home,  R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Search", R.drawable.search, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Notifications", R.drawable.alert,  R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile", R.drawable.profile_icon,  R.color.white);

        // Add items
        mNavigation.addItem(item1);
        mNavigation.addItem(item2);
        mNavigation.addItem(item3);
        mNavigation.addItem(item4);
        mNavigation.addItem(item5);

        mNavigation.setAccentColor(Color.parseColor("#f2f4f5"));
        mNavigation.setInactiveColor(Color.parseColor("#ffffff"));



         setCurrentItem();


        // Set background color
     //   mNavigation.setDefaultBackgroundColor(Color.parseColor("#198db3"));


        mNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
        mNavigation.setAccentColor(Color.parseColor("#3E3271"));
        mNavigation.setInactiveColor(Color.parseColor("#bd3f97"));
       // Disable the translation inside the CoordinatorLayout
        mNavigation.setBehaviorTranslationEnabled(false);


        mNavigation.setTranslucentNavigationEnabled(true);

       // Manage titles
        mNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        mNavigation.setSelected(true);

        // Set listeners
        mNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...

             startNewActivities(position);
                return true;
            }
        });



        //set notifications on bottomNavigation
        if(mNoOfNotifications.getNoOfNotifications() > 0)
            setNotifications(mNoOfNotifications.getNoOfNotifications());
    }

//    protected void setCurrentItem(int itemNo){
//        mNavigation.setCurrentItem(itemNo);
//    }

    private void setCurrentItem(){

        if(BaseActivity.this instanceof NotificationActivity){
            mNavigation.setCurrentItem(NOTIFICATION);
        }
        if(BaseActivity.this instanceof HomeActivity){
            mNavigation.setCurrentItem(HOME);
        }
        if(BaseActivity.this instanceof ProfileActivity){
            mNavigation.setCurrentItem(PROFILE);
        }
        if(BaseActivity.this instanceof SearchActivity){
            mNavigation.setCurrentItem(SEARCH);
        } if(BaseActivity.this instanceof ExploreActivity){
            mNavigation.setCurrentItem(EXPLORE);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }




    private void setNotifications(int noOfNotifications){
        if(noOfNotifications > 0) {
            if(BaseActivity.this instanceof NotificationActivity){
                //do something here notification activity
            }else {
                String notifications = "";
                if(noOfNotifications > 100){
                    notifications = "100+";
                }else{
                    notifications = noOfNotifications+"";
                }
                AHNotification notification = new AHNotification.Builder()
                        .setText(notifications)
                        .setBackgroundColor(ContextCompat.getColor(BaseActivity.this,  R.color.lightRed))
                        .setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white))
                        .build();
                mNavigation.setNotification(notification, NOTIFICATION);
            }
        }
    }

    private void startNewActivities(int position) {
        notifyItemSelected(position);

        if(position == HOME){//1
            if(!Network.isAvailable(this)){
                Toaster.setToaster(getBaseContext(),"Network is not available");
            }


            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();

        }else if(position == EXPLORE){//2
            if(!Network.isAvailable(this)){
                Toaster.setToaster(getBaseContext(),"Network is not available");
            }
           startActivity(new Intent(getBaseContext(), ExploreActivity.class));
            finish();

        }

        else if(position == SEARCH){//3
            if(!Network.isAvailable(this)){
                Toaster.setToaster(getBaseContext(),"Network is not available");
            }

            startActivity(new Intent(getBaseContext(), SearchActivity.class));
            finish();

        }else if(position == NOTIFICATION){//4
            if(!Network.isAvailable(this)){
                Toaster.setToaster(getBaseContext(),"Network is not available");
            }



            Intent intent = new Intent(getBaseContext(),NotificationActivity.class);


            startActivity(intent);
            finish();

        }else if(position == PROFILE){//5
            if(!Network.isAvailable(this)){
                Toaster.setToaster(getBaseContext(),"Network is not available");
            }



            Intent intent = new Intent(getBaseContext(),ProfileActivity.class);
            intent.putExtra(KeyStore.PASS_DATA,mPrefer.getUsername());

            startActivity(intent);
            finish();

        }
    }





    private BroadcastReceiver openFireBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             if(intent.getExtras() != null){
                 NetworkInfo info = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
//                 =============== SET OPENFIRE CONNECTION ==============
                 if(info != null && info.getState() == NetworkInfo.State.CONNECTED) {
                     //network connected
                     wifiConnection(CONNECTED_WIFI);


                 }else{
                     //wifi not connected
                     wifiConnection(DISCONNECTED_WIFI);

                 }
             }
        }
    };




    /* ============= LOCAL BROADCAST MANAGER =============== */

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int no = intent.getIntExtra(KeyStore.LOCAL_BROADCAST_RECEIVER,0);
            if(no > 0) {
                Log.e(KeyStore.TAG, "onReceive: no of notifications = "+no );
                setNotifications(no);
            }else {
                Log.e(KeyStore.TAG, "onReceive: no notification received" );
            }
        }
    };


    protected abstract int setLayout();
    protected abstract void notifyItemSelected(int position);

    protected void wifiConnection(int connection){
        if(connection == DISCONNECTED_WIFI){
            Toaster.setToaster(getBaseContext(),"Internet connection is required");
        }
    }


}
