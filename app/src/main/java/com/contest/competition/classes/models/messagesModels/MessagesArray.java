package com.contest.competition.classes.models.messagesModels;

import java.util.ArrayList;

public class MessagesArray {
    private ArrayList<ConversationData> mConversationData = new ArrayList<>();

    public void setConversationData(ArrayList<ConversationData> conversationData){
        mConversationData.addAll(conversationData);
    }

    public ArrayList<ConversationData> getConversationData(){
        return mConversationData;
    }

}
