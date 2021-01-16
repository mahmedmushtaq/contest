package com.contest.competition.utils.activities.front;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.contest.competition.R;

import com.contest.competition.adapters.rv.NotificationRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingNotification;
import com.contest.competition.classes.interfaces.listenerCaller.NotificationListenerCaller;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.requests.data.notificationsreq.NotificationRetrieveInActivity;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;
import com.contest.competition.utils.views.LinearDividerItemDecoration;

import java.util.ArrayList;

public class NotificationActivity extends BaseActivity {

    private LoginSharedPrefer mPrefer;
    private RecyclerView mRecyclerView;
    private NotificationRv rv;
    private ProgressBar notificationPb;
    private SwipeRefreshLayout mRefreshLayout;
    private int lastEndId = 0;
    private ArrayHolder mArrayHolder ;
    private NotificationRetrieveInActivity mNotificationRetrieveInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();

        mPrefer = new LoginSharedPrefer(this);

        mArrayHolder = new ArrayHolder();

        mArrayHolder.getNotificationData().clear();
        mNotificationRetrieveInActivity = new NotificationRetrieveInActivity();
        mNotificationRetrieveInActivity.setContext(this);




        mRecyclerView = findViewById(R.id.notification_rv);
        notificationPb = findViewById(R.id.notification_Pb);
        notificationPb.setVisibility(View.VISIBLE);


        rv = new NotificationRv();

        mNotificationRetrieveInActivity.setOnLoadingNotification(new OnLoadingNotification() {
            @Override
            public void onLoading(ArrayList<NotificationData> notificationData) {

                notificationPb.setVisibility(View.GONE);
                mRefreshLayout.setRefreshing(false);
                if(notificationData != null) {
                  //  Log.e("loadData", "onLoading: load data "+notificationData.get(0) );
                    mArrayHolder.setNotificationData(notificationData);
                    rv.setArrayHolder(mArrayHolder);
                    rv.notifyDataSetChanged();
                }

            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(this);
        //   DividerItemDecoration decoration = new DividerItemDecoration(this,manager.getOrientation());
        mRecyclerView.setLayoutManager(manager);


        removeNotification();
        NotificationListenerCaller listenerCaller = new NotificationListenerCaller(this, mPrefer.getUsername(), mNotificationRetrieveInActivity, new NotificationListenerCaller.NotificationListenerCallerInterface() {
            @Override
            public void onRemove(NotificationData notificationData) {
                linkToDeletedContest(notificationData);
            }
        });


        rv.setListener(listenerCaller);

        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(this, R.color.lightSilver,1, R.id.notification_profileIv));




        rv.setArrayHolder(mArrayHolder);
        mRecyclerView.setAdapter(rv);


        setRefreshLayout();



        BackgroundWorkAsyncTask asyncTask = new BackgroundWorkAsyncTask();
        asyncTask.execute();


    }






    private void setRefreshLayout() {
        mRefreshLayout = findViewById(R.id.swipeRefreshNotification);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mArrayHolder != null) {
                    mArrayHolder.getNotificationData().clear();
                }
                mNotificationRetrieveInActivity.retrieveNotification(mPrefer.getUsername(),"",0);

            }
        });
    }



//    private void visitProfile(NotificationData data){
//        VisitProfile.with(this).visitUserProfile(data.getUsername());
////        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
////        intent.putExtra(KeyStore.PASS_DATA,data.getUsername());
////
////        startActivity(intent);
//    }

    private void linkToDeletedContest(NotificationData data){
        NotificationsReq.notificationOpened(data.getId(),mPrefer.getUsername());
        mArrayHolder.getNotificationData().remove(data);
        rv.setArrayHolder(mArrayHolder);
        rv.notifyDataSetChanged();
    }

    private void removeNotification(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(KeyStore.NOTIFICATION_ID);

    }

    private void setToolbar(){
        Toolbar toolbar =  findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);
        TextView tv = findViewById(R.id.toolbar_for_all_text);
        tv.setText("Notifications");
    }



    private class BackgroundWorkAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("retrieve", "doInBackground: call background" );
            mNotificationRetrieveInActivity.retrieveNotification(mPrefer.getUsername(),"",0);

            return null;
        }
    }



    @Override
    protected int setLayout() {
        return R.layout.activity_notification;
    }

    @Override
    protected void notifyItemSelected(int position) {
        if(mRecyclerView != null && !mArrayHolder.getNotificationData().isEmpty()){
            mArrayHolder.getNotificationData().clear();
            rv.notifyDataSetChanged();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }




}
