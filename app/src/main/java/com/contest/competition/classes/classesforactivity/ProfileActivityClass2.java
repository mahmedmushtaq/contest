package com.contest.competition.classes.classesforactivity;


import androidx.appcompat.widget.Toolbar;

public class ProfileActivityClass2 {
  /*  private String passUsername;
    private RecyclerView mRecyclerView;
    private ProfileRv rv;
    private LoginSharedPrefer mSharedPrefer;

    private CircularImageView mProfilePic;
    private ProgressBar imageUploadedProgressBar;
    private CropImageFromGallery mImageFromGallery;
    private FancyButton mFollowBtn, mFollowedBtn, mWaitFollowRequestBtn;
    private SwipeRefreshLayout mPullRefreshLayout;

    private ArrayList<Integer> containOnlyProfileContestId = new ArrayList<>();
    private ArrayList<Integer> containOnlyProfileSimplePostId = new ArrayList<>();
    private String checkPresentAlreadySinglePostId = "";
    private String checkPresentAlreadyContestId = "";
    private boolean textShowAlready = false;//mean when no post data or further data is not found then it shown to prevent doubling of recycler view
    private ProfileActivity.BackgroundTaskLoadNewData postProfileData;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getIntentValue();

        mSharedPrefer = new LoginSharedPrefer(this);

        setToolbar();


        mImageFromGallery = new CropImageFromGallery(ProfileActivity.this);





        ArrayHolder.profileHeaderData.clear();
        ArrayHolder.profileBodyData.clear();
        containOnlyProfileContestId.clear();
        containOnlyProfileSimplePostId.clear();
        checkPresentAlreadyContestId  = "";
        checkPresentAlreadySinglePostId = "";








        mRecyclerView = findViewById(R.id.profile_activityRv);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rv = new ProfileRv();

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(this, R.color.lightSilver,1,0));

        // learn this below listener you will learn all profile code ..HEHE

        rv.addProfileListener(new ProfileRvListener() {
            @Override
            public void updateProfile(CircularImageView imageView, ProgressBar progressBar, ProfileHeaderData data) {
                mProfilePic = imageView;
                imageUploadedProgressBar = progressBar;//profile pic progress bar
                dismisPowerMenu();

                selectImageFromGallery();
                afterCroppedImageSelection();
            }

            @Override
            public void viewProfile(CircularImageView imageView, ProfileHeaderData data) {

                mProfilePic = imageView;
                dismisPowerMenu();
                popUpImage(data);
            }

            @Override
            public void viewFollowers() {
                Intent intent = new Intent(getBaseContext(),FollowerActivity.class);
                FollowerActivityCheck check = new FollowerActivityCheck("only to see followers",passUsername);
                intent.putExtra(KeyStore.PASS_OBJECT,check);
                startActivity(intent);

                Animatoo.animateSlideUp(ProfileActivity.this);
            }

            @Override
            public void viewFollowingsUsers() {
                Intent intent = new Intent(getBaseContext(),FollowingUserActivity.class);
                intent.putExtra(KeyStore.PASS_DATA,passUsername);
                startActivity(intent);

                Animatoo.animateSlideUp(ProfileActivity.this);
            }

            @Override
            public void startFollowing(FancyButton followBtn, FancyButton cancelFollowingBtn,FancyButton waitBtn) {
                mFollowBtn = followBtn;
                mFollowedBtn = cancelFollowingBtn;//because when user click onAccept btn then cancel dialog show there is assign equal to accept btn
                mWaitFollowRequestBtn = waitBtn;
                setStartFollowing();

            }

            @Override
            public void cancelFollowing(FancyButton cancelFollowBtn, FancyButton followBtn,FancyButton waitBtn) {
                mFollowBtn = followBtn;
                mFollowedBtn = cancelFollowBtn;
                mWaitFollowRequestBtn = waitBtn;
                userCancel();
            }

            @Override
            public void cancelFollowRequest(FancyButton cancelFollowBtn, FancyButton followBtn, FancyButton waitBtn) {
                mFollowBtn = followBtn;
                mFollowedBtn = cancelFollowBtn;
                mWaitFollowRequestBtn = waitBtn;
                cancelFollowReq();
            }

            @Override
            public void onClickCredits() {
                //start advertisement activity
                if(passUsername.equals(mSharedPrefer.getUsername())) {
                    startActivity(new Intent(getBaseContext(), AdvertisementMain.class));

                    Animatoo.animateSlideUp(ProfileActivity.this);
                }


            }

            @Override
            public void onClickBoostFollowers(ProfileHeaderData data) {
                Toaster.setToaster(getBaseContext(),"Boost this user followers = "+data.getUsername());
            }
        });

        HomeRvImplementation implementation = new HomeRvImplementation(ProfileActivity.this,mSharedPrefer.getUsername());
        implementation.setListener(new HomeRvImplementation.SuccessHomeRvImplementationListener() {
            @Override
            public void Successful(int position) {
                rv.notifyItemChanged(position);
            }

            @Override
            public void loadingMore(int position) {
                PostData data = ArrayHolder.profileBodyData.get(ArrayHolder.profileBodyData.size()-1);
                if(!(data instanceof SimpleTvData))
                    setLoadMore();

            }



            @Override
            public void remove(int position) {
                Log.e("profile", "remove: this works"+position );
                ArrayHolder.profileBodyData.remove(position);
                rv.notifyItemRemoved(position);
                rv.notifyItemRangeChanged(position,ArrayHolder.profileBodyData.size());








            }
        });
        rv.setHomeRvListener(implementation);



        //

        pullRefreshLayout();
        mRecyclerView.setAdapter(rv);

        ProfileActivity.BackgroundTask profileInfoTask = new ProfileActivity.BackgroundTask();
        profileInfoTask.execute();


    }




    private void pullRefreshLayout(){
        mPullRefreshLayout = findViewById(R.id.swipeRefreshProfileLayout);
        mPullRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayHolder.profileHeaderData.clear();
                ArrayHolder.profileBodyData.clear();
                containOnlyProfileContestId.clear();
                containOnlyProfileSimplePostId.clear();
                checkPresentAlreadyContestId = "";
                checkPresentAlreadySinglePostId = "";


                getProfileInfo();

            }
        });
        mPullRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));

        mPullRefreshLayout.setRefreshing(false);
    }

    private void cancelFollowReq(){
        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                cancelFollow();
            }
        });
        Dialoger.setConfirmDialog(ProfileActivity.this,"Cancel Follow Request","Do you want to cancel follow request");
    }

    private void cancelFollow() {
        mWaitFollowRequestBtn.setVisibility(View.GONE);
        CancelFollowing cancelFollowing = new CancelFollowing();
        cancelFollowing.setCancelFollowingListener(new CancelFollowing.cancelFollowingListener() {
            @Override
            public void onSuccess(final String body) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Dialoger.dismissDialog();
                        Log.e(KeyStore.TAG, "run: "+body );
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            if(Validator.validateWebResult(result)){

                                mFollowBtn.setVisibility(View.VISIBLE);
                                mFollowedBtn.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onFail() {

            }
        });

        cancelFollowing.cancelFollowingReq(passUsername, mSharedPrefer.getUsername());
    }

    private void userCancel(){
        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                dialog.dismiss();
                setCancelFollowing();
            }
        });
        Dialoger.setConfirmDialog(ProfileActivity.this,passUsername,"Do you want to unFollow ?");
    }

    private void setStartFollowing(){
        mFollowBtn.setVisibility(View.GONE);
        StartFollowing startFollowing = new StartFollowing();
        startFollowing.setStartFollowingProcessListener(new StartFollowing.addFollowListener() {
            @Override
            public void onSuccess(final JSONObject object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                // mFollowedBtn.setVisibility(View.VISIBLE);
                                mFollowedBtn.setVisibility(View.VISIBLE);

                            }else if(result.contains("wait")){
                                mWaitFollowRequestBtn.setVisibility(View.VISIBLE);
                                //getProfileInfo();
                            }else{
                                Toaster.setToaster(getBaseContext(),reason);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onFailure() {


            }
        });


        startFollowing.startFollowing(passUsername,mSharedPrefer.getUsername(),"check");

    }

    private void setCancelFollowing(){
        CancelFollowing cancelFollowing = new CancelFollowing();
        cancelFollowing.setCancelFollowingListener(new CancelFollowing.cancelFollowingListener() {
            @Override
            public void onSuccess(final String body) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e(KeyStore.TAG, "cancel following : "+body);
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            if(Validator.validateWebResult(result)){
                                mWaitFollowRequestBtn.setVisibility(View.GONE);
                                mFollowBtn.setVisibility(View.VISIBLE);
                                mFollowedBtn.setVisibility(View.GONE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onFail() {

            }
        });

        cancelFollowing.cancelFollowing(mSharedPrefer.getUsername(),passUsername);


    }

    @Override
    protected int setLayout() {
        return R.layout.activity_profile;
    }





    private void popUpImage(ProfileHeaderData data) {
        Intent intent = new Intent(this,ProfileViewActivity.class);
        intent.putExtra(KeyStore.PASS_DATA,data.getProfilePic());
        startActivity(intent);

    }


//    private void returnPreviousActivityData(){
//        Intent intent = new Intent();
//        intent.putExtra(KeyStore.PASS_DATA, CHECKER);
//        setResult(Activity.RESULT_OK,intent);
//
//    }



    private void setToolbar(){
        Toolbar toolbar =  findViewById(R.id.profile_activityToolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle(null);
        ImageView editToolbar = findViewById(R.id.profile_activityEdit);
        if(!passUsername.equals(mSharedPrefer.getUsername())) {
            editToolbar.setVisibility(View.GONE);
            toolbar.setNavigationIcon(R.drawable.back_button);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }



        editToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),EditProfileActivity.class));

                Animatoo.animateSlideLeft(ProfileActivity.this);
            }
        });

    }



    @Override
    public void finish() {
        //returnPreviousActivityData();
        super.finish();
    }

    private void getIntentValue() {
        passUsername = getIntent().getStringExtra(KeyStore.PASS_DATA);


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
                        ArrayHolder.profileHeaderData.add(data);
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
            profileInfoProcess.getProfileInfo(passUsername,"");
        }else{
            profileInfoProcess.getProfileInfo(passUsername,mSharedPrefer.getUsername());
        }


    }



    private void dismisPowerMenu(){
        Menu.hideMenu();
    }

    @Override
    public void onBackPressed() {
        dismisPowerMenu();
        finish();
        super.onBackPressed();
    }



    /*=========== UPLOAD NEW PROFILE ============ */
    /*=========== UPLOAD NEW PROFILE ============ */

