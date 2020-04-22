package com.contest.competition.classes.interfaces.listenerCaller;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.contest.competition.adapters.rv.CommentRv;
import com.contest.competition.classes.VisitProfile;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CommentRvIistener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CommentData;
import com.contest.competition.requests.data.commentsReq.LoadComments;
import com.contest.competition.requests.data.commentsReq.RemoveComment;
import com.contest.competition.utils.views.Dialoger;

public class CommentRvListenerCaller implements CommentRvIistener {
    private Context mContext;
    private LoadComments mLoadComments;
    private String postId;

    private RecyclerView mRecyclerView;
    private CommentRv rv;
    private ArrayHolder mArrayHolder;

    public CommentRvListenerCaller(String postId, Context context, LoadComments loadComments, RecyclerView recyclerView, CommentRv rv, ArrayHolder arrayHolder){
        mLoadComments = loadComments;
        this.postId = postId;
        mContext = context;
        this.rv = rv;
        mRecyclerView = recyclerView;
        mArrayHolder = arrayHolder;

    }




    @Override
    public void onRemoveComments(CommentData data, int position) {

        removeCommentDailogFirst(position,data);
    }

    @Override
    public void loadMoreComments(CommentData data, int position) {
         mLoadComments.loadComments(postId,data.getCommentId()+"");
    }

    @Override
    public void onClickProfile(CommentData data) {
        VisitProfile.with(mContext).visitUserProfile(data.getCommenterUsername());
    }

    private void removeCommentDailogFirst(final int rvPosition, final CommentData data){
        Dialoger.setConfirmDialog((Activity)mContext,"Comment","Are you sure you want to remove this comment");
        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                dialog.dismiss();
                removeComment(rvPosition,data);
            }
        });
    }
    private void removeComment(int rvPosition,CommentData data) {
        RemoveComment.remove(data.getCommentId()+"", postId +"",mContext);

        mRecyclerView.removeViewAt(rvPosition);
        mArrayHolder.getCommentData().remove(data);
        rv.setArrayHolder(mArrayHolder);
        rv.notifyDataSetChanged();

    }


}
