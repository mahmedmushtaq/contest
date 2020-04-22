package com.contest.competition.utils.activities.front.posts;

import android.os.AsyncTask;

import android.os.Bundle;


import androidx.emoji.widget.EmojiEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.adapters.rv.CommentRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadComments;
import com.contest.competition.classes.interfaces.listenerCaller.CommentRvListenerCaller;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CommentData;
import com.contest.competition.requests.data.commentsReq.InsertComment;
import com.contest.competition.requests.data.commentsReq.LoadComments;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.LinearDividerItemDecoration;
import com.contest.competition.utils.views.Toaster;

import java.util.ArrayList;



public class CommentActivity extends BaseToolbarActivity {
    private String postId;
    private String commentType ;
    private CommentRv rv;
    private RecyclerView mRecyclerView;
    private int noOfComments = 0;
    private SwipeRefreshLayout mRefreshLayout;
    private EmojiEditText commentsEt;
    private ImageView sendComments;
    private LoginSharedPrefer mPrefer;
    private TextView totalCommentsTv;
    private LinearLayoutManager mManager;
    private ArrayHolder mArrayHolder;
    private LoadComments mLoadComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mArrayHolder = new ArrayHolder();
        mLoadComments = new LoadComments(this);
        postId = getIntent().getStringExtra(KeyStore.PASS_DATA);
        commentType = getIntent().getStringExtra(KeyStore.COMMENT_TYPE);

        mRecyclerView = findViewById(R.id.comment_rv);
        mRefreshLayout = findViewById(R.id.commentSwipe);
        commentsEt = findViewById(R.id.comments_emojiEt);
        sendComments = findViewById(R.id.comments_send);
        totalCommentsTv = findViewById(R.id.total_commentsTv);
        mPrefer = new LoginSharedPrefer(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mArrayHolder != null) {
                    mArrayHolder.getCommentData().clear();
                }
                loadData("");
            }
        });
        mRefreshLayout.setRefreshing(false);

        setRecyclerView();

        LoadCommentBackground background = new LoadCommentBackground();
        background.execute();


        sendComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSendComments();
            }
        });



    }

    private void setSendComments() {
        if(commentsEt.getText().length() > 0){
            sendComment();
        }else{
            Toaster.setToaster(this,"Enter your comments");
        }

    }

    private void sendComment() {
        Dialoger.setDialog(CommentActivity.this,"Comment","Please wait your comment is being posted");
        InsertComment comment = new InsertComment(CommentActivity.this);
        comment.insertComment(postId +"",commentsEt.getText().toString(),mPrefer.getUsername(),commentType);
        commentsEt.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Animatoo.animateSlideDown(CommentActivity.this);
    }

    private void setRecyclerView(){
        LinearDividerItemDecoration decoration = new LinearDividerItemDecoration(this, R.color.lightSilver,1, R.id.commenter_profilePic);
        mManager = new LinearLayoutManager(this);
        mManager.setStackFromEnd(true);
        mManager.setReverseLayout(true);
        rv = new CommentRv();
        rv.noOfComments = noOfComments+"";
        CommentRvListenerCaller listener = new CommentRvListenerCaller(postId,this,mLoadComments,mRecyclerView,rv,mArrayHolder);
        rv.setCommentRvListener(listener);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(rv);


    }



    private void loadData(final String lastId) {

        mLoadComments.loadComments(postId,lastId);
        mLoadComments.setLoadComments(new OnLoadComments() {
            @Override
            public void onSuccess(ArrayList<CommentData> commentData) {
                CommentData commentsData = commentData.get(0);
                mRefreshLayout.setRefreshing(false);
                totalCommentsTv.setText(commentsData.getNoOfComments()+" comments");


                mArrayHolder.setCommentData(commentData);
                rv.setArrayHolder(mArrayHolder);
                rv.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void finish() {
        super.finish();

        Animatoo.animateSlideDown(CommentActivity.this);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected String setActivityName() {
        return "Comment Activity";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }


    private class LoadCommentBackground extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            loadData("");
            return null;
        }
    }
}
