package com.contest.competition.utils.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.contest.competition.R;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.List;

/**
 * Created by M Ahmed Mushtaq on 7/7/2018.
 */

public class Menu {

    public static final int SHOWN_AS_DROP_DOWN = 1;
    public static final int SHOWN_AS_ANCHOR_VIEW_CENTER = 2;

    private static menuClickListener mMenuClickListener;
    private static PowerMenu mPowerMenu;

    public  interface menuClickListener{
        void onMenuClick(int position,PowerMenuItem item);
    }

    public  static   void setMenuClickListener(menuClickListener listener){
        mMenuClickListener = listener;
    }

    public static void createMenu(Context context, List<PowerMenuItem> items, View view,int show){
        mPowerMenu = new PowerMenu.Builder(context)
                // list has "Novel", "Poerty", "Art"
                .addItemList(items)
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(context.getResources().getColor(R.color.black))
                //  .setSelectedTextColor(mContext.getResources().getColor(R.color.wetAsphalt))
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
                .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                    @Override
                    public void onItemClick(int position, PowerMenuItem item) {
                        if(mMenuClickListener != null)
                     mMenuClickListener.onMenuClick(position,item);
                    }
                })
                .build();

       //
        if(show == SHOWN_AS_ANCHOR_VIEW_CENTER)
        mPowerMenu.showAsAnchorCenter(view);
        if(show == SHOWN_AS_DROP_DOWN)
        mPowerMenu.showAsDropDown(view);

    }

    public static void hideMenu(){
        if(mPowerMenu != null){
            if(mPowerMenu.isShowing()){
                mPowerMenu.dismiss();
            }
        }
    }
}
