package com.contest.competition.classes.classesforactivity;


import androidx.appcompat.widget.Toolbar;

public class HomeActivityClass2 {
//    private EditText searchEt;
//    private Toolbar searchActivityToolbar;
//    private RecyclerView mRecyclerView;
//    private HomeRv rv;
//    private LoginSharedPrefer mPrefer;
//    private ProgressBar homeRvPb;
//    private SwipeRefreshLayout swipe;
//    private HomeActivity.BackgroundAsync mBackgroundAsync;
//
//    // private CountDownTimer mTimer;
//    private boolean countDownTimerIsRunning = false;
//    private FloatingActionButton newPostBtn;
//
//    private ArrayList<Integer> containOnlyHomeContestId = new ArrayList<>();
//    private ArrayList<Integer> containOnlyHomeSimplePostId = new ArrayList<>();
//    private ArrayList<Integer> boostedIdsHome = new ArrayList<>();
//    private String simplePostIdsLl = "";
//
//
//    private Handler mHandler ;
//
//
//    private CountDownTimer mTimer;
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // searchEt = findViewById(R.id.search_Et);
//        //searchActivityToolbar = findViewById(R.id.searchActivity_toolbar);
//        ArrayHolder.homePostData.clear();
//        containOnlyHomeContestId.clear();
//        containOnlyHomeSimplePostId.clear();
//        simplePostIdsLl = "";
//
//
//
//
//        mRecyclerView = findViewById(R.id.home_rv);
//        homeRvPb = findViewById(R.id.homeRvPb);
//        swipe = findViewById(R.id.swipeHome);
//        swipe.setRefreshing(false);
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                ArrayHolder.homePostData.clear();
//                containOnlyHomeContestId.clear();
//                containOnlyHomeSimplePostId.clear();
//                simplePostIdsLl = "";
//
//
//                retrieveAllPosts("","","","","","");
//
//
//            }
//        });
//
//
//
//        mHandler = new Handler();
//
//        newPostBtn = findViewById(R.id.fab_new_post);
//
//
//
//
//
//        // final LinearLayoutManager manager = new LinearLayoutManager(this);
//
//        //  final CustomLinearLayoutManager manager = new CustomLinearLayoutManager(this);
//
//
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//
//
//
//
//
//        mPrefer = new LoginSharedPrefer(this);
//
//        rv = new HomeRv();
//
//        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.getRecycledViewPool().clear();
//        mRecyclerView.setAdapter(rv);
//        mRecyclerView.setItemAnimator(null);
//        int windowVisibility = mRecyclerView.getWindowVisibility();
//        Log.e("check", "onCreate: window visibility = "+windowVisibility );
//
//
//
//
//
//        rvListener();
//
//
//        // setSearchProcess();
//
//
//        newPostBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createNewPost();
//            }
//        });
//
//
//
//        mBackgroundAsync = new HomeActivity.BackgroundAsync();
//
//        mBackgroundAsync.execute();
//
//
//
//
//
//
//        //latest data retrieving system
//
//        mTimer = new HomeActivity.MyCountDownTimer(30000,1000);
//        //  Here millisecInFuture is the time you set in millisecond
//        // when you want CountDownTimer to stop and countDownInterval is the interval time in millisecond
//        // you set after which number will increment in CountDownTimer.
//
//        mTimer.start();
//
//
//
////        Timer timer = new Timer();
////        timer.scheduleAtFixedRate(new TimerTask() {
////            @Override
////            public void run() {
////                loadNewData();
////            }
////        },1000,2000);
//
//
//
//
//
//        //  retrieveAllPosts("");
//
//
//
//
//
//    }
//
//    //understand this function/method
//
//    private void retrieveAllPosts(String profileUsername, String lastPostId, String lastContestId, final String firstPostId, final String firstContestId,String lastBoostedId){
//        RetrieveAllPosts.retrievePost(mPrefer.getUsername(),profileUsername,lastPostId,lastContestId,firstPostId,firstContestId,lastBoostedId);
//
//
//        RetrieveAllPosts.setDataListener(new RetrieveAllPosts.RetrieveAllPostsDataListener() {
//
//            @Override
//            public void onRetrieveSinglePostData(PostData data, int id) {
//
//
//
//                if(!simplePostIdsLl.contains(id+"")) {
//                    simplePostIdsLl += ""+id;
//                    if(firstContestId.isEmpty() && firstPostId.isEmpty()) {
//                        ArrayHolder.homePostData.add(data);
//                    }else {
//                        ArrayHolder.homePostData.add(0, data);
//                    }
//
//                }else{
//                    Log.e("HomeActivity", "onRetrieveSinglePostData: already contain data" );
//                }
//
//
//
//            }
//
//            @Override
//            public void hidePb() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipe.setRefreshing(false);
//                        homeRvPb.setVisibility(View.GONE);
//                    }
//                });
//            }
//
//            @Override
//            public void updateView() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        rv.notifyDataSetChanged();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onEnd(PostData data) {
//                //end reached mean no further data is found
//
//                if(ArrayHolder.homePostData.size() > 0) {
//                    PostData postData = ArrayHolder.homePostData.getLast();
//                    if (!(postData instanceof SimpleTvData))
//                        ArrayHolder.homePostData.add(data);
//                }else{
//                    ArrayHolder.homePostData.add(data);
//                }
//
//            }
//
//            @Override
//            public void onRetrieveOnlyIds(ArrayList<Integer> contestIds, ArrayList<Integer> postIds) {
//                if(firstPostId.isEmpty() && firstContestId.isEmpty()){
//                    containOnlyHomeContestId.addAll(contestIds);
//                    containOnlyHomeSimplePostId.addAll(postIds);
//                }else{
//                    containOnlyHomeContestId.addAll(0,contestIds);
//                    containOnlyHomeSimplePostId.addAll(0,postIds);
//                }
//            }
//
//
//
//
//
//            @Override
//            public void boostedId(ArrayList<Integer> boostedIds) {
//                boostedIdsHome.addAll(boostedIds);
//            }
//
//            @Override
//            public void onRetrieveContestData(PostData data,int postId,int contestId) {
//
//
//                if(!simplePostIdsLl.contains(postId+"")) {
//                    simplePostIdsLl += ""+postId;
//
//                    if(firstContestId.isEmpty() && firstPostId.isEmpty()) {
//                        ArrayHolder.homePostData.add(data);
//                    }else {
//                        ArrayHolder.homePostData.add(0, data);
//                    }
//
//
//
//                }else{
//                    Log.e("HomeActivity", "onRetrieveSinglePostData: already contain data" );
//                }
//
//
//
//            }
//
//            @Override
//            public void onFail() {
//
//            }
//
//
//        });
//
//    }
//
//
//    private void createNewPost() {
//        startActivity(new Intent(getBaseContext(), SelectContestActivity.class));
//
//        Animatoo.animateSlideLeft(HomeActivity.this);
//
//    }
//
//
//    private void rvListener(){
//        HomeRvImplementation implementation = new HomeRvImplementation(HomeActivity.this,mPrefer.getUsername());
//        implementation.setListener(new HomeRvImplementation.SuccessHomeRvImplementationListener() {
//            @Override
//            public void Successful(int position) {
//                rv.notifyItemChanged(position);
//            }
//
//            @Override
//            public void loadingMore(int position) {
//                // if(containOnlyHomeSimplePostId.isEmpty()) {
//                //       Toaster.setToaster(getBaseContext(),"loading more");
//                // if (containOnlyHomeContestId.get(containOnlyHomeContestId.size() - 1) > -1 && containOnlyHomeSimplePostId.get(containOnlyHomeSimplePostId.size() - 1) > -1)
//                PostData data = ArrayHolder.homePostData.get(ArrayHolder.homePostData.size()-1);
//                if(!(data instanceof SimpleTvData)) {
//                    if(data instanceof SimplePostData) {
//                        SimplePostData simplePostData = (SimplePostData) data;
//                        if (simplePostData.getIsPostBoosted() != PostView.IS_POST_BOOSTED) {
//                            //mean when last post is boosted then not load more posts .and its not possible
//                            //and when possible then more data is not found in server hehe
//
//                            setLoadMore();
//                        }
//                    }else{
//                        setLoadMore();
//                    }
//
//                    setLoadMore();
//                }
//
//
//
//            }
//
//
//
//            @Override
//            public void remove(int position) {
//                ArrayHolder.homePostData.remove(position);
//                mRecyclerView.removeViewAt(position);
//                rv.notifyItemRemoved(position);
//            }
//        });
//        rv.setHomeRvListener(implementation);
//    }
//
//    private void setLoadMore() {
//
//        if(!containOnlyHomeSimplePostId.isEmpty() && !containOnlyHomeContestId.isEmpty()) {
//
//            if(boostedIdsHome.isEmpty()) {
//                //we need last id because we retrieve data descending order so last id needed to retrieve old data
//                int lastPostId = containOnlyHomeSimplePostId.get(containOnlyHomeSimplePostId.size() - 1);
//                int lastContestId = containOnlyHomeContestId.get(containOnlyHomeContestId.size() - 1);
//                retrieveAllPosts("", lastPostId + "", lastContestId + "", "", "","");
//            }else{
//
//                int lastPostId = containOnlyHomeSimplePostId.get(containOnlyHomeSimplePostId.size() - 1);
//                int lastContestId = containOnlyHomeContestId.get(containOnlyHomeContestId.size() - 1);
//                int lastBoostId = boostedIdsHome.get(boostedIdsHome.size()-1);
//                retrieveAllPosts("", lastPostId + "", lastContestId + "", "", "",lastBoostId+"");
//
//            }
//        }else if(!containOnlyHomeSimplePostId.isEmpty() && containOnlyHomeContestId.isEmpty()){
//            if(boostedIdsHome.isEmpty()) {
//                int lastPostId = containOnlyHomeSimplePostId.get(containOnlyHomeSimplePostId.size() - 1);
//                retrieveAllPosts("", lastPostId + "", "", "", "", "");
//            }else{
//                int lastPostId = containOnlyHomeSimplePostId.get(containOnlyHomeSimplePostId.size() - 1);
//                int lastBoostId = boostedIdsHome.get(boostedIdsHome.size() - 1);
//                retrieveAllPosts("", lastPostId + "", "", "", "", lastBoostId+"");
//
//            }
//        }else if(containOnlyHomeSimplePostId.isEmpty() && !containOnlyHomeContestId.isEmpty()){
//            if(boostedIdsHome.isEmpty()) {
//
//                int lastContestId = containOnlyHomeContestId.get(containOnlyHomeContestId.size() - 1);
//                retrieveAllPosts("", "", lastContestId + "", "", "", "");
//            }else{
//                int lastContestId = containOnlyHomeContestId.get(containOnlyHomeContestId.size() - 1);
//                int lastBoostId = boostedIdsHome.get(boostedIdsHome.size() - 1);
//                retrieveAllPosts("", "", lastContestId + "", "", "", ""+lastBoostId);
//
//            }
//        }
//
//    }
//
//    private void loadNewData() {
//
//        if(!containOnlyHomeSimplePostId.isEmpty() && !containOnlyHomeContestId.isEmpty()) {
//
//            int firstPostId = containOnlyHomeSimplePostId.get(0);
//            //first id is get because in server side we get data descending order so in
//            //order to get more data we need first id and then move down
//            int firstContestId = containOnlyHomeContestId.get(0);
//
//
//            retrieveAllPosts("","","",firstPostId+"",firstContestId+"","");
//
//        }else if(!containOnlyHomeSimplePostId.isEmpty() && containOnlyHomeContestId.isEmpty()){
//
//            int firstPostId = containOnlyHomeSimplePostId.get(0);
//            retrieveAllPosts("","","",""+firstPostId,"","");
//
//        }else if(containOnlyHomeSimplePostId.isEmpty() && !containOnlyHomeContestId.isEmpty()){
//
//            int firstContest = containOnlyHomeContestId.get(0);
//            retrieveAllPosts("","","","",""+firstContest,"");
//
//        }
//
//    }
//
//
//    private void setSearchProcess(){
////        searchEt.setCursorVisible(false);
////        searchActivityToolbar.setFocusableInTouchMode(true);
////        searchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////            @Override
////            public void onFocusChange(View v, boolean hasFocus) {
////                if(hasFocus){
////                    startActivity(new Intent(getBaseContext(),SearchActivity.class));
////                }
////            }
////        });
//
//
//
//
////        searchEt.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(HomeActivity.INPUT_METHOD_SERVICE);
////                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
////                startActivity(new Intent(getBaseContext(),SearchActivity.class));
////            }
////        });
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    private class BackgroundAsync extends AsyncTask<Void,Void,Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            retrieveAllPosts("","","","","","");
//
//
//            return null;
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//    private class MyCountDownTimer extends CountDownTimer{
//
//
//        /**
//         * @param millisInFuture    The number of millis in the future from the call
//         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//         *                          is called.
//         * @param countDownInterval The interval along the way to receive
//         *                          {@link #onTick(long)} callbacks.
//         */
//        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            countDownTimerIsRunning = true;
//        }
//
//        @Override
//        public void onFinish() {
//            countDownTimerIsRunning = false;
//            //retrieve here new data
//
//            loadNewData();
//            start();
//        }
//    }
//
//    @Override
//    protected int setLayout() {
//        return R.layout.activity_home;
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        mTimer.cancel();
//        mTimer.start();
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Menu.hideMenu();
//    }
//
//
//    @Override
//    protected void onPause() {
//
//        mTimer.cancel();
//        super.onPause();
//
//    }
//
//    @Override
//    protected void onStop() {
//        mTimer.cancel();
//        super.onStop();
//
//    }
//
//    @Override
//    protected void notifyItemSelected(int position) {
//        if(mRecyclerView != null && !ArrayHolder.homePostData.isEmpty()){
//            ArrayHolder.homePostData.clear();
//            rv.notifyDataSetChanged();
//            mRecyclerView.getRecycledViewPool().clear();
//        }
//    }
}
