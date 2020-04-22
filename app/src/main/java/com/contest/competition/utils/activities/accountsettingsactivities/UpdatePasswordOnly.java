package com.contest.competition.utils.activities.accountsettingsactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.contest.competition.R;
import com.contest.competition.requests.data.updatereq.UpdatePasswordOnlyReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Toaster;

public class UpdatePasswordOnly extends BaseToolbarActivity {


    private EditText updatePassword;
    private Button update;

    private LoginSharedPrefer mLoginSharedPrefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        updatePassword = findViewById(R.id.new_password_update_activity);

        update = findViewById(R.id.update_new_password_btn);
        mLoginSharedPrefer = new LoginSharedPrefer(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePasswordMethod();



            }


        });



    }

    private void updatePasswordMethod(){
        if(updatePassword.getText().toString().length() <=4 || updatePassword.getText().toString().length() >= 20){
            Toaster.setToaster(this,"Password must be between 4 and 20 character");
        }else{
            UpdatePasswordOnlyReq.updateData(updatePassword.getText().toString(),mLoginSharedPrefer.getUsername(),this);
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_update_password_only;
    }

    @Override
    protected String setActivityName() {
        return "Update password Only";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }
}
