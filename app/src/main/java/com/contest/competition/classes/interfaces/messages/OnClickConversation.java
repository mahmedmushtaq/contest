package com.contest.competition.classes.interfaces.messages;

import com.contest.competition.classes.models.messagesModels.ConversationData;

public interface OnClickConversation {
    void onClick(ConversationData data);
    void onDeleteClick(ConversationData data,int position);
}
