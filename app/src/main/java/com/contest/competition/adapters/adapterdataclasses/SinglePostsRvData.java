package com.contest.competition.adapters.adapterdataclasses;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.contest.competition.R;
import com.contest.competition.adapters.holders.SinglePostRvHolder;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.HomeRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.ImageLoadProgressBar;
import com.contest.competition.utils.views.Menu;
import com.contest.competition.utils.views.PostView;
import com.contest.competition.utils.views.Toaster;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class SinglePostsRvData {
    public static int HOME_RV = 1;
    public static int PROFILE_RV = 2;
    public static int TRENDING_RV = 3;
    private static LoginSharedPrefer mPrefer;
    public static final String DELETE_POST = "Delete post";
    private static ArrayHolder mArrayHolder;

    private static int profileCheck;
    public static void setProfileCheck(int profileCheckInner){
        profileCheck = profileCheckInner;
    }

    public static void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    private static Handler mHandler;



    public static void putPostData(RecyclerView.ViewHolder holder, final int position, final HomeRvListener mHomeRvListener, final Context mContext, int check){
        final SinglePostRvHolder body = (SinglePostRvHolder) holder;
        mPrefer = new LoginSharedPrefer(mContext);

        final PostData postData;
        if(check == HOME_RV) {
            if(position <= mArrayHolder.getHomePostdata().size()) {
                postData = mArrayHolder.getHomePostdata().get(position);
                showSimplePostRecyclerView(postData,body,mContext,mHomeRvListener,position);
            }else Toaster.setToaster(mContext,"Please refresh a page");


        }
        else if(check == PROFILE_RV) {
            if(profileCheck == KeyStore.MY_OWN_PROFILE) {
                if(position-1 <= mArrayHolder.getOwnProfileData().size()) {//this condition is used to remove indexoutofboundexception error
                    postData = mArrayHolder.getOwnProfileData().get(position - 1);//because header be included therefore we remove header and get body data only
                    showSimplePostRecyclerView(postData,body,mContext,mHomeRvListener,position);
                }else Toaster.setToaster(mContext,"Please refresh a page");
            }else{
                if(position-1 <= mArrayHolder.getOtherUserProfileData().size()) {
                    postData = mArrayHolder.getOtherUserProfileData().get(position - 1);
                    showSimplePostRecyclerView(postData,body,mContext,mHomeRvListener,position);
                }else Toaster.setToaster(mContext,"Please refresh a page");
            }


        }else {
            if(position <= mArrayHolder.getTrendingData().size()) {
                postData = mArrayHolder.getTrendingData().get(position);
                showSimplePostRecyclerView(postData,body,mContext,mHomeRvListener,position);
            }else Toaster.setToaster(mContext,"Please refresh a page");
        }





    }
    private static void showSimplePostRecyclerView(PostData postData,final SinglePostRvHolder body,final Context mContext,final HomeRvListener mHomeRvListener,final int position){
        final SimplePostData data = (SimplePostData) postData;

        if(data == null) return;

        if(data.getPostedText().isEmpty()){
            body.postedText.setVisibility(View.GONE);
        }else{
            body.postedText.setVisibility(View.VISIBLE);
            body.postedText.setText(data.getPostedText());
        }

        if(data.getPostedImage().isEmpty()){
            body.postedImage.setVisibility(View.GONE);

        }else{

             body.postedImage.setVisibility(View.VISIBLE);
             Uri uri = Uri.parse(Addresses.getWebAddress()+data.getPostedImage());
           // body.postedImage.setImageURI(uri);
         //   Glide.with(mContext).load(uri).into(body.postedImage);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

//            body.postedImage.setController(Fresco.newDraweeControllerBuilder()
//                    .setImageRequest(
//                            ImageRequestBuilder.newBuilderWithSource(
//                                    Uri.parse(Addresses.getWebAddress()+data.getPostedImage()))
//                                    .setProgressiveRenderingEnabled(true)
//                                    .build())
//                    .setOldController(body.postedImage.getController())
//                    .build());


            GenericDraweeHierarchy hierarchy =
                    GenericDraweeHierarchyBuilder.newInstance(mContext.getResources())
                             .setProgressBarImage(new ImageLoadProgressBar(ContextCompat.getColor(mContext,R.color.colorAccent)))
                            .build();
            body.postedImage.setHierarchy(hierarchy);
            body.postedImage.setImageURI(uri);



//            GlideImageLoader loader = new GlideImageLoader(body.postedImage, body.singlePost_progressBar);
//            loader.setThumbNail(0.5f);
//
//            loader.load(Addresses.getWebAddress()+data.getPostedImage(),options);


//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .signature(ObjectKey(signature))
//
//            Glide.with(mContext).load(uri)//.apply(new RequestOptions().centerCrop())
//                   .thumbnail(0.01f)
//                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).listener(new RequestListener<Drawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    body.singlePost_progressBar.setVisibility(View.GONE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    new android.os.Handler().postDelayed(
//                            new Runnable() {
//                                public void run() {
//                                     body.singlePost_progressBar.setVisibility(View.GONE);
//                                }
//                            },
//                            1000);
//
//                    return false;
//                }
//            }).into(body.postedImage);


//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            Bitmap newBitmap = new Bitmap();
//
////save scaled down image to cache dir
//            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//
//            File imageFile = new File(filePath);
//
//// write the bytes in file
//            FileOutputStream fo = new FileOutputStream(imageFile);
//            fo.write(bytes.toByteArray());

        }

        body.name.setText(data.getPostedName());
        if(data.getIsPostBoosted() == PostView.IS_POST_BOOSTED){
            body.dateTime.setText("Boosted");
        }else {
            body.dateTime.setText(data.getPostedDateTime());
        }
        Glide.with(mContext).load(Addresses.getWebAddress()+data.getPostedProfile()).into(body.profile);
        //  body.totalTv.setText(HighLighter.setTotalVotesAndCommentsAndTotalSeen(data.getTotalVotesIntoK()+" votes",", ",data.getTotalCommentsIntoK()+" comments",data.getTotalSeenIntoK()+" seens",mContext));
        body.totalVotes.setText(data.getTotalVotesIntoK());
        body.totalComments.setText(data.getTotalCommentsIntoK());
        body.totalSeen.setText(data.getTotalSeenIntoK());


        if(data.getPostedBy().equals(mPrefer.getUsername())){
            body.features.setVisibility(View.VISIBLE);
        }else{
            body.features.setVisibility(View.GONE);

        }

// here works feature btn


//        if(data.getPostedBy().equals(mPrefer.getUsername())){
//            body.boostBtn.setVisibility(View.VISIBLE);
//        }else{
//            body.boostBtn.setVisibility(View.GONE);
//        }


//        body.boostBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mHomeRvListener != null)
//                    mHomeRvListener.onClickBoostBtn(postData,position);
//            }
//        });

        body.features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu.hideMenu();
                showFeatures(data,body.features,mContext,mHomeRvListener,position);
            }
        });


        body.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickCreatorProfile(data);
            }
        });

        //here feature works

