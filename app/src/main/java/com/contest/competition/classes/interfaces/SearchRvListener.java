package com.contest.competition.classes.interfaces;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.contest.competition.classes.models.SearchData;

/**
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public interface SearchRvListener {
    void onViewEndRvItem(SearchData data, int position);
    void onAddFollowListener(SearchData data, Button followBtn);
    void viewProfileListener(SearchData data);

}
