package com.contest.competition.classes.interfaces.recyclerviewinterfaces;

import com.contest.competition.classes.models.NotificationData;

/**
 * Created by M Ahmed Mushtaq on 6/28/2018.
 */

public interface NotificationRvListener {
    void onViewLastItem(NotificationData data, int position);
    void onItemClick(NotificationData data);
    void onProfileClick(NotificationData data);
}
