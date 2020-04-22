package com.contest.competition.utils.activities.profileactivities;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.adapters.rv.FollowFeatureRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingFollowFeaturesData;
import com.contest.competition.classes.interfaces.listenerCaller.FollowRvListenerCaller;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.FollowData;
import com.contest.competition.classes.models.FollowerActivityCheck;
import com.contest.competition.requests.data.followfeature.RetrieveFollowersReq;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.LinearDividerItemDecoration;

import java.util.ArrayList;


public class FollowerActivity extends BaseToolbarActivity {
    private String username;
    private String returnUsername = "";
    private String returnName = "";
    private FollowerActivityCheck mCheck;
    private RecyclerView mRecyclerView;
    private FollowFeatureRv rv;
    private ArrayHolder mArrayHolder;
    private RetrieveFollowersReq mFollowerActivityReq;



    //    change SearchData to FollowData in ArrayHolder and create seperate interface listener to link userProfileId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   ArrayHolder.followerData.clear();
        mArrayHolder = new ArrayHolder();
        mArrayHolder.getFollowerData().clear();


        mCheck = getIntent().getParcelableExtra(KeyStore.PASS_OBJECT);
        username = mCheck.getUsername();
        mRecyclerView = findViewById(R.id.followerUser_rv);
        mFollowerActivityReq = new RetrieveFollowersReq();

        mFollowerActivityReq.setContext(this);
        mFollowerActivityReq.setArrayHolder(mArrayHolder);




        LinearLayoutManager manager = new LinearLayoutManager(this);
        LinearDividerItemDecoration decoration = new LinearDividerItemDecoration(this, R.color.white,1,  R.id.search_profilePic);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(decoration);

        rv = new FollowFeatureRv(FollowFeatureRv.Follower_Activity);
        rv.setArrayHolder(mArrayHolder);




        mFollowerActivityReq.setOnLoadingFollowFeaturesData(new OnLoadingFollowFeaturesData() {
            @Override
            public void onLoad(ArrayList<FollowData> followData) {
                mArrayHolder.setFollowerData(followData);
                rv.setArrayHolder(mArrayHolder);
                rv.notifyDataSetChanged();
            }
        });






        FollowRvListenerCaller followRvListenerCaller = new FollowRvListenerCaller(this, username, new FollowRvListenerCaller.OnSuccessfulFollowRvCaller() {
            @Override
            public void onSuccessful(FollowData data) {
                returnUsername = data.getUsername();
                returnName = data.getName();
                returnResultForSelectUsername();



            }
        });

        followRvListenerCaller.setRetrieveFollowersReq(mFollowerActivityReq);

        rv.setFollowRvListener(followRvListenerCaller);

        setRecyclerView();
        getData();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_follower;
    }

    @Override
    protected String setActivityName() {
        return "Followers";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }

    private void setRecyclerView(){
        rv.setArrayHolder(mArrayHolder);
        mRecyclerView.setAdapter(rv);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(FollowerActivity.this);
        returnResultForSelectUsername();
    }



    @Override
    public void finish() {
        super.finish();
        Animatoo.animateSlideDown(FollowerActivity.this);


    }

    private void getData(){
      mFollowerActivityReq.getData(username,"");
    }


    private void returnResultForSelectUsername(){
        Intent intent = getIntent();
        intent.putExtra(KeyStore.RETURN_RESULT, returnUsername);
        intent.putExtra("return name",returnName);
        Log.e(KeyStore.TAG, "returnResultForSelectUsername: return result = "+returnUsername );
        setResult(RESULT_OK, intent);
        finish();
    }



}
