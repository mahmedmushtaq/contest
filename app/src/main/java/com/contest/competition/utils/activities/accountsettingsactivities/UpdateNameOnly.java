package com.contest.competition.utils.activities.accountsettingsactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.contest.competition.R;
import com.contest.competition.requests.data.updatereq.UpdateNameOnlyReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Toaster;

public class UpdateNameOnly extends BaseToolbarActivity {

   private EditText updateName;
   private Button update;

   private LoginSharedPrefer mLoginSharedPrefer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateName = findViewById(R.id.new_name_update_activity);

        update = findViewById(R.id.update_name_btn);
        mLoginSharedPrefer = new LoginSharedPrefer(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateNameMethod();


            }
        });

    }

    private void updateNameMethod() {
        if(updateName.getText().toString().length() > 4){
            UpdateNameOnlyReq.updateData(updateName.getText().toString(),mLoginSharedPrefer.getUsername(),this);
        }else{
            Toaster.setToaster(this,"Please write your full name");
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_update_name_only;
    }

    @Override
    protected String setActivityName() {
        return "Update Name Only";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }
}
