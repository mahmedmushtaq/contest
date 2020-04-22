package com.contest.competition.utils.activities.launching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import java.util.ArrayList;

import com.contest.competition.classes.interfaces.recyclerviewinterfaces.SimpleRvListener;
import com.contest.competition.utils.views.Dialoger;

public class StartTutorialActivity extends AppCompatActivity {

    private ArrayList<String> tutorialTextData = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tutorial);
        mRecyclerView = findViewById(R.id.tutorial_rv);
        TutorialRvAdapter adapter = new TutorialRvAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);

        tutorialTextData.clear();
        tutorialTextData.add("Sign Up Tutorial");//1
        tutorialTextData.add("Log In Tutorial");//2
        tutorialTextData.add("Forgot Password Tutorial");//3
        tutorialTextData.add("New Post Tutorial");//4
        tutorialTextData.add("Create New Contest Tutorial");//5
        tutorialTextData.add("Update Profile Tutorial");//6

        tutorialTextData.add("Boost Post Tutorial");//7
        tutorialTextData.add("Earn credits Tutorial");//8

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,manager.getOrientation()));
        mRecyclerView.setAdapter(adapter);

        adapter.setSimpleRvListener(new SimpleRvListener() {
            @Override
            public void onClick(int position) {
                doRestProgramme(position);
            }
        });








    }

    private void doRestProgramme(int position) {
        if(position == 0){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"Sign up tutorial","1-Open a app and wait few second\n2-It redirect you next page , Here sign up button located" +
                    "\n3- Click this btn and then follow few sign up set ups " +
                    "\n4- Afterward a confirmation code send to your email and confirm your email then your account " +
                    "successfully created .Hope You Enjoy this");
        }else if(position == 1){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"Log in tutorial","1- Open a app and wait few second\n2- It redirect you next page , Here Login button located " +
                    "\n3- Click this and enter your login detail when it send you next page");
        }else if(position == 2){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"Forgot password tutorial","At login page , forgot password btn is located click it and recover your password");
        }else if(position == 3){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"New post tutorial","1- After successfully login ,then at home page floating btn is present at bottom click it" +
                    "\n2- After clicking on it , its further two types 1-\"Create Simple Post \" 2-\" Create New Contest\" " +
                    "\n3- Click on Create Simple Post and then app redirect you on create simple post page");
        }else if(position == 4){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"New contest tutorial","1- After successfully login ,then at home page floating btn is present at bottom click it" +
                    "\n2- After clicking on it , its further two types 1-\"Create Simple Post \" 2-\" Create New Contest\" " +
                    "\n3- Click on Create contest  and then app redirect you on create contest page");
        }else if(position == 5){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"Update profile tutorial","At profile page,press on default profile image for a few seconds continuously then alert is appear ");

        }else if(position == 6){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"Boost post tutorial","After uploading , you are automatically redirected to home page where your latest uploaded post shown and below your post click boost btn ");

        }else if(position == 7){
            Dialoger.setOnlyPositiveBtnDialog(StartTutorialActivity.this,"Earn credit tutorial","Earn credit by \n" +
                    "1- By creating contest with your followers more and more.It is because when you win you will earn 10 credits and when you tie you can earn 5 credits" +
                    "\n2- You can also buy credits " +
                    "\n2-a- Go to profile page and click on credits then it goes to you new page where you watch video to earn coins ");

        }
    }

    private   class TutorialRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

         private SimpleRvListener mSimpleRvListener;
         public void setSimpleRvListener(SimpleRvListener listener){
             mSimpleRvListener = listener;
         }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TutorialHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder recylerViewHolder, final int position) {
             TutorialHolder holder = (TutorialHolder) recylerViewHolder;
             holder.simpleRvText.setText(tutorialTextData.get(position));
             holder.simpleRvText.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(mSimpleRvListener != null)
                         mSimpleRvListener.onClick(position);
                 }
             });
        }

        @Override
        public int getItemCount() {
            return tutorialTextData.size();
        }

        private   class TutorialHolder extends RecyclerView.ViewHolder{
            TextView simpleRvText;
            public TutorialHolder(View v) {
                super(v);
                simpleRvText = v.findViewById(R.id.simple_rv_text);

            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideUp(StartTutorialActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Animatoo.animateSlideUp(StartTutorialActivity.this);
    }
}