   /* private void selectImageFromGallery() {
        SelectCropImageFromGallery.selectImage(ProfileActivity.this,true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SelectCropImageFromGallery.onActivityResult(requestCode,resultCode,data);
    }


    private void afterCroppedImageSelection(){
        SelectCropImageFromGallery.setFileSelected(new SelectCropImageFromGallery.fileSelected() {
            @Override
            public void onSelectedForPost(Bitmap selectedImage, File selectedImageFile) {
                imageSelected(selectedImageFile);
            }
        });
    }



    private void imageSelected(File file) {

        imageUploadedProgressBar.setVisibility(View.VISIBLE);

        UploadNewProfile.setUploadListener(new UploadNewProfile.uploadProfileListener() {
            @Override
            public void onSuccess(String body) {

                try {
                    imageUploadedProgressBar.setVisibility(View.GONE);

                    JSONObject object = new JSONObject(body);
                    String result = object.getString("result");
                    String reason = object.getString("reason");
                    if(Validator.validateWebResult(result)){

                        String profilePic = object.getString("profile_pic");
                        Glide.with(getBaseContext()).load(Addresses.getWebAddress()+profilePic).into(mProfilePic);
                        mSharedPrefer.setProfilePath(profilePic);
                        //  CHECKER = "yes";


                    }else{
                        Toaster.setToaster(getBaseContext(),reason);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {

            }
        });

        UploadNewProfile.uploadProfile(file,mSharedPrefer.getUsername(),ProfileActivity.this);


    }

    private class BackgroundTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getProfileInfo();


            return null;
        }
    }

    private class BackgroundTaskLoadNewData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            retrieveAllPosts(passUsername,"","");
            return null;
        }
    }


//    ================= BODY CODE =======================






    private void retrieveAllPosts(String profileUsername,String lastPostId,String lastContestId){
        RetrieveAllPosts.retrievePost(mSharedPrefer.getUsername(),profileUsername,lastPostId,lastContestId,"","","");
        RetrieveAllPosts.setDataListener(new RetrieveAllPosts.RetrieveAllPostsDataListener() {
            @Override
            public void onRetrieveSinglePostData(PostData data, int postId) {
                if(!checkPresentAlreadySinglePostId.contains(postId+"")) {
                    checkPresentAlreadySinglePostId += ""+postId;
                    ArrayHolder.profileBodyData.add(data);
                }

            }

            @Override
            public void updateView() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void hidePb() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onRetrieveOnlyIds(ArrayList<Integer> contestIds, ArrayList<Integer> postIds) {
                containOnlyProfileSimplePostId.addAll(postIds);
                containOnlyProfileContestId.addAll(contestIds);
            }



            @Override
            public void onRetrieveContestData(PostData data, int postId, int contestIds) {
                if(!checkPresentAlreadyContestId.contains(contestIds+"")) {
                    checkPresentAlreadyContestId += ""+contestIds+"";
                    ArrayHolder.profileBodyData.add(data);
                }



            }

            @Override
            public void onEnd(PostData data) {
                ArrayHolder.profileBodyData.add(data);

            }

            @Override
            public void boostedId(ArrayList<Integer> boostedIds) {

            }

            @Override
            public void onFail() {


            }
        });



    }


    private void setLoadMore() {

        if(!containOnlyProfileSimplePostId.isEmpty() && !containOnlyProfileContestId.isEmpty()) {

            int lastPostId = containOnlyProfileSimplePostId.get(containOnlyProfileSimplePostId.size() - 1);
            int lastContestId = containOnlyProfileContestId.get(containOnlyProfileContestId.size()-1);
            retrieveAllPosts(passUsername,lastPostId+"",lastContestId+"");

        }else if(!containOnlyProfileSimplePostId.isEmpty() && containOnlyProfileContestId.isEmpty()){

            int lastPostId = containOnlyProfileSimplePostId.get(containOnlyProfileSimplePostId.size()-1);
            retrieveAllPosts(passUsername,lastPostId+"","");

        }else if(containOnlyProfileSimplePostId.isEmpty() && !containOnlyProfileContestId.isEmpty()){

            int lastContestId = containOnlyProfileContestId.get(containOnlyProfileContestId.size()-1);
            retrieveAllPosts(passUsername,"",lastContestId+"");

        }

    }


    @Override
    protected void notifyItemSelected(int position) {
        if(mRecyclerView != null && !ArrayHolder.profileHeaderData.isEmpty() && !ArrayHolder.profileBodyData.isEmpty()){
            ArrayHolder.profileBodyData.clear();
            ArrayHolder.profileHeaderData.clear();
            rv.notifyDataSetChanged();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }

*/


}
