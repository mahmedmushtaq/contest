package com.contest.competition.utils.activities.front;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;
import com.contest.competition.adapters.rv.HomeRv;
import com.contest.competition.classes.classesforactivity.MainPageDataLoadingClass;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.messages.activities.ConversationActivity;
import com.contest.competition.requests.data.postreq.RetrieveAllPosts;
import com.contest.competition.storage.sharedpreferences.IntroSharedPreference;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;
import com.contest.competition.utils.activities.front.posts.SelectContestActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;

public class HomeActivity2 extends BaseActivity {
    private HomeRv rv;
    private RecyclerView mRecyclerView;
    private LoginSharedPrefer mLoginSharedPrefer;
    private ProgressBar homeRvProgressBar;
    private SwipeRefreshLayout swipe;
    private ArrayHolder mArrayHolder;
    private FloatingActionButton newPostBtn;
    private IntroSharedPreference mIntroSharedPreference;
    private ImageView openConversation;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArrayHolder = new ArrayHolder();
        rv = new HomeRv();
        rv.setArrayHolder(mArrayHolder);

        mLoginSharedPrefer = new LoginSharedPrefer(this);


        mRecyclerView = findViewById(R.id.home_rv);
        homeRvProgressBar = findViewById(R.id.homeRvPb);
        swipe = findViewById(R.id.swipeHome);
        openConversation = findViewById(R.id.home_openConversations);
        swipe.setRefreshing(false);

        mIntroSharedPreference  =new IntroSharedPreference(this);



        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               mArrayHolder.getHomePostdata().clear();
               //again load data


            }
        });

        openConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ConversationActivity.class));
                Animatoo.animateSlideLeft(HomeActivity2.this);
            }
        });



        newPostBtn = findViewById(R.id.fab_new_post);



        LinearLayoutManager manager = new LinearLayoutManager(this);


        //  LinearDividerItemDecoration decoration = new LinearDividerItemDecoration(this,R.color.lightSilver,1,R.id.single_postTotalSeen);//resource id mean the part of layout in which after this decoration show

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setAdapter(rv);



        setIntroAboutApp();


        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewPost();
            }
        });





    }


//    private void retrieveData(){
//        RetrieveAllPosts.retrievePost();
//    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void notifyItemSelected(int position) {

    }

    private void setIntroAboutApp(){
        if(!mIntroSharedPreference.getSelfieSharedPreference()) {
            Typeface typefaceTile = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.otf");
            Typeface typeFacecontent = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");


            new GuideView.Builder(this)
                    .setTitle(getString(R.string.create_a_contest))
                    .setContentText(getString(R.string.click_here_and_create_any_type_of_contest))
                    .setTargetView(newPostBtn)
                    .setContentTypeFace(typeFacecontent)
                    .setTitleTypeFace(typefaceTile)
                    .setDismissType(DismissType.outside) //optional - default dismissible by TargetView
                    .build()
                    .show();
        }
    }


    private void createNewPost() {
        startActivity(new Intent(getBaseContext(), SelectContestActivity.class));

        Animatoo.animateSlideLeft(this);

    }

}
