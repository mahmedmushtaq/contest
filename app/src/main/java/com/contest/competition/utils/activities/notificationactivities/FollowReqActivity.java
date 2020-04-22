package com.contest.competition.utils.activities.notificationactivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.requests.data.followfeature.AcceptFollowReq;
import com.contest.competition.requests.data.followfeature.CancelFollowing;
import com.contest.competition.requests.data.followfeature.CheckFollowingReq;
import com.contest.competition.requests.data.followfeature.StartFollowing;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

import com.contest.competition.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class FollowReqActivity extends BaseToolbarActivity {
    private NotificationData mData;
    private Button accept,cancel;
    private CircularImageView profile;
    private TextView  text;
    private CancelFollowing mCancelFollowing;
    private StartFollowing mStartFollowing;
    private LinearLayout container;
    private String mId ,mCheck;
    private CheckFollowingReq mCheckFollowingReq;
    private LoginSharedPrefer mPrefer;

    /*int is not equal to null But Integer is equal to null when not assign to its values*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentValue();


        accept = findViewById(R.id.not_followReq_acceptBtn);
        cancel = findViewById(R.id.not_followReq_cancelBtn);
        profile = findViewById(R.id.not_followReq_profile);
        text = findViewById(R.id.not_followReq_tv);
        container = findViewById(R.id.not_followReq_Ll);
        mPrefer = new LoginSharedPrefer(this);

        NotificationsReq.notificationOpened(mData.getId(),mPrefer.getUsername());

        container.setVisibility(View.GONE);

        mCancelFollowing = new CancelFollowing();
        mStartFollowing = new StartFollowing();
        mCheckFollowingReq = new CheckFollowingReq();

        String[] splitter = mData.getLink().split("=");
        mId = splitter[1];

       checkData();

       Glide.with(this).load(Addresses.getWebAddress()+mData.getProfilePics()).into(profile);
       text.setText(HighLighter.threeThingsSpanner(mData.getMessage(),mData.getName(),mData.getDateTime(),this));

       accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setAccept();
           }
       });
       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                setCancel();
           }
       });

       mCancelFollowing.setCancelFollowingListener(new CancelFollowing.cancelFollowingListener() {
           @Override
           public void onSuccess(String body) {
               try {
                   JSONObject object = new JSONObject(body);
                   final String reason = object.getString("reason");
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Dialoger.dismissDialog();
                           Toaster.setToaster(getBaseContext(),reason);
                       }
                   });
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onFail() {

           }
       });

    }

    private void setCancel(){
        if(mCheck != null) {
            Dialoger.setDialog(FollowReqActivity.this, "Follow Req", "Please wait process is doing");
            mCancelFollowing.cancelFollowingReq(mPrefer.getUsername(), mData.getUsername());
            container.setVisibility(View.GONE);
        }
    }

    private void setAccept(){
        if(mCheck != null){
            Dialoger.setDialog(FollowReqActivity.this,"Follow Req","Please wait process is doing");
            AcceptFollowReq.acceptReq(FollowReqActivity.this,mId);
            AcceptFollowReq.setAcceptFollowReq(new AcceptFollowReq.acceptFollowReq() {
                @Override
                public void onSuccess(final String body) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject object = new JSONObject(body);
                                String result = object.getString("result");
                                final String reason = object.getString("reason");
                                Dialoger.dismissDialog();
                                Toaster.setToaster(getBaseContext(),reason);
                                container.setVisibility(View.GONE);
                                mStartFollowing.startFollowing(mPrefer.getUsername(),mData.getUsername(),"");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onFail() {

                }
            });

        }
    }

    private void checkData(){
       mCheckFollowingReq.setCheck(mId);
       mCheckFollowingReq.setCheckFollowingReq(new CheckFollowingReq.FollowReqCheckListener() {
           @Override
           public void onCheck(final String body) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Log.e("retrieve data", "run: "+body );
                           JSONObject object = new JSONObject(body);
                           String result = object.getString("result");
                           String reason = object.getString("reason");
                           if(Validator.validateWebResult(result)){
                               mCheck = "check";
                               container.setVisibility(View.VISIBLE);
                           }else{
                               Toaster.setToaster(getBaseContext(),reason);
                           }


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               });
           }
       });
    }





    @Override
    protected int setLayout() {
        return R.layout.activity_not_follow_req;
    }

    @Override
    protected String setActivityName() {
        return "Follow Request";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }

    private void getIntentValue(){
        mData = getIntent().getParcelableExtra(KeyStore.PASS_OBJECT);
    }
}
