package com.contest.competition.classes.interfaces.requestinterfaces;

import com.contest.competition.classes.models.CommentData;

import java.util.ArrayList;

public interface OnLoadComments {
    void onSuccess(ArrayList<CommentData> commentData);
}
