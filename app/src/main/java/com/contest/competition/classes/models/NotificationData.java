package com.contest.competition.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by M Ahmed Mushtaq on 6/18/2018.
 */

public class NotificationData implements Parcelable{
    private String mName,mUsername,mMessage,mProfilePics,mLink,mOpened,mViewed,mDateTime;
    private int mId;

    public NotificationData(String name, String username, String message, String profilePics, String link, String opened, String viewed, String dateTime,int id) {
        mName = name;
        mUsername = username;
        mMessage = message;
        mProfilePics = profilePics;
        mLink = link;
        mOpened = opened;
        mViewed = viewed;
        mDateTime = dateTime;
        mId = id;

    }


    protected NotificationData(Parcel in) {
        mName = in.readString();
        mUsername = in.readString();
        mMessage = in.readString();
        mProfilePics = in.readString();
        mLink = in.readString();
        mOpened = in.readString();
        mViewed = in.readString();
        mDateTime = in.readString();

        mId = in.readInt();
    }

    public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getProfilePics() {
        return mProfilePics;
    }

    public String getLink() {
        return mLink;
    }

    public String getOpened() {
        return mOpened;
    }

    public String getViewed() {
        return mViewed;
    }

    public String getDateTime() {
        return mDateTime;
    }


    public int getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mUsername);
        dest.writeString(mMessage);
        dest.writeString(mProfilePics);
        dest.writeString(mLink);
        dest.writeString(mOpened);
        dest.writeString(mViewed);
        dest.writeString(mDateTime);

        dest.writeInt(mId);
    }
}
