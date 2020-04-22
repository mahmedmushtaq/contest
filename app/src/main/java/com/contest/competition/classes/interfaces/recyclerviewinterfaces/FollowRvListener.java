package com.contest.competition.classes.interfaces.recyclerviewinterfaces;

import com.contest.competition.classes.models.FollowData;

/**
 * Created by M Ahmed Mushtaq on 7/22/2018.
 */

public interface FollowRvListener {
    void viewProfileListener(FollowData data);
    void onNameAndUsernameContainerListener(FollowData data);
    void onViewEndRvItem(FollowData data,int position);
}
