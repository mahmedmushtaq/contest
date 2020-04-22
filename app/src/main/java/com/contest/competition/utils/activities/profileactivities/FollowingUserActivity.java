package com.contest.competition.utils.activities.profileactivities;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.adapters.rv.FollowFeatureRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingFollowFeaturesData;
import com.contest.competition.classes.interfaces.listenerCaller.FollowRvListenerCaller;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.FollowData;
import com.contest.competition.requests.data.followfeature.RetrieveFollowingsReq;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.LinearDividerItemDecoration;

import java.util.ArrayList;


public class FollowingUserActivity extends BaseToolbarActivity {
    private String username;
    private RecyclerView mRecyclerView;
    private FollowFeatureRv rv;
    private ArrayHolder mArrayHolder;
    private RetrieveFollowingsReq mReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ArrayHolder.followingData.clear();



        username = getIntent().getStringExtra(KeyStore.PASS_DATA);

        mRecyclerView = findViewById(R.id.followingUser_rv);
        mArrayHolder = new ArrayHolder();
        mReq = new RetrieveFollowingsReq();
        mReq.setContext(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        LinearDividerItemDecoration decoration = new LinearDividerItemDecoration(this,  R.color.white,1,R.id.search_profilePic);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(decoration);

        rv = new FollowFeatureRv(FollowFeatureRv.Following_Activity);
        rv.setArrayHolder(mArrayHolder);



        mReq.setOnLoadingFollowFeaturesData(new OnLoadingFollowFeaturesData() {
            @Override
            public void onLoad(ArrayList<FollowData> followData) {
                mArrayHolder.setFollowingData(followData);
                rv.setArrayHolder(mArrayHolder);
                rv.notifyDataSetChanged();

            }
        });


        FollowRvListenerCaller followRvListenerCaller = new FollowRvListenerCaller(this, username, new FollowRvListenerCaller.OnSuccessfulFollowRvCaller() {
            @Override
            public void onSuccessful(FollowData data) {

            }
        });

        followRvListenerCaller.setRetrieveFollowingsReq(mReq);




        rv.setFollowRvListener(followRvListenerCaller);

        setRecyclerView();

        getData();


    }

    @Override
    protected int setLayout() {
        return R.layout.activity_following_user;
    }

    @Override
    protected String setActivityName() {
        return "Followings";
    }

    @Override
    protected int useWhiteToolbar() {
        return  WHITE_TOOLBAR;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(FollowingUserActivity.this);
    }

    @Override
    public void finish() {
        super.finish();
        Animatoo.animateSlideDown(FollowingUserActivity.this);
    }

    private void setRecyclerView(){

        rv.setArrayHolder(mArrayHolder);
        mRecyclerView.setAdapter(rv);
    }

    private void getData(){
        mReq.getData(username,"");
    }

}
