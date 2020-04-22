package com.contest.competition.adapters.messagesadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;
import com.contest.competition.adapters.messagesadapters.messagesHolders.ConversationRvHolder;
import com.contest.competition.adapters.messagesadapters.rvdata.ConversationRvData;
import com.contest.competition.classes.interfaces.messages.OnClickConversation;
import com.contest.competition.classes.models.messagesModels.ConversationData;
import com.contest.competition.classes.models.messagesModels.MessagesArray;

public class ConversationRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private MessagesArray mMessagesArray;
    private Context mContext;

    private OnClickConversation mListener;
    public void setConversationClick(OnClickConversation listener){
        mListener = listener;
    }


    public void setMessagesArray(MessagesArray messagesArray){
        mMessagesArray = messagesArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ConversationRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position < mMessagesArray.getConversationData().size()){

            final ConversationData conversationData = mMessagesArray.getConversationData().get(position);
            final ConversationRvHolder bodyHolder = (ConversationRvHolder) holder;
            ConversationRvData rvData = new ConversationRvData();
            rvData.setContext(mContext);
            rvData.setBodyHolder(bodyHolder);
            rvData.setConversationData(conversationData);
            rvData.setListener(mListener);
            rvData.setPosition(position);
            rvData.setData();




        }

    }

    @Override
    public int getItemCount() {
        if(mMessagesArray != null)
        return mMessagesArray.getConversationData().size();
        else return 0;
    }



}
