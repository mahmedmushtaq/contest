package com.contest.competition.classes.classesforactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.contest.competition.adapters.ProfileRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.SelectCropImageFromGallery;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.interfaces.HomeRvImplementation;
import com.contest.competition.classes.interfaces.ProfileRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.FollowerActivityCheck;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.ProfileHeaderData;
import com.contest.competition.classes.models.SimpleTvData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.messages.activities.ConversationActivity;
import com.contest.competition.messages.activities.InboxActivity;
import com.contest.competition.requests.data.followfeature.CancelFollowing;
import com.contest.competition.requests.data.followfeature.StartFollowing;
import com.contest.competition.requests.data.postreq.RetrieveAllPosts;
import com.contest.competition.requests.data.profile.UploadNewProfile;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.advertisements.AdvertisementMain;
import com.contest.competition.utils.activities.profileactivities.EditProfileActivity;
import com.contest.competition.utils.activities.profileactivities.FollowerActivity;
import com.contest.competition.utils.activities.profileactivities.FollowingUserActivity;
import com.contest.competition.utils.activities.profileactivities.ProfileViewActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.FollowBtnStyle;
import com.contest.competition.utils.views.Menu;
import com.contest.competition.utils.views.Toaster;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ProfileActivityClass {
    private String passUsername;
    private RecyclerView mRecyclerView;
    private ProfileRv rv;
    private LoginSharedPrefer mSharedPrefer;

    private CircularImageView mProfilePic;
    private ProgressBar imageUploadedProgressBar;
    //private CropImageFromGallery mImageFromGallery;
  //  private FancyButton mFollowBtn, mFollowedBtn, mWaitFollowRequestBtn;
    private SwipeRefreshLayout mPullRefreshLayout;

    private ArrayList<Integer> containOnlyProfileContestId = new ArrayList<>();
    private ArrayList<Integer> containOnlyProfileSimplePostId = new ArrayList<>();
    private String checkPresentAlreadySinglePostId = "";
    private String checkPresentAlreadyContestId = "";
    private ArrayHolder mArrayHolder;

    private boolean textShowAlready = false;//mean when no post data or further data is not found then it shown to prevent doubling of recycler view


    private Context mContext;
    private Button mFollowBtn;
    private int mCheck;

    public void setCheck(int check){
        mCheck = check;
    }



    public void setContext(Context context){
        mContext = context;
    }




    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }


    public void setPassUsername(String passUsername){
        this.passUsername = passUsername;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
    }

    public void setProfileRv(ProfileRv rv){
        this.rv = rv;
    }

    public void setSharedPrefer(LoginSharedPrefer sharedPrefer){
        this.mSharedPrefer = sharedPrefer;
    }

    public void setPullRefreshLayout(SwipeRefreshLayout pullRefreshLayout){
        mPullRefreshLayout = pullRefreshLayout;
    }


    public  void clearArray(){
        if(mArrayHolder != null){
            mArrayHolder.getProfileHeaderData().clear();
        }


        if(mCheck == KeyStore.MY_OWN_PROFILE)
        mArrayHolder.getOwnProfileData().clear();
        else mArrayHolder.getOtherUserProfileData().clear();
        containOnlyProfileContestId.clear();
        containOnlyProfileSimplePostId.clear();
        checkPresentAlreadyContestId  = "";
        checkPresentAlreadySinglePostId = "";

    }

