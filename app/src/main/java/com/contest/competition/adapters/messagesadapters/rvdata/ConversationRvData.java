package com.contest.competition.adapters.messagesadapters.rvdata;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.contest.competition.adapters.messagesadapters.messagesHolders.ConversationRvHolder;
import com.contest.competition.classes.interfaces.messages.OnClickConversation;
import com.contest.competition.classes.models.messagesModels.ConversationData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.utils.views.Menu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;

public class ConversationRvData {
    private Context mContext;
    private ConversationRvHolder bodyHolder;
    private ConversationData conversationData;
    private OnClickConversation mListener;
    private int position;

    public void setListener(OnClickConversation onClickConversationListener){
        mListener = onClickConversationListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setContext(Context context){
        mContext = context;
    }

    public void setBodyHolder(ConversationRvHolder holder){
        bodyHolder = holder;
    }

    public void setConversationData(ConversationData data){
        conversationData = data;
    }

    public void setData(){
        bodyHolder.messageDateTime.setText(conversationData.getMessagesDateTime());
        bodyHolder.lastMessage.setText(conversationData.getLastMessage());
        Glide.with(mContext)
                .load(Addresses.getWebAddress()+conversationData.getProfilePicPath())
                .into(bodyHolder.userProfile);
        bodyHolder.user_username.setText(conversationData.getName());
        bodyHolder.singleConversationContainerLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onClick(conversationData);
            }
        });

        bodyHolder.singleConversationContainerLl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                setMenu(bodyHolder.singleConversationContainerLl);

                return true;
            }
        });
    }

    private void setMenu(View view){
        PowerMenuItem item = new PowerMenuItem("Delete",false);
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                if(mListener != null) {
                    mListener.onDeleteClick(conversationData, position);
                    Menu.hideMenu();
                }
            }
        });
        ArrayList<PowerMenuItem> items = new ArrayList<>();
        items.add(item);

        Menu.createMenu(mContext,items,view,Menu.SHOWN_AS_ANCHOR_VIEW_CENTER);

    }
}
