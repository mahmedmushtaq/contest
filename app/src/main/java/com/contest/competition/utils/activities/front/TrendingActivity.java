package com.contest.competition.utils.activities.front;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.contest.competition.R;
import com.contest.competition.adapters.TrendingRv;
import com.contest.competition.classes.interfaces.HomeRvImplementation;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.requests.data.trendingreq.OneWeekTrending;
import com.contest.competition.requests.data.trendingreq.RetrieveTrendingData;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;

import java.util.ArrayList;

public class TrendingActivity extends BaseActivity {
    private ProgressBar pb;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoginSharedPrefer mPrefer;


    private RecyclerView mRecyclerView;
    private TrendingRv mTrendingRv;
    private HomeRvImplementation mHomeRvImplementation;
    private RetrieveTrendingData mData;
    private ArrayHolder mArrayHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        pb = findViewById(R.id.trending_pb);
        swipeRefreshLayout = findViewById(R.id.trending_swipe);
        mPrefer = new LoginSharedPrefer(this);
        mTrendingRv = new TrendingRv();
        mData = new RetrieveTrendingData();
        mData.setContext(this);
        mArrayHolder = new ArrayHolder();

        mTrendingRv.setArrayHolder(mArrayHolder);

        mHomeRvImplementation = new HomeRvImplementation(this,mPrefer.getUsername());
        mTrendingRv.setRvListener(mHomeRvImplementation);

        mRecyclerView = findViewById(R.id.trending_rv);
        swipeRefreshLayout.setRefreshing(false);
        Toolbar toolbar = findViewById(R.id.trending_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        TextView tv= findViewById(R.id.toolbar_for_all_text);
        tv.setText("Trending Posts");
        tv.setTextColor(getResources().getColor(R.color.white));

        setRecyclerView();


        setHomeRvImplementationListener();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                     swipeRefreshLayout.setRefreshing(false);
                     mArrayHolder.getTrendingData().clear();
                     mData.retrieveTrendingData(mPrefer.getUsername());

                }
        });


        setDataCallBack();
        mData.retrieveTrendingData(mPrefer.getUsername());


        OneWeekTrending.checkOneWeekTrending();



    }

    private void setDataCallBack(){
        mData.setCallBack(new RetrieveTrendingData.RetrieveNotificationCallBack() {
            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess(ArrayList<PostData> trendingData) {
                pb.setVisibility(View.GONE);
                mArrayHolder.setTrendingData(trendingData);
                mTrendingRv.setArrayHolder(mArrayHolder);
              mTrendingRv.notifyDataSetChanged();

            }
        });
    }

    private void setRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.setAdapter(home_rv);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setAdapter(mTrendingRv);
        mRecyclerView.setItemAnimator(null);
    }

    private void setHomeRvImplementationListener(){
           mHomeRvImplementation.setListener(new HomeRvImplementation.SuccessHomeRvImplementationListener() {
               @Override
               public void Successful(int position) {
                   mTrendingRv.notifyDataSetChanged();

               }

               @Override
               public void loadingMore(int position) {

               }

               @Override
               public void remove(int position) {

               }
           });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_trending;
    }

    @Override
    protected void notifyItemSelected(int position) {
        if(mRecyclerView != null && !mArrayHolder.getTrendingData().isEmpty()){
         //  RetrieveTrendingData.setClearArray();
            mTrendingRv.notifyDataSetChanged();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }
}
