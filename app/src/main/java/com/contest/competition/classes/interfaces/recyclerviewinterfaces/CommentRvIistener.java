package com.contest.competition.classes.interfaces.recyclerviewinterfaces;

import com.contest.competition.classes.models.CommentData;

/**
 * Created by M Ahmed Mushtaq on 7/29/2018.
 */

public interface CommentRvIistener {
    void onRemoveComments(CommentData data, int position);
    void loadMoreComments(CommentData data,int position);
    void onClickProfile(CommentData data);

}
