package com.contest.competition.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by M Ahmed Mushtaq on 7/7/2018.
 */

public class FollowerActivityCheck implements Parcelable {
    //this check whether user view follower activity to see followers of profile user or it view to create contest with followers
    private String username;
    private String check;
    public FollowerActivityCheck(String check,String username){
        this.username = username;
        this.check = check;
    }

    protected FollowerActivityCheck(Parcel in) {
        username = in.readString();
        check = in.readString();
    }

    public static final Creator<FollowerActivityCheck> CREATOR = new Creator<FollowerActivityCheck>() {
        @Override
        public FollowerActivityCheck createFromParcel(Parcel in) {
            return new FollowerActivityCheck(in);
        }

        @Override
        public FollowerActivityCheck[] newArray(int size) {
            return new FollowerActivityCheck[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getCheck() {
        return check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(check);
    }
}