//    private void returnPreviousActivityData(){
//        Intent intent = new Intent();
//        intent.putExtra(KeyStore.PASS_DATA, CHECKER);
//        ((Activity)mContext).setResult(Activity.RESULT_OK,intent);
//
//    }


    public void setHomeRvImplementation(){
        HomeRvImplementation implementation = new HomeRvImplementation(mContext,mSharedPrefer.getUsername());
        implementation.setListener(new HomeRvImplementation.SuccessHomeRvImplementationListener() {
            @Override
            public void Successful(int position) {
                rv.setArrayHolder(mArrayHolder);
                rv.notifyItemChanged(position);
            }

            @Override
            public void loadingMore(int position) {
                PostData data ;
                if(mCheck == KeyStore.MY_OWN_PROFILE)
                 data = mArrayHolder.getOwnProfileData().get(mArrayHolder.getOwnProfileData().size()-1);
                else
                 data = mArrayHolder.getOtherUserProfileData().get(mArrayHolder.getOtherUserProfileData().size()-1);
                if(!(data instanceof SimpleTvData))
                    setLoadMore();

            }



            @Override
            public void remove(int position) {
                Log.e("profile", "remove: this works"+position );
                mArrayHolder.getOwnProfileData().remove(position-1);
                rv.setArrayHolder(mArrayHolder);
                rv.notifyItemRemoved(position);
                rv.notifyItemRangeChanged(position, mArrayHolder.getOwnProfileData().size());







            }
        });
        rv.setHomeRvListener(implementation);
    }

    public void setRvListener(){
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
            public void onClickBackIcon() {
                ((Activity)mContext).finish();
            }

            @Override
            public void onClickEditIcon() {
                mContext.startActivity(new Intent(mContext, EditProfileActivity.class));
//
                Animatoo.animateSlideLeft(mContext);
            }

            @Override
            public void onClickSendMessage(ProfileHeaderData data) {
                 Intent intent = new Intent(mContext, InboxActivity.class);

                // intent.putExtra(KeyStore.PASS_DATA,data.getUsername());
                 intent.putExtra(KeyStore.PASS_OBJECT,data);
                 mContext.startActivity(intent);
                 Animatoo.animateSlideLeft(mContext);
               // mContext.startActivity(new Intent(mContext, ConversationActivity.class));
                //Animatoo.animateSlideLeft(mContext);
            }

            @Override
            public void viewProfile(CircularImageView imageView, ProfileHeaderData data) {

                mProfilePic = imageView;
                dismisPowerMenu();
                popUpImage(data);
            }

            @Override
            public void viewFollowers() {
                Intent intent = new Intent(mContext,FollowerActivity.class);
                FollowerActivityCheck check = new FollowerActivityCheck("only to see followers",passUsername);
                intent.putExtra(KeyStore.PASS_OBJECT,check);
                mContext.startActivity(intent);

                Animatoo.animateSlideUp(mContext);
            }

            @Override
            public void viewFollowingsUsers() {
                Intent intent = new Intent(mContext,FollowingUserActivity.class);
                intent.putExtra(KeyStore.PASS_DATA,passUsername);
                mContext.startActivity(intent);

                Animatoo.animateSlideUp(mContext);
            }



            @Override
            public void onClickCredits() {
                //start advertisement activity
                if(passUsername.equals(mSharedPrefer.getUsername())) {
                    mContext.startActivity(new Intent(mContext, AdvertisementMain.class));

                    Animatoo.animateSlideUp(mContext);
                }


            }

            @Override
            public void onClickBoostFollowers(ProfileHeaderData data) {
                Toaster.setToaster(mContext,"Boost this user followers = "+data.getUsername());
            }


            @Override
            public void startFollowing(Button followBtn) {
                mFollowBtn = followBtn;
                setStartFollowing();

            }

            @Override
            public void cancelFollowing(Button cancelFollowBtn) {
                  mFollowBtn = cancelFollowBtn;
                  userCancel();
            }


            @Override
            public void cancelFollowRequest(Button cancelFollowBtn) {
                mFollowBtn = cancelFollowBtn;
                cancelFollowReq();
            }




        });
    }

