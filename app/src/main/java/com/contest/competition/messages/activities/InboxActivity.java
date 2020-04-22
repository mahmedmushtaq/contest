package com.contest.competition.messages.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.SimpleDateTimeClass;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.VisitProfile;
import com.contest.competition.classes.models.ProfileHeaderData;
import com.contest.competition.classes.models.messagesModels.ConversationData;
import com.contest.competition.requests.data.messagesreq.RetrieveInboxReq;
import com.contest.competition.requests.data.messagesreq.SendMessageReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Random;

public class InboxActivity extends AppCompatActivity {
    private ProfileHeaderData mProfileHeaderData;
    private LoginSharedPrefer mPrefer;
    private ImageView visitProfile;
    private String subscription,fromTopic;
    private ConversationData mConversationData;
    private TextView usernameTv;
    private EditText messageBody;
    private String fromUsername,toUsername;
    private ImageView sendMessageBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);


         mConversationData = (ConversationData) getIntent().getExtras().getSerializable(KeyStore.PASS_OBJECT);
         if(mConversationData != null) {


             getConversationData();
         }

         mPrefer = new LoginSharedPrefer(this);

         visitProfile = findViewById(R.id.inbox_activity_visit_profile);
         usernameTv = findViewById(R.id.inbox_activity_username);
         messageBody = findViewById(R.id.inbox_emojiEt);

         usernameTv.setText(toUsername);

         sendMessageBtn = findViewById(R.id.inbox_sendMessage);
         sendMessageBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 send();
             }
         });


         fromUsername = mPrefer.getUsername();
         fromTopic = mPrefer.getSubscriptionTopic();


        RetrieveInboxReq.retrieve(mPrefer.getUsername(),mConversationData.getConversationId()+"","");


       // Log.e("inboxActivity", "onCreate: own subscripe topic = "+mPrefer.getSubscriptionTopic()+" other user topic = "+mProfileHeaderData.getSubscriptionTopic()+" and othr value = "+mProfileHeaderData.getUsername());




    }

    private void getConversationData(){
        toUsername = mConversationData.getToUsername();
        subscription = mConversationData.getToSubscription();

    }

    public void visitProfile(View view) {
        VisitProfile.with(this).visitUserProfile(toUsername);
    }

    public void backBtn(View view) {
        finish();
        Animatoo.animateSlideRight(this);
    }

    private void send() {
        if(messageBody.getText().toString().length() > 1){
            sendMessage();
        }else{
            Toaster.setToaster(this,"Please write something");
        }

    }

    private void sendMessage() {
        JSONObject object = new JSONObject();
        Random random = new Random();
        int value = random.nextInt(999999999-1000)+1000;
        try {
            object.put("from_username",fromUsername);
            object.put("to_username",toUsername);
            object.put("secret_key",value);//this is the unique identifiction of messages
            object.put("message_body",messageBody.getText().toString());
            object.put("date_time", SimpleDateTimeClass.getSimpleTime());
            String jsonValue = object.toString();
            SendMessageReq.sendMessageReq(fromUsername,toUsername,subscription,value,jsonValue,fromTopic);
          //  Log.e("date_time", "sendMessage: date time = "+SimpleDateTimeClass.getSimpleTime());
            messageBody.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this);
    }
}
