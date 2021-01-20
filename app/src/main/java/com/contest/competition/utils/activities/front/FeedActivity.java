package com.contest.competition.utils.activities.front;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.adapters.RetrieveFeedRv;
import com.contest.competition.classes.classesforactivity.RetrieveFeed;
import com.contest.competition.classes.models.ArrayHolder;

import com.contest.competition.messages.activities.ConversationActivity;
import com.contest.competition.storage.sharedpreferences.IntroSharedPreference;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;
import com.contest.competition.utils.activities.front.posts.SelectContestActivity;
import com.contest.competition.utils.views.Menu;


import com.contest.competition.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;


public class FeedActivity extends BaseActivity {

    private EditText searchEt;
    private Toolbar searchActivityToolbar;
    private RecyclerView mRecyclerView;
    private RetrieveFeedRv rv;
    private LoginSharedPrefer mPrefer;
    private ProgressBar homeRvPb;
    private SwipeRefreshLayout swipe;
    private BackgroundAsync mBackgroundAsync;
    private ArrayHolder mArrayHolder;

    // private CountDownTimer mTimer;
    //  private boolean countDownTimerIsRunning = false;
    private FloatingActionButton newPostBtn;




    private Handler mHandler ;


//    private CountDownTimer mTimer;

    private RetrieveFeed mRetrieveFeed;
    private IntroSharedPreference mIntroSharedPreference;
    private ImageView openConversation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArrayHolder = new ArrayHolder();
        mRetrieveFeed = new RetrieveFeed();
        mRetrieveFeed.setArrayHolder(mArrayHolder);
        mRetrieveFeed.clearArray();


        rv = new RetrieveFeedRv();
        rv.setArrayHolder(mArrayHolder);

        mPrefer = new LoginSharedPrefer(this);


        mRecyclerView = findViewById(R.id.home_rv);
        homeRvPb = findViewById(R.id.homeRvPb);
        swipe = findViewById(R.id.swipeHome);
        // openConversation = findViewById(R.id.home_openConversations);
        swipe.setRefreshing(false);


        mRetrieveFeed.setContext(this);
        mRetrieveFeed.setFeedRv(rv);
        mRetrieveFeed.setLoginSharedPrefer(mPrefer);
        mRetrieveFeed.setRecyclerView(mRecyclerView);
        mRetrieveFeed.setSwipe(swipe);
        mRetrieveFeed.setPb(homeRvPb);
        mRetrieveFeed.rvListener();

        mIntroSharedPreference  =new IntroSharedPreference(this);



        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRetrieveFeed.onSwipeLayout();

            }
        });



//        openConversation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(),ConversationActivity.class));
//                Animatoo.animateSlideLeft(HomeActivity.this);
//            }
//        });


        newPostBtn = findViewById(R.id.fab_new_post);



        LinearLayoutManager manager = new LinearLayoutManager(this);


        //  LinearDividerItemDecoration decoration = new LinearDividerItemDecoration(this,R.color.lightSilver,1,R.id.single_postTotalSeen);//resource id mean the part of layout in which after this decoration show








        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setAdapter(rv);

        int windowVisibility = mRecyclerView.getWindowVisibility();









        // setSearchProcess();
        setIntroAboutApp();


        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroSharedPreference.setSelfieSharedPreference(true);
                createNewPost();
            }
        });



        mBackgroundAsync = new BackgroundAsync();

        mBackgroundAsync.execute();






        //latest data retrieving system

        //mTimer = new MyCountDownTimer(3000,1000);
        //  Here millisecInFuture is the time you set in millisecond
        // when you want CountDownTimer to stop and countDownInterval is the interval time in millisecond
        // you set after which number will increment in CountDownTimer.

        //    mTimer.start();







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

        Animatoo.animateSlideLeft(FeedActivity.this);

    }


    private void rvListener(){
        mRetrieveFeed.rvListener();

    }





//    private void loadNewData() {
//
//        mHomeActivityClass.loadNewData();
//
//
//
//    }

    private void startConversationActivity(){
        startActivity(new Intent(getBaseContext(), ConversationActivity.class));

    }


    private void setSearchProcess(){
//        searchEt.setCursorVisible(false);
//        searchActivityToolbar.setFocusableInTouchMode(true);
//        searchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    startActivity(new Intent(getBaseContext(),SearchActivity.class));
//                }
//            }
//        });




//        searchEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(HomeActivity.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                startActivity(new Intent(getBaseContext(),SearchActivity.class));
//            }
//        });

    }
















    private class BackgroundAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            //retrieveAllPosts("","","","","","");

            mRetrieveFeed.retrieveAllPosts("","","","","","");

            return null;
        }
    }











//    private class MyCountDownTimer extends CountDownTimer{
//
//
//        /**
//         * @param millisInFuture    The number of millis in the future from the call
//         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//         *                          is called.
//         * @param countDownInterval The interval along the way to receive
//         *                          {@link #onTick(long)} callbacks.
//         */
//        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//                 countDownTimerIsRunning = true;
//        }
//
//        @Override
//        public void onFinish() {
//            countDownTimerIsRunning = false;
//             //retrieve here new data
//
//           loadNewData();
//           start();
//        }
//    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }


    @Override
    protected void onResume() {
        super.onResume();

        //    mTimer.cancel();
        //  mTimer.start();




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Menu.hideMenu();
    }


    @Override
    protected void onPause() {

        // mTimer.cancel();
        super.onPause();

    }

    @Override
    protected void onStop() {
        //  mTimer.cancel();
        super.onStop();

    }

    @Override
    protected void notifyItemSelected(int position) {
        if(mRecyclerView != null && !mArrayHolder.getHomePostdata().isEmpty()){
            mArrayHolder.getHomePostdata().clear();
            rv.notifyDataSetChanged();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }
}
