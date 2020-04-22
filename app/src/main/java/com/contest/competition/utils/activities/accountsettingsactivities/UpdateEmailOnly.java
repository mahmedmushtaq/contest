package com.contest.competition.utils.activities.accountsettingsactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.contest.competition.R;
import com.contest.competition.classes.Validator;
import com.contest.competition.requests.data.updatereq.UpdateEmailOnlyReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Toaster;

public class UpdateEmailOnly extends BaseToolbarActivity {

    private EditText updateEmail;
    private Button update;
    private LoginSharedPrefer mLoginSharedPrefer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateEmail = findViewById(R.id.new_email_update_activity);

        update = findViewById(R.id.update_new_email_btn);
        mLoginSharedPrefer = new LoginSharedPrefer(this);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            updateEmail();


            }
        });



    }

    private void updateEmail() {
        if(Validator.isValidEmail(updateEmail.getText().toString())){
            UpdateEmailOnlyReq.updateData(updateEmail.getText().toString(),mLoginSharedPrefer.getUsername(),this);
        }else{
            Toaster.setToaster(this,"Please write valid email");
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_update_email_only;
    }

    @Override
    protected String setActivityName() {
        return "Update email Only";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }
}
