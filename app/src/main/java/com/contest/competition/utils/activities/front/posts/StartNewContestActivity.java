package com.contest.competition.utils.activities.front.posts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;


import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Network;
import com.contest.competition.classes.SelectCropImageFromGallery;
import com.contest.competition.classes.models.FollowerActivityCheck;
import com.contest.competition.requests.data.contestreq.SendNewContestReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.activities.front.HomeActivity;
import com.contest.competition.utils.activities.profileactivities.FollowerActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class StartNewContestActivity extends BaseToolbarActivity {

    private ImageView selectedPic,refresh;
    private Spinner tillTimeSpinner;
    private Button selectUser,sendCompetition;
    private TextView selectedUsername;
    private String selectedUsername_Name,selected_username,selectTimeString;
    private LoginSharedPrefer mPrefer;

    private File mSelectedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectImage();
        selectedPic = findViewById(R.id.startNewContest_selectedPic);
        setTillTimeSpinner();
        selectUser = findViewById(R.id.startNewContest_selectBtn);
        selectedUsername = findViewById(R.id.startNewContest_selectedUsername);
        sendCompetition = findViewById(R.id.startNewContest_sendCompetition);
        refresh = findViewById(R.id.refresh_startNewContestActivity);

        mPrefer = new LoginSharedPrefer(this);
        selectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectUser();
            }
        });
        sendCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSendCompetition();
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });




    }

    @Override
    protected int setLayout() {
        return R.layout.activity_start_new_contest;
    }

    @Override
    protected String setActivityName() {
        return "New contest";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }

    private void setSendCompetition() {
        if(!selectedUsername.getText().toString().isEmpty() && selectTimeString != null && mSelectedImageFile != null){
           createContest();
        }else{
            Toaster.setToaster(getBaseContext(),"Contest cannot be created because you have not selected user or time limit or pic");

        }
    }

    private void createContest() {
        if(Network.isAvailable(this)) {
            Dialoger.setDialog(StartNewContestActivity.this, "Contest", "Please wait your contest is sending to other selected user");
            SendNewContestReq.setContestListener(new SendNewContestReq.contestListener() {
                @Override
                public void onSuccessNewContest(String body) {
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                    finish();
                }


                @Override
                public void onFailNewContest() {

                }


            });
            SendNewContestReq.createNewContest(StartNewContestActivity.this, mPrefer.getUsername(), mPrefer.getUsername(), selected_username, selectTimeString, mSelectedImageFile);
        }else Toaster.setToaster(getBaseContext(),"Network is not available");
    }

    private void setSelectUser(){
        selectedUsername.setText("");
        selectedUsername_Name = "";
        selected_username = "";
        Intent intent = new Intent(getBaseContext(), FollowerActivity.class);
        FollowerActivityCheck check = new FollowerActivityCheck("select user for contest",mPrefer.getUsername());
        intent.putExtra(KeyStore.PASS_OBJECT,check);
        startActivityForResult(intent,KeyStore.RETURN_SELECT_USER_ACTIVITY_RESULT);

        Animatoo.animateSlideUp(StartNewContestActivity.this);
    }




    private void setTillTimeSpinner(){
        tillTimeSpinner = findViewById( R.id.startNewContest_selectTillTime);
        List<String> tillTimeEnd = new ArrayList<>();
        tillTimeEnd.add("One day");
        tillTimeEnd.add("Two day");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,tillTimeEnd);
        dataAdapter.setDropDownViewResource( R.layout.simple_rv_layout);
        tillTimeSpinner.setAdapter(dataAdapter);

        tillTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#34495e"));
                selectTimeString = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void selectImage(){
        SelectCropImageFromGallery.selectImage(StartNewContestActivity.this,true);
        SelectCropImageFromGallery.setFileSelected(new SelectCropImageFromGallery.fileSelected() {
            @Override
            public void onSelected(Bitmap selectedImage, File selectedImageFile) {
             selectedPic.setImageBitmap(selectedImage);
             mSelectedImageFile = selectedImageFile;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SelectCropImageFromGallery.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == KeyStore.RETURN_SELECT_USER_ACTIVITY_RESULT){
            String selectUsernameResultedString = data.getStringExtra(KeyStore.RETURN_RESULT);
            String resultName = data.getStringExtra("return name");
            if(!selectUsernameResultedString.isEmpty() && !resultName.isEmpty()){
               selectedUsername.setText(selectUsernameResultedString);
               selectedUsername_Name = resultName;
               selected_username = selectUsernameResultedString;
            }
        }
    }


}
