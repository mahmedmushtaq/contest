package com.contest.competition.utils.activities.front.posts;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.emoji.widget.EmojiTextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.contest.competition.R;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.requests.data.contestreq.Vote;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.requests.data.postreq.RetrieveSimplePost;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Toaster;
import com.facebook.drawee.view.SimpleDraweeView;


public class SimplePostActivity extends BaseToolbarActivity {
    private NotificationData mData;
    private LoginSharedPrefer mPrefer;
    private TextView name,dateTime,totalSeen,totalComments,totalVotes;
    private EmojiTextView postedText;
    private ImageView profile,voteBtn,commentBtn;
    private SimpleDraweeView postedImage;
    private String singlePostId;
    private LinearLayout totalLl;
    private Button features;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = getIntent().getParcelableExtra(KeyStore.PASS_OBJECT);


        mPrefer = new LoginSharedPrefer(this);
        String[] split = mData.getLink().split("=");
        singlePostId = split[1];
        NotificationsReq.notificationOpened(mData.getId(),mPrefer.getUsername());


//        name = findViewById(R.id.name);
//        dateTime = findViewById(R.id.dateTimeHomeRvPost);
//        totalTv = findViewById(R.id.totalVotesAndComments_homeRvPost);
//        profile = findViewById(R.id.homeRvPost_profile);
//        postedImage = findViewById(R.id.homeRvPost_postedImage);a
//        postedText = findViewById(R.id.homeRvPost_postedStatus);
//        commentBtn = findViewById(R.id.commentBtn_homeRvPost);
//        voteBtn = findViewById(R.id.voteBtn_homeRvPost);

        profile =  findViewById(R.id.single_postProfileUser);
        name =  findViewById(R.id.single_postPostedByUser);
        dateTime =  findViewById(R.id.single_postDataTime);
        totalSeen =  findViewById(R.id.single_postTotalSeen);
        totalComments =  findViewById(R.id.single_postTotalComments);
        totalVotes = findViewById(R.id.single_postTotalVotes);
        postedImage =  findViewById(R.id.single_postPostedImage);
        postedText =  findViewById(R.id.single_postPostedText);
        commentBtn = findViewById(R.id.single_postCommentIv);
        voteBtn = findViewById(R.id.single_postVoteIv);
        features = findViewById(R.id.single_postFeaturesBtn);
        totalLl =  findViewById(R.id.single_postTotalLl);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),CommentActivity.class);
                intent.putExtra(KeyStore.PASS_DATA,singlePostId);
                intent.putExtra(KeyStore.COMMENT_TYPE,"single post");
                startActivity(intent);

                Animatoo.animateSlideUp(SimplePostActivity.this);
            }
        });

        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votePost();
            }
        });

        totalLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),CheckWhoVotesCommentsAndSeenActivity.class);
                intent.putExtra(KeyStore.PASS_DATA,singlePostId+"");
                startActivity(intent);

                Animatoo.animateSlideUp(SimplePostActivity.this);
            }
        });



        BackgroundAsync async = new BackgroundAsync();
        async.execute();



    }

    private void votePost() {
        Vote.vote(SimplePostActivity.this,mPrefer.getUsername(),singlePostId+"","","","single post");
        Vote.setVoteListener(new Vote.voteListener() {
            @Override
            public void onSuccess(String result, String reason) {
                if(Validator.validateWebResult(result)){
                    voteBtn.setColorFilter(getResources().getColor(R.color.peter));
                }else{
                    Toaster.setToaster(getBaseContext(),reason);
                }
            }
        });
    }

//    @Override
//    protected int setLayout() {
//        return R.layout.activity_simple_post;
//    }


    @Override
    protected int setLayout() {
        return R.layout.single_post_rv_layout;
    }

    @Override
    protected String setActivityName() {
        return "Single Post";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }


    private void loadSinglePostData(){
        RetrieveSimplePost.retrieveSinglePost(SimplePostActivity.this,singlePostId,mPrefer.getUsername());
        RetrieveSimplePost.setRetrieveListener(new RetrieveSimplePost.RetrieveSinglePostListener() {
            @Override
            public void onRetrieve(SimplePostData data) {
               setAfterLoading(data);
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void setAfterLoading(SimplePostData data){
        if(data.getPostedText().isEmpty()){
            postedText.setVisibility(View.GONE);
        }else{
            postedText.setVisibility(View.VISIBLE);
            postedText.setText(data.getPostedText());
        }

        if(data.getPostedImage().isEmpty()){
            postedImage.setVisibility(View.GONE);
        }else{
            postedImage.setVisibility(View.VISIBLE);
           Uri uri = Uri.parse(Addresses.getWebAddress()+data.getPostedImage());
         //  Glide.with(this).load(uri).into(postedImage);
           postedImage.setImageURI(uri);

        }

        name.setText(data.getPostedName());
        dateTime.setText(data.getPostedDateTime());
        Glide.with(this).load(Addresses.getWebAddress()+data.getPostedProfile()).into(profile);
        //totalTv.setText(HighLighter.setTotalVotesAndCommentsAndTotalSeen(data.getTotalVotesIntoK()+" votes","&",data.getTotalCommentsIntoK()+" comments",data.getTotalSeenIntoK()+" seens",this));
          totalComments.setText(data.getTotalCommentsIntoK());
          totalVotes.setText(data.getTotalVotesIntoK());
          totalSeen.setText(data.getTotalSeenIntoK());



    }


    private class BackgroundAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            loadSinglePostData();
            return null;
        }
    }
}
