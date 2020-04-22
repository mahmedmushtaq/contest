package com.contest.competition.classes.models.messagesModels;

import java.io.Serializable;

public class ConversationData implements Serializable {
    private String lastMessageSecretKey;
    private String name,toUsername;
    private String profilePicPath;
    private String lastMessage;
    private String messagesDateTime;
    private int conversationId;

    public String getFromSubscription() {
        return fromSubscription;
    }

    public void setFromSubscription(String fromSubscription) {
        this.fromSubscription = fromSubscription;
    }

    public String getToSubscription() {
        return toSubscription;
    }

    public void setToSubscription(String toSubscription) {
        this.toSubscription = toSubscription;
    }

    private String fromSubscription;
    private String toSubscription;


    public String getLastMessageSecretKey() {
        return lastMessageSecretKey;
    }

    public void setLastMessageSecretKey(String lastMessageSecretKey) {
        this.lastMessageSecretKey = lastMessageSecretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getMessagesDateTime() {
        return messagesDateTime;
    }

    public void setMessagesDateTime(String messagesDateTime) {
        this.messagesDateTime = messagesDateTime;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
}
