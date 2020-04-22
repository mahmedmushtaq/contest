package com.contest.competition.utils.activities.front;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.adapters.ProfileRv;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.SelectCropImageFromGallery;
import com.contest.competition.classes.classesforactivity.ProfileActivityClass;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.ProfileHeaderData;

import com.contest.competition.requests.data.profile.ProfileInfoProcess;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;

import com.contest.competition.R;
import com.contest.competition.utils.views.WrapperLinearLayoutManager;


public class ProfileActivity extends BaseActivity {
    private String passUsername;
    private RecyclerView mRecyclerView;
    private ProfileRv rv;
    private LoginSharedPrefer mSharedPrefer;
  //  private SelectCropImageFromGallery mImageFromGallery;
    private SwipeRefreshLayout mPullRefreshLayout;


    private boolean textShowAlready = false;//mean when no post data or further data is not found then it shown to prevent doubling of recycler view
    private BackgroundTaskLoadNewData postProfileData;

    private ProfileActivityClass mProfileActivityClass;


    private ArrayHolder mArrayHolder;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getIntentValue();

        mSharedPrefer = new LoginSharedPrefer(this);

        mArrayHolder = new ArrayHolder();


     //   setToolbar();


      //  mImageFromGallery = new SelectCropImageFromGallery(ProfileActivity.this);

        mProfileActivityClass = new ProfileActivityClass();

        mProfileActivityClass.setCheck(KeyStore.MY_OWN_PROFILE);
        mProfileActivityClass.setArrayHolder(mArrayHolder);

        mProfileActivityClass.setContext(this);


//        mProfileActivityClass.clearArray();








        mRecyclerView = findViewById(R.id.profile_activityRv);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rv = new ProfileRv();
        rv.setCheck(KeyStore.MY_OWN_PROFILE);

        rv.setArrayHolder(mArrayHolder);
        mRecyclerView.setLayoutManager(new WrapperLinearLayoutManager(this));
     //   mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(this, R.color.lightSilver,1,0));

        mRecyclerView.setItemAnimator(null);
        mRecyclerView.getRecycledViewPool().clear();
        mRecyclerView.setAdapter(rv);
        mProfileActivityClass.setRecyclerView(mRecyclerView);
        mProfileActivityClass.setProfileRv(rv);
        pullRefreshLayout();
        mProfileActivityClass.setPullRefreshLayout(mPullRefreshLayout);
        mProfileActivityClass.setPassUsername(passUsername);
        mProfileActivityClass.setSharedPrefer(mSharedPrefer);
        // learn this below listener you will learn all profile code ..HEHE

        mProfileActivityClass.setRvListener();






        BackgroundTask profileInfoTask = new BackgroundTask();
        profileInfoTask.execute();

        mProfileActivityClass.setHomeRvImplementation();


    }




    private void pullRefreshLayout() {
        mPullRefreshLayout = findViewById(R.id.swipeRefreshProfileLayout);
        mPullRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProfileActivityClass.clearArray();

                getProfileInfo();

            }
        });
        mPullRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));

        mPullRefreshLayout.setRefreshing(false);
    }


    @Override
    protected int setLayout() {
        return R.layout.activity_profile;
    }


    private void getProfileInfo() {
        Log.e(KeyStore.TAG, "getProfileInfo: profile info works" );

        ProfileInfoProcess profileInfoProcess = new ProfileInfoProcess();


        profileInfoProcess.setProfileInfoListener(new ProfileInfoProcess.profileInfoListener() {
            @Override
            public void onSuccess(final ProfileHeaderData data) {
                postProfileData = new ProfileActivity.BackgroundTaskLoadNewData();
                postProfileData.execute();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.setRefreshing(false);
                        mArrayHolder.setProfileHeaderData(data);
                        rv.setArrayHolder(mArrayHolder);
                        rv.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });


        if(passUsername.equalsIgnoreCase(mSharedPrefer.getUsername())){
            Log.e("profilecheck", "getProfileInfo: my  profile" );
            profileInfoProcess.getProfileInfo(passUsername,"");
        }else{
            Intent intent =  new Intent(getBaseContext(),OtherUserProfileActivity.class);
            intent.putExtra(KeyStore.PASS_DATA,passUsername);
            startActivity(intent);
            Animatoo.animateSlideUp(ProfileActivity.this);
          // profileInfoProcess.getProfileInfo(passUsername,mSharedPrefer.getUsername());
        }





    }





//    private void returnPreviousActivityData(){
//        Intent intent = new Intent();
//        intent.putExtra(KeyStore.PASS_DATA, CHECKER);
//        setResult(Activity.RESULT_OK,intent);
//
//    }



//    private void setToolbar(){
//
//
//
////        getSupportActionBar().setTitle(null);
//        ImageView editToolbar = findViewById(R.id.profile_activityEdit);
//        ImageView backIcon = findViewById(R.id.backActivity_pf);
//        if(!passUsername.equals(mSharedPrefer.getUsername())) {
//            editToolbar.setVisibility(View.GONE);
//            backIcon.setVisibility(View.VISIBLE);
//            backIcon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }
//
//
//
//        editToolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(),EditProfileActivity.class));
//
//                Animatoo.animateSlideLeft(ProfileActivity.this);
//            }
//        });
//
//    }



    @Override
    public void finish() {
        //returnPreviousActivityData();
        super.finish();
    }

    private void getIntentValue() {
        passUsername = getIntent().getStringExtra(KeyStore.PASS_DATA);


    }






    @Override
    public void onBackPressed() {
        mProfileActivityClass.dismisPowerMenu();
//        finish();
        super.onBackPressed();
    }



    /*=========== UPLOAD NEW PROFILE ============ */
    /*=========== UPLOAD NEW PROFILE ============ */

//    private void selectImageFromGallery() {
//         SelectCropImageFromGallery.selectImage(ProfileActivity.this,true);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SelectCropImageFromGallery.onActivityResult(requestCode,resultCode,data);
    }




    private class BackgroundTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            getProfileInfo();


            return null;
        }
    }

    private class BackgroundTaskLoadNewData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            mProfileActivityClass.retrieveAllPosts(passUsername,"","");
            return null;
        }
    }


//    ================= BODY CODE =======================





    @Override
    protected void notifyItemSelected(int position) {
        if(mRecyclerView != null && !mArrayHolder.getProfileHeaderData().isEmpty() && !mArrayHolder.getOwnProfileData().isEmpty()){
            mArrayHolder.getOwnProfileData().clear();
            mArrayHolder.getProfileHeaderData().clear();
            rv.setArrayHolder(mArrayHolder);
            rv.notifyDataSetChanged();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }









}
