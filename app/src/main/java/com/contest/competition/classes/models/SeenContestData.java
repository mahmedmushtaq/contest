package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 8/20/2018.
 */

public class SeenContestData {
    private int contestId,rvPosition;

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public void setRvPosition(int rvPosition) {
        this.rvPosition = rvPosition;
    }

    public int getContestId() {
        return contestId;
    }

    public int getRvPosition() {
        return rvPosition;
    }
}