//      @Override
//            public void startFollowing(FancyButton followBtn, FancyButton cancelFollowingBtn, FancyButton waitBtn) {
//                mFollowBtn = followBtn;
//                mFollowedBtn = cancelFollowingBtn;//because when user click onAccept btn then cancel dialog show there is assign equal to accept btn
//                mWaitFollowRequestBtn = waitBtn;
//                setStartFollowing();
//
//            }
//
//            @Override
//            public void cancelFollowing(FancyButton cancelFollowBtn, FancyButton followBtn,FancyButton waitBtn) {
//                mFollowBtn = followBtn;
//                mFollowedBtn = cancelFollowBtn;
//                mWaitFollowRequestBtn = waitBtn;
//                userCancel();
//            }
//
//            @Override
//            public void cancelFollowRequest(FancyButton cancelFollowBtn, FancyButton followBtn, FancyButton waitBtn) {
//                mFollowBtn = followBtn;
//                mFollowedBtn = cancelFollowBtn;
//                mWaitFollowRequestBtn = waitBtn;
//                cancelFollowReq();
//            }

    private void cancelFollowReq(){
        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                cancelFollow();
            }
        });
        Dialoger.setConfirmDialog((Activity)mContext,"Cancel Follow Request","Do you want to cancel follow request");
    }


    private void cancelFollow() {
        //mWaitFollowRequestBtn.setVisibility(View.GONE);
        CancelFollowing cancelFollowing = new CancelFollowing();
        cancelFollowing.setCancelFollowingListener(new CancelFollowing.cancelFollowingListener() {
            @Override
            public void onSuccess(final String body) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Dialoger.dismissDialog();
                        Log.e(KeyStore.TAG, "run: "+body );
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            if(Validator.validateWebResult(result)){

                               // mFollowBtn.setVisibility(View.VISIBLE);
                               // mFollowBtn.setText("Follow");
                                FollowBtnStyle.setFollowBtn(mFollowBtn,mContext);
                               // mFollowedBtn.setVisibility(View.GONE);
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



    private void popUpImage(ProfileHeaderData data) {
        Intent intent = new Intent(mContext,ProfileViewActivity.class);
        intent.putExtra(KeyStore.PASS_DATA,data.getProfilePic());
        mContext.startActivity(intent);

    }

    private void setCancelFollowing(){
        CancelFollowing cancelFollowing = new CancelFollowing();
        cancelFollowing.setCancelFollowingListener(new CancelFollowing.cancelFollowingListener() {
            @Override
            public void onSuccess(final String body) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e(KeyStore.TAG, "cancel following : "+body);
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            if(Validator.validateWebResult(result)){
                              //  mWaitFollowRequestBtn.setVisibility(View.GONE);
                              //  mFollowBtn.setText("Follow");
                              //  mFollowedBtn.setVisibility(View.GONE);
                                FollowBtnStyle.setFollowBtn(mFollowBtn,mContext);

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

    private void userCancel(){
        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                dialog.dismiss();
                setCancelFollowing();
            }
        });
        Dialoger.setConfirmDialog((Activity)mContext,passUsername,"Do you want to unFollow ?");
    }

    private void setStartFollowing(){
//        mFollowBtn.setVisibility(View.GONE);
        StartFollowing startFollowing = new StartFollowing();
        startFollowing.setStartFollowingProcessListener(new StartFollowing.addFollowListener() {
            @Override
            public void onSuccess(final JSONObject object) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                // mFollowedBtn.setVisibility(View.VISIBLE);
                             //   mFollowedBtn.setVisibility(View.VISIBLE);
                            //    mFollowBtn.setText("Followed");
                                FollowBtnStyle.setFollowedBtn(mFollowBtn,mContext);

                            }else if(result.contains("wait")){
//                                mWaitFollowRequestBtn.setVisibility(View.VISIBLE);
                              //  mFollowBtn.setText("Request");
                                FollowBtnStyle.setRequestedBtn(mFollowBtn,mContext);
                                //getProfileInfo();
                            }else{
                                Toaster.setToaster(mContext,reason);
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



    private void selectImageFromGallery() {
        SelectCropImageFromGallery.selectImage((Activity) mContext,true);
    }

    public void dismisPowerMenu(){
        Menu.hideMenu();
    }

    private void afterCroppedImageSelection(){
        SelectCropImageFromGallery.setFileSelected(new SelectCropImageFromGallery.fileSelected() {
            @Override
            public void onSelected(Bitmap selectedImage, File selectedImageFile) {
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
                        Glide.with(mContext).load(Addresses.getWebAddress()+profilePic).into(mProfilePic);
                        mSharedPrefer.setProfilePath(profilePic);
                        //  CHECKER = "yes";


                    }else{
                        Toaster.setToaster(mContext,reason);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {

            }
        });

        UploadNewProfile.uploadProfile(file,mSharedPrefer.getUsername(),mContext);


    }


    public void retrieveAllPosts(String profileUsername,String lastPostId,String lastContestId){
        RetrieveAllPosts.retrievePost(mContext,mSharedPrefer.getUsername(),profileUsername,lastPostId,lastContestId,"","","");
        RetrieveAllPosts.setDataListener(new RetrieveAllPosts.RetrieveAllPostsDataListener() {
            @Override
            public void onRetrieveSinglePostData(PostData data, int postId) {
                if(!checkPresentAlreadySinglePostId.contains(postId+"")) {
                    checkPresentAlreadySinglePostId += ""+postId;
                    if(mCheck == KeyStore.MY_OWN_PROFILE)
                    mArrayHolder.getOwnProfileData().add(data);
                    else mArrayHolder.getOtherUserProfileData().add(data);
                }

            }

            @Override
            public void updateView() {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.getRecycledViewPool().clear();
                        rv.setArrayHolder(mArrayHolder);
                        rv.notifyDataSetChanged();
                      //  rv.notifyItemRangeInserted(0, ArrayHolder.profileBodyData.size()+1);
                    }
                });

            }

            @Override
            public void hidePb() {
                ((Activity)mContext).runOnUiThread(new Runnable() {
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
                    if(mCheck == KeyStore.MY_OWN_PROFILE)
                        mArrayHolder.getOwnProfileData().add(data);
                    else mArrayHolder.getOtherUserProfileData().add(data);

                }



            }

            @Override
            public void onEnd(PostData data) {
                if(mCheck == KeyStore.MY_OWN_PROFILE)
                    mArrayHolder.getOwnProfileData().add(data);
                else mArrayHolder.getOtherUserProfileData().add(data);


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
}