//        body.postEditing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mHomeRvListener != null)
//                    mHomeRvListener.onPostEditingListener(data,position,body.postEditing);
//            }
//        });

        body.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickCreatorProfile(data);
            }
        });

        body.totalLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickVotesCommentAndSeenLl(data);
            }
        });

        body.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickCommentBtn(data);
            }
        });






        if(data.getAlreadyVote() == PostView.ALREADY_VOTE){
            int color = mContext.getResources().getColor(R.color.peter); //The color u want
            body.voteBtn.setColorFilter(color);

            // body.voteBtn.setImageResource(R.drawable.filled_star);
        }else{
            body.voteBtn.setImageResource(R.drawable.empty_star);
        }

        body.voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null) {
                    body.voteBtn.setColorFilter(mContext.getResources().getColor(R.color.peter));
                    mHomeRvListener.onClickVoteBtn(data, position, body.voteBtn);
                }
            }
        });

        body.postedImage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {

                    body.doubleClickPostedVote.setVisibility(View.VISIBLE);
                    imageDoubleTapListener(mHomeRvListener,body,mContext,data,position);
                    return super.onDoubleTap(e);
                }

            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }


    private static void imageDoubleTapListener(HomeRvListener mHomeRvListener, final SinglePostRvHolder body, Context context, SimplePostData data, int position){

        body.doubleClickPostedVote.postDelayed(new Runnable() {
            @Override
            public void run() {
                body.doubleClickPostedVote.setVisibility(View.GONE);
            }
        },700);

        if(mHomeRvListener != null) {

            body.voteBtn.setColorFilter(context.getResources().getColor(R.color.peter));
            mHomeRvListener.onClickVoteBtn(data, position, body.voteBtn);
        }

    }


    private static void showFeatures(final PostData data, Button featureBtn, Context context, final HomeRvListener listener, final int rvPosition) {


        final List<PowerMenuItem> items = new ArrayList<>();

        items.add(new PowerMenuItem("Boost Post",false));
        items.add(new PowerMenuItem(DELETE_POST,false));



        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {

                    if(position == 0)
                    { if(listener != null)
                            listener.onClickBoostBtn(data);
                    }else {
                        if(listener != null)
                            listener.onClickDeleteBtn(data,rvPosition);
                    }

            }
        });

        Menu.createMenu(context,items,featureBtn,Menu.SHOWN_AS_DROP_DOWN);


    }

}
