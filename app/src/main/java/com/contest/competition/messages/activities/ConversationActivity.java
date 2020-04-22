package com.contest.competition.messages.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;
import com.contest.competition.adapters.messagesadapters.ConversationRv;
import com.contest.competition.classes.interfaces.messages.messageslistener.OnClickConversationListener;
import com.contest.competition.classes.models.messagesModels.ConversationData;
import com.contest.competition.classes.models.messagesModels.MessagesArray;
import com.contest.competition.requests.data.messagesreq.RetrieveConversationListReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;
import com.contest.competition.utils.views.LinearDividerItemDecoration;

import java.util.ArrayList;

public class ConversationActivity extends BaseActivity {

    private LoginSharedPrefer mLoginSharedPrefer;
    private MessagesArray mMessagesArray;
    private RecyclerView mRecyclerView;
    private ConversationRv rv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView();




//        LinearLayout linearLayout = findViewById(R.id.conversation_view_container_ll);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                  startActivity(new Intent(getBaseContext(),InboxActivity.class));
//            }
//        });


        setToolbar();


        mMessagesArray = new MessagesArray();
        mMessagesArray.getConversationData().clear();

        mRecyclerView = findViewById(R.id.activity_conversation_rv);
        mSwipeRefreshLayout = findViewById(R.id.conversation_swipe);
        //mSwipeRefreshLayout.setRefreshing(false);

        mLoginSharedPrefer = new LoginSharedPrefer(this);
         rv = new ConversationRv();

         LinearDividerItemDecoration itemDecoration = new LinearDividerItemDecoration(this,R.color.silverDark,1,22);

        rv.setMessagesArray(mMessagesArray);
        OnClickConversationListener listener = new OnClickConversationListener(this,mRecyclerView,rv);
        rv.setConversationClick(listener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       // mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(rv);


        retrieveConversationsList();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMessagesArray.getConversationData().clear();
                retrieveConversationsList();
            }
        });



        //enableSwipeToDeleteAndUndo();


        //Toaster.setToaster(this,"Please wait conversation list is loading");
       // RetrieveConversationListReq.retrieveList(mLoginSharedPrefer.getUsername());



    }


    private void retrieveConversationsList(){
        mSwipeRefreshLayout.setRefreshing(false);
        RetrieveConversationListReq retrieveConversationListReq = new RetrieveConversationListReq();
        retrieveConversationListReq.setRetrieveConversationListener(new RetrieveConversationListReq.retrieveConversationListener() {
            @Override
            public void onRetrieve(ArrayList<ConversationData> conversationData) {

                mMessagesArray.setConversationData(conversationData);
                rv.setMessagesArray(mMessagesArray);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.notifyDataSetChanged();
                    }
                });


            }
        });

        retrieveConversationListReq.retrieveList(mLoginSharedPrefer.getUsername());

    }


    private void setToolbar(){

        TextView nameToolbar = findViewById(R.id.top_heading_text);
        nameToolbar.setText(R.string.messages);
        ImageView backBtn = findViewById(R.id.top_toolbar_back_btn);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this);
    }








    @Override
    protected int setLayout() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void notifyItemSelected(int position) {

    }
}
