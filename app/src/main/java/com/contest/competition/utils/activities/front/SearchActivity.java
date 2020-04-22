package com.contest.competition.utils.activities.front;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.contest.competition.R;

import com.contest.competition.adapters.SearchRv;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.VisitProfile;
import com.contest.competition.classes.interfaces.SearchRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SearchData;
import com.contest.competition.requests.data.SearchProcess;
import com.contest.competition.requests.data.followfeature.StartFollowing;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseActivity;
import com.contest.competition.utils.views.FollowBtnStyle;
import com.contest.competition.utils.views.LinearDividerItemDecoration;
import com.contest.competition.utils.views.Toaster;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends BaseActivity {

  //  private Toolbar mToolbar;
   // private EditText searchUsers;
  //  private ImageView search_iv;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SearchRv mRv;

    private LoginSharedPrefer mPrefer;
    private SearchProcess mSearchProcess;
    private MaterialSearchBar mSearchBar;
    private int lastRemainingId = 0;
    private ArrayHolder mArrayHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   ArrayHolder.searchData.clear();



      //findViewById(R.id.searchActivity_toolbar);

       // searchUsers = findViewById(R.id.search_Et);
     //   search_iv = findViewById(R.id.search_iv);
        mSearchBar = findViewById(R.id.searchBar);
        mRecyclerView = findViewById(R.id.searchActivity_rv);
        mProgressBar = findViewById(R.id.search_progressBar);


        mPrefer = new LoginSharedPrefer(this);





//        if(getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setTitle(null);
//        }





       setRecyclerView();









         mSearchProcess = new SearchProcess();
         mSearchProcess.setListener(new SearchProcess.SearchProcessListener() {
            @Override
            public void onSuccess(final ArrayHolder arrayHolder) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mArrayHolder = arrayHolder;
                        mRv.setArrayHolder(mArrayHolder);
                        mProgressBar.setVisibility(View.GONE);
                          mRv.notifyDataSetChanged();

                    }
                });





            }

            @Override
            public void onFailure() {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      mProgressBar.setVisibility(View.GONE);

                  }
              });

            }

            @Override
            public void noResultFoundError(final String reason) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        Toaster.setToaster(getBaseContext(),reason);
                    }
                });
            }
        });



         mSearchBar.addTextChangeListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(mArrayHolder != null)
                    mArrayHolder.getSearchDataArrayList().clear();
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         });

         mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
             @Override
             public void onSearchStateChanged(boolean enabled) {

             }

             @Override
             public void onSearchConfirmed(CharSequence text) {
                 if (!text.toString().isEmpty()) {
                     mProgressBar.setVisibility(View.VISIBLE);
                     mSearchProcess.searchUser(SearchActivity.this, text.toString(), mPrefer.getUsername(), "");
                 }
             }

             @Override
             public void onButtonClicked(int buttonCode) {

             }
         });



//        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Do some magic
//
//              mProgressBar.setVisibility(View.VISIBLE);
//              mSearchProcess.searchUser(SearchActivity.this,query,mPrefer.getUsername(),"");
////
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayHolder.searchData.clear();
//                return false;
//            }
//        });



//        search_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayHolder.searchData.clear();
//                if(searchUsers.getText().length() != 0) {
//                    // submit sign up form
//                    mProgressBar.setVisibility(View.VISIBLE);
//                    mSearchProcess.searchUser(SearchActivity.this,searchUsers.getText().toString(),mPrefer.getUsername(),"");
//
//                }
//
//            }
//        });
//
//        searchUsers.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId== EditorInfo.IME_ACTION_SEARCH){
//                    ArrayHolder.searchData.clear();
//                    if(searchUsers.getText().length() != 0) {
//                        mProgressBar.setVisibility(View.VISIBLE);
//                        mSearchProcess.searchUser(SearchActivity.this,searchUsers.getText().toString(),mPrefer.getUsername(),"");
//                    }
//                }
//                return false;
//            }
//        });



//        searchUsers.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length() == 0) ArrayHolder.searchData.clear();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_search;
    }


    private void setRecyclerView(){
        mRv = new SearchRv();
        mRv.setArrayHolder(mArrayHolder);

        LinearLayoutManager manager = new LinearLayoutManager(this);


        mRecyclerView.setLayoutManager(manager);


        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(this,R.color.lightSilver,1,R.id.search_profilePic));

        mRecyclerView.setAdapter(mRv);


        mRv.setListener(new SearchRvListener() {
            @Override
            public void onAddFollowListener(SearchData data, Button followBtn) {
                startFollowing(data,followBtn);
            }

            @Override
            public void viewProfileListener(SearchData data) {
                //Toaster.setToaster(getBaseContext(),data.getName());
//                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
//                intent.putExtra(KeyStore.PASS_DATA,data.getUsername());
//
//                startActivity(intent);
                VisitProfile.with(SearchActivity.this).visitUserProfile(data.getUsername());
            }



            @Override
            public void onViewEndRvItem(SearchData data,int position) {
                    if(data.getId() != lastRemainingId) {
                         if(position > 9) {
                             Toaster.setToaster(getBaseContext(), "Loading more");
                             mSearchProcess.searchUser(SearchActivity.this, data.getPreviousSearchKeyWord(), mPrefer.getUsername(), data.getId() + "");
                         }
                        lastRemainingId = data.getId();
                    }
            }
        });


    }


    private void startFollowing(SearchData data, final Button followBtn){

        FollowBtnStyle.setFollowedBtn(followBtn,this);
        StartFollowing following = new StartFollowing();

        following.setStartFollowingProcessListener(new StartFollowing.addFollowListener() {
            @Override
            public void onSuccess(final JSONObject object) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           String result = object.getString("result");
                           String reason = object.getString("reason");


                           if(Validator.validateWebResult(result)){

                           }else if(result.contains("wait")){

                               FollowBtnStyle.setRequestedBtn(followBtn,SearchActivity.this);

                               //here send request again to


                           }else{
                               Toaster.setToaster(getBaseContext(),reason);
                               FollowBtnStyle.setFollowBtn(followBtn,SearchActivity.this);
                           }


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               });

            }

            @Override
            public void onFailure() {

            }
        });

        Log.e("id are ", "startFollowing: "+data.getId() );

        following.startFollowing(data.getUsername(),mPrefer.getUsername(),"check");
    }


    @Override
    protected void notifyItemSelected(int position) {
        if(mRecyclerView != null && mArrayHolder != null){
            mArrayHolder.getSearchDataArrayList().clear();
            mRv.notifyDataSetChanged();
            mRecyclerView.getRecycledViewPool().clear();
        }
    }



}
