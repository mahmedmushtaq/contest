package com.contest.competition.classes.classesforactivity;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.contest.competition.adapters.rv.HomeRv;
import com.contest.competition.classes.interfaces.HomeRvImplementation;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.models.SimpleTvData;
import com.contest.competition.requests.data.postreq.RetrieveAllPosts;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.PostView;
import com.contest.competition.utils.views.Toaster;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class MainPageDataLoadingClass {
    private ArrayList<Integer> containOnlyContestId = new ArrayList<>();
    private ArrayList<Integer> containOnlySimplePostId = new ArrayList<>();
    private ArrayList<Integer> boostedIds = new ArrayList<>();
    private String simplePostIdsLl = "";
    private Context context;
    private LoginSharedPrefer mPrefer;
    private RecyclerView mRecyclerView;
    private HomeRv rv;
    private SwipeRefreshLayout swipe;
    private ProgressBar pb;
    private ArrayHolder mArrayHolder;

    private int checkPost;


    public void setArrayHolder(ArrayHolder arrayHolder) {
        mArrayHolder = arrayHolder;
    }

    public void setPb(ProgressBar pb) {
        this.pb = pb;
    }

    public void setCheckPost(int checkPost) {
        this.checkPost = checkPost;
    }

    public void setSwipe(SwipeRefreshLayout sw) {
        swipe = sw;
    }

    public void setHomeRv(HomeRv rv) {
        this.rv = rv;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setContext(Context mContext) {
        this.context = mContext;
    }

    public void setLoginSharedPrefer(LoginSharedPrefer loginSharedPrefer) {
        mPrefer = loginSharedPrefer;
    }


    public void clearArray() {

        if (mArrayHolder != null) {
            mArrayHolder.getHomePostdata().clear();
            containOnlyContestId.clear();
            containOnlySimplePostId.clear();
            simplePostIdsLl = "";
        }

    }

    public void onSwipeLayout() {
        clearArray();
        retrieveAllPosts("", "", "", "", "", "");

    }

    public void retrieveAllPosts(String profileUsername, String lastPostId, String lastContestId, final String firstPostId, final String firstContestId, String lastBoostedId) {


        try {

            RetrieveAllPosts.retrievePost(context, mPrefer.getUsername(), profileUsername, lastPostId, lastContestId, firstPostId, firstContestId, lastBoostedId);
        } catch (IndexOutOfBoundsException e) {
            Toaster.setToaster(context, "Please refresh a page");
        }


        RetrieveAllPosts.setDataListener(new RetrieveAllPosts.RetrieveAllPostsDataListener() {

            @Override
            public void onRetrieveSinglePostData(PostData data, int id) {


                if (!simplePostIdsLl.contains(id + "")) {
                    simplePostIdsLl += "" + id;
                    if (firstContestId.isEmpty() && firstPostId.isEmpty()) {
                        try {
                            mArrayHolder.getHomePostdata().add(data);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Log.e("MainPageData", "onRetrieveSinglePostData: MAinPageDataLoadingClass ArrayIndexOutOfBoundException = ");
                        }
                    } else {
                        mArrayHolder.getHomePostdata().add(0, data);
                    }

                } else {
                    // Log.e("HomeActivity", "onRetrieveSinglePostData: already contain data" );
                }


            }

            @Override
            public void hidePb() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                        pb.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void updateView() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.setArrayHolder(mArrayHolder);
                        rv.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onEnd(PostData data) {
                //end reached mean no further data is found

                Log.e("data", "onEnd: End Reached ");
                // if(mArrayHolder.getHomePostdata().size() > 0) {
                PostData postData = mArrayHolder.getHomePostdata().get(mArrayHolder.getHomePostdata().size() - 1);
                if (!(postData instanceof SimpleTvData))
                    mArrayHolder.getHomePostdata().add(data);
//                }else{
//                    mArrayHolder.getHomePostdata().add(data);
//                }

            }

            @Override
            public void onRetrieveOnlyIds(ArrayList<Integer> contestIds, ArrayList<Integer> postIds) {
                if (firstPostId.isEmpty() && firstContestId.isEmpty()) {
                    containOnlyContestId.addAll(contestIds);
                    containOnlySimplePostId.addAll(postIds);
                } else {
                    containOnlyContestId.addAll(0, contestIds);
                    containOnlySimplePostId.addAll(0, postIds);
                }
            }


            @Override
            public void boostedId(ArrayList<Integer> boostedIds) {
                boostedIds.addAll(boostedIds);
            }

            @Override
            public void onRetrieveContestData(final PostData data, int postId, int contestId) {


                if (!simplePostIdsLl.contains(postId + "")) {
                    simplePostIdsLl += "" + postId;

                    if (firstContestId.isEmpty() && firstPostId.isEmpty()) {
                        try{
                            mArrayHolder.getHomePostdata().add(data);
                        }catch(Exception e){
                            Log.e("exceptionContestData", "onRetrieveContestData: Exception = "+e.getMessage() );
                        }



                    } else {

                        mArrayHolder.getHomePostdata().add(0, data);
                    }


                } else {
                    Log.e("HomeActivity", "onRetrieveSinglePostData: already contain data");
                }


            }

            @Override
            public void onFail() {

            }


        });

    }

    public void rvListener() {
        HomeRvImplementation implementation = new HomeRvImplementation(context, mPrefer.getUsername());
        implementation.setListener(new HomeRvImplementation.SuccessHomeRvImplementationListener() {
            @Override
            public void Successful(int position) {

                rv.setArrayHolder(mArrayHolder);
                rv.notifyItemChanged(position, new HomeRv());

            }

            @Override
            public void loadingMore(int position) {
                Log.e("loadingMOre", "loadingMore: Loading more ");
                // if(containOnlyHomeSimplePostId.isEmpty()) {
                //       Toaster.setToaster(getBaseContext(),"loading more");
                // if (containOnlyHomeContestId.get(containOnlyHomeContestId.size() - 1) > -1 && containOnlyHomeSimplePostId.get(containOnlyHomeSimplePostId.size() - 1) > -1)
                PostData data = mArrayHolder.getHomePostdata().get(mArrayHolder.getHomePostdata().size() - 1);
                if (!(data instanceof SimpleTvData)) {
                    if (data instanceof SimplePostData) {
                        SimplePostData simplePostData = (SimplePostData) data;
                        if (simplePostData.getIsPostBoosted() != PostView.IS_POST_BOOSTED) {
                            //mean when last post is boosted then not load more posts .and its not possible
                            //and when possible then more data is not found in server hehe

                            setLoadMore();
                        }
                    } else {
                        setLoadMore();
                    }

                    setLoadMore();
                }


            }


            @Override
            public void remove(int position) {
                mArrayHolder.getHomePostdata().remove(position);
                mRecyclerView.removeViewAt(position);
                rv.setArrayHolder(mArrayHolder);
                rv.notifyItemRemoved(position);
            }
        });
        rv.setHomeRvListener(implementation);
    }

    public void setLoadMore() {

        if (containOnlySimplePostId != null && containOnlyContestId != null && !containOnlySimplePostId.isEmpty() && !containOnlyContestId.isEmpty()) {

            if (boostedIds.isEmpty()) {
                //we need last id because we retrieve data descending order so last id needed to retrieve old data

                try {


                    int lastPostId = containOnlySimplePostId.get(containOnlySimplePostId.size() - 1);
                    int lastContestId = containOnlyContestId.get(containOnlyContestId.size() - 1);
                    retrieveAllPosts("", lastPostId + "", lastContestId + "", "", "", "");
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e("null pointer exception", "setLoadMore: Index pointer exception ");
                } catch (NullPointerException e) {
                    Log.e("null pointer exception", "setLoadMore: Null pointer exception ");
                }

            } else {

                int lastPostId = containOnlySimplePostId.get(containOnlySimplePostId.size() - 1);
                int lastContestId = containOnlyContestId.get(containOnlyContestId.size() - 1);
                int lastBoostId = boostedIds.get(boostedIds.size() - 1);
                retrieveAllPosts("", lastPostId + "", lastContestId + "", "", "", lastBoostId + "");

            }
        } else if (!containOnlySimplePostId.isEmpty() && containOnlyContestId.isEmpty()) {
            if (boostedIds.isEmpty()) {
                int lastPostId = containOnlySimplePostId.get(containOnlySimplePostId.size() - 1);
                retrieveAllPosts("", lastPostId + "", "", "", "", "");
            } else {
                int lastPostId = containOnlySimplePostId.get(containOnlySimplePostId.size() - 1);
                int lastBoostId = boostedIds.get(boostedIds.size() - 1);
                retrieveAllPosts("", lastPostId + "", "", "", "", lastBoostId + "");

            }
        } else if (containOnlySimplePostId.isEmpty() && !containOnlyContestId.isEmpty()) {
            if (boostedIds.isEmpty()) {

                int lastContestId = containOnlyContestId.get(containOnlyContestId.size() - 1);
                retrieveAllPosts("", "", lastContestId + "", "", "", "");
            } else {
                int lastContestId = containOnlyContestId.get(containOnlyContestId.size() - 1);
                int lastBoostId = boostedIds.get(boostedIds.size() - 1);
                retrieveAllPosts("", "", lastContestId + "", "", "", "" + lastBoostId);

            }
        }

    }

//    public void loadNewData() {
//
//        if(!containOnlySimplePostId.isEmpty() && !containOnlyContestId.isEmpty()) {
//
//            int firstPostId = containOnlySimplePostId.get(0);
//            //first id is get because in server side we get data descending order so in
//            //order to get more data we need first id and then move down
//            int firstContestId = containOnlyContestId.get(0);
//
//
//            retrieveAllPosts("","","",firstPostId+"",firstContestId+"","");
//
//        }else if(!containOnlySimplePostId.isEmpty() && containOnlyContestId.isEmpty()){
//
//            int firstPostId = containOnlySimplePostId.get(0);
//            retrieveAllPosts("","","",""+firstPostId,"","");
//
//        }else if(containOnlySimplePostId.isEmpty() && !containOnlyContestId.isEmpty()){
//
//            int firstContest = containOnlyContestId.get(0);
//            retrieveAllPosts("","","","",""+firstContest,"");
//
//        }
//
//    }


}
