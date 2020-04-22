package com.contest.competition.classes.models;

import android.graphics.Bitmap;

/**
 * Created by M Ahmed Mushtaq on 6/17/2018.
 */

public class SettingBodyData {

    private Bitmap mSettingPic;
    private String mName;

    public SettingBodyData(Bitmap settingPic, String name){
        this.mName = name;
        this.mSettingPic = settingPic;
    }

    public Bitmap getSettingPic() {
        return mSettingPic;
    }

    public String getName() {
        return mName;
    }
}
