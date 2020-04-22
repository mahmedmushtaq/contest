package com.contest.competition.utils.activities.profileactivities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.adapters.rv.EditProfileRv;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.ShareApp;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.RecyclerViewItemListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.storage.sharedpreferences.NoOfNotifications;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.activities.accountsettingsactivities.FollowerSettingsActivity;
import com.contest.competition.utils.activities.accountsettingsactivities.UpdateAccountActivity;
import com.contest.competition.utils.activities.launching.FrontActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Menu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;


public class EditProfileActivity extends BaseToolbarActivity {
     private RecyclerView mRecyclerView;
     private EditProfileRv rv;
     private int UPDATE_ACCOUNT = 0;
     private int FOLLOWER_SETTINGS_ACTIVITY = 1;
     private int LOGOUT = 2;
     private GoogleSignInClient mGoogleSignInAccount;
     private ArrayHolder mArrayHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mRecyclerView = findViewById(R.id.editProfile_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        mArrayHolder = new ArrayHolder();

        setEditData();
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,manager.getOrientation()));
        rv = new EditProfileRv();
        rv.setArrayHolder(mArrayHolder);
        rv.setRecyclerViewItemListener(new RecyclerViewItemListener() {
            @Override
            public void onItemClickListener(int position) {
                checker(position);
            }
        });

        mRecyclerView.setAdapter(rv);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();



        mGoogleSignInAccount = GoogleSignIn.getClient(this, gso);




    }

    private void setEditData(){
        mArrayHolder.getEditProfileData().clear();
        mArrayHolder.getEditProfileData().add("Update account");
        mArrayHolder.getEditProfileData().add("Follower settings");
        mArrayHolder.getEditProfileData().add("Share app");

        mArrayHolder.getEditProfileData().add("logout");

    }

    private void setAfterLogoutEmptyAllArrayData(){
       // ArrayHolder.searchData.clear();
        //ArrayHolder.settingData.clear();
        //ArrayHolder.settingHeaderData.clear();
       // ArrayHolder.notificationData.clear();

        //ArrayHolder.profileHeaderData.clear();
//        ArrayHolder.followingData.clear();
//        ArrayHolder.followerData.clear();
//        ArrayHolder.editProfileData.clear();

       // ArrayHolder.commentData.clear();
    }

    private void checker(int position){
       if(position == 0){
        startActivity(new Intent(getBaseContext(), UpdateAccountActivity.class));
       }else if(position == 1){
         startActivity(new Intent(getBaseContext(), FollowerSettingsActivity.class));
       } else if(position == 2){
         shareApp(position);
         }else if(position == 3){
           logout();
       }
    }

    private void shareApp(int position){
        List<PowerMenuItem> items = new ArrayList<>();
        items.add(new PowerMenuItem("Share apk",false));
        items.add(new PowerMenuItem("Share app link",false));
        View view = mRecyclerView.findViewHolderForAdapterPosition(position).itemView;
        Menu.createMenu(this,items,view,Menu.SHOWN_AS_DROP_DOWN);
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                if(item.title.equals("Share apk")){
                    ShareApp.shareApplication(EditProfileActivity.this);
                }else{
                    ShareApp.shareAppLink(EditProfileActivity.this);
                }
            }
        });

    }


    private void logout(){
        final LoginSharedPrefer sharedPrefer = new LoginSharedPrefer(this);
        final NoOfNotifications no = new NoOfNotifications(this);

        Dialoger.setAlerter(EditProfileActivity.this,"Logout","Please wait we are signing out you");
        setAfterLogoutEmptyAllArrayData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialoger.dismissDialog();


                if(sharedPrefer.getRememberMe()){
                    sharedPrefer.removeSharedPreference();
                    no.removeNotifications();

                }else{

                    sharedPrefer.removeEmailAndPassword();
                    sharedPrefer.removeSharedPreference();
                    no.removeNotifications();
                    FirebaseAuth.getInstance().signOut();
                    mGoogleSignInAccount.signOut();


                }


                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(KeyStore.NOTIFICATION_ID);
                Intent intent = new Intent(getBaseContext(), FrontActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        },1000);

    }


    @Override
    protected int setLayout() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected String setActivityName() {
        return "Edit profile";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this);
    }
}
