package com.contest.competition.classes.interfaces.messages.messageslistener;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.adapters.messagesadapters.ConversationRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.messages.OnClickConversation;
import com.contest.competition.classes.models.messagesModels.ConversationData;
import com.contest.competition.messages.activities.ConversationActivity;
import com.contest.competition.messages.activities.InboxActivity;
import com.contest.competition.requests.data.messagesreq.DeleteConversation;

public class OnClickConversationListener implements OnClickConversation {
     private  Context mContext;
     private  RecyclerView mRecyclerView;
     private  ConversationRv mRv;


     public OnClickConversationListener(Context context, RecyclerView recyclerView, ConversationRv rv){
         mContext = context;
         mRecyclerView = recyclerView;
         mRv = rv;
     }

    @Override
    public void onClick(ConversationData data) {
        Intent intent = new Intent(mContext, InboxActivity.class);

        intent.putExtra(KeyStore.PASS_OBJECT,data);
        mContext.startActivity(intent);
        Animatoo.animateSlideLeft(mContext);
    }

    @Override
    public void onDeleteClick(ConversationData data,int position) {
        DeleteConversation.deleteConversation(data.getConversationId()+"");

         if(mRecyclerView != null)
             mRecyclerView.removeViewAt(position);

         else if(mRv != null) {
             mRv.notifyItemRemoved(position);
             mRv.notifyDataSetChanged();
         }




    }
}
