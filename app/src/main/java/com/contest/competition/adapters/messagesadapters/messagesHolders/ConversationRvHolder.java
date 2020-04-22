package com.contest.competition.adapters.messagesadapters.messagesHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;
import com.contest.competition.classes.models.messagesModels.MessagesArray;

public class ConversationRvHolder  extends RecyclerView.ViewHolder {

    public ImageView userProfile;
    public TextView user_username,lastMessage,messageDateTime;
    public LinearLayout singleConversationContainerLl;

    public ConversationRvHolder(@NonNull View itemView) {
        super(itemView);

        userProfile = itemView.findViewById(R.id.conversation_userProfile);
        user_username = itemView.findViewById(R.id.conversation_user_name);
        lastMessage = itemView.findViewById(R.id.conversation_text);
        messageDateTime = itemView.findViewById(R.id.conversation_dateTime);
        singleConversationContainerLl = itemView.findViewById(R.id.conversation_view_container_ll);


    }
}
