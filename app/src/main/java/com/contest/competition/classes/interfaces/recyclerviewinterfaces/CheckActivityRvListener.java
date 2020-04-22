package com.contest.competition.classes.interfaces.recyclerviewinterfaces;

import com.contest.competition.classes.models.SawVoteCheckData;

/**
 * Created by M Ahmed Mushtaq on 8/24/2018.
 */

public interface CheckActivityRvListener {
    void onReachingBottom(SawVoteCheckData data);
    void visitProfileOnClick(SawVoteCheckData data);
}
