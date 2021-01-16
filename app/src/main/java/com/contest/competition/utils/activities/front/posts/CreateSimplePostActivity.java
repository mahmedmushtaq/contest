package com.contest.competition.utils.activities.front.posts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiEditText;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.contest.competition.R;

import java.io.File;

import com.contest.competition.classes.SelectCropImageFromGallery;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.requests.data.postreq.PostSimplePost;
import com.contest.competition.requests.data.postreq.UploadSimpleStatus;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

public class CreateSimplePostActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView nameTv;
    private EmojiEditText typeTextEt;
    private Button postBtn,selectPhotoBtn;
    private ImageView userProfileIv,selectedIv;
    private LoginSharedPrefer mPrefer;
    private File mSelectedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        setContentView(R.layout.activity_create_simple_post);

        mToolbar = findViewById(R.id.createSimplePost_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.black_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //start coding here
        nameTv = findViewById(R.id.createSimplePost_userNameTv);
        typeTextEt = findViewById(R.id.createSimplePost_et);
        postBtn = findViewById(R.id.createSimplePost_postBtn);
        selectPhotoBtn = findViewById(R.id.createSimplePost_selectPhotoBtn);
        userProfileIv = findViewById(R.id.createSimplePost_userProfile);
        selectedIv = findViewById(R.id.createSimplePost_selectedPhotoIv);

        mPrefer = new LoginSharedPrefer(this);

        if(mPrefer.getName().length() > 16){
            nameTv.setText(mPrefer.getName().substring(0,16)+"..");
        }else{
            nameTv.setText(mPrefer.getName());
        }

        Glide.with(this).load(Addresses.getWebAddress()+mPrefer.getProfilePath()).into(userProfileIv);




        Intent receivingDirectPhotoFromIntent = getIntent();//get direct photo from gallery
        String receivingIntentAction = receivingDirectPhotoFromIntent.getAction();
        if(Intent.ACTION_SEND.equals(receivingIntentAction)){
            handleIntent(receivingDirectPhotoFromIntent);
        }



    }



    private void handleIntent(Intent intent) {
        String type = intent.getType();
        if(type.contains("image")){
            Uri fileUri =  intent.getParcelableExtra (Intent.EXTRA_STREAM);
            selectedIv.setImageURI(fileUri);

        }
    }


    public void setPostBtn(View view){
         if(mSelectedImageFile == null && typeTextEt.getText().toString().isEmpty()){
             Toaster.setToaster(getBaseContext(),"Type text Or select image");
         }else if(mSelectedImageFile != null && typeTextEt.getText().toString().isEmpty()){
             //only image selected
             Dialoger.setDialog(CreateSimplePostActivity.this,"Posting","Please wait..");
             PostSimplePost.simplePost(CreateSimplePostActivity.this,"",mPrefer.getUsername(),mSelectedImageFile);

         }else if(mSelectedImageFile == null && !typeTextEt.getText().toString().isEmpty()){

             //send simple status to server
              UploadSimpleStatus.uploadSimpleStatus(CreateSimplePostActivity.this,typeTextEt.getText().toString(),mPrefer.getUsername());


         }else if(mSelectedImageFile != null && !typeTextEt.getText().toString().isEmpty()){

                 //image and status both selected
                 Dialoger.setDialog(CreateSimplePostActivity.this,"Posting","Please wait..");
                 PostSimplePost.simplePost(CreateSimplePostActivity.this,typeTextEt.getText().toString(),mPrefer.getUsername(),mSelectedImageFile);

         }
    }



  //  private boolean validTextLength(){
    //    return typeTextEt.getText().toString().length() <= 300;
  //  }

    public void setSelectPhotoBtn(View view){
        SelectCropImageFromGallery.selectImage(CreateSimplePostActivity.this,true);
        SelectCropImageFromGallery.setFileSelected(new SelectCropImageFromGallery.fileSelected() {
            @Override
            public void onSelected(Bitmap selectedImage, File selectedImageFile) {
                mSelectedImageFile = selectedImageFile;
                selectedIv.setImageBitmap(selectedImage);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SelectCropImageFromGallery.onActivityResult(requestCode,resultCode,data);
      //  mCropImageFromGallery.onActivityResult(requestCode,resultCode,data);
    }
}
