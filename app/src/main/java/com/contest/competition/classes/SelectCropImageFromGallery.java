package com.contest.competition.classes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by M Ahmed Mushtaq on 7/18/2018.
 */

public class SelectCropImageFromGallery {

    private static Activity mActivity;
    private static fileSelected mFileSelected;

    public interface fileSelected{
        void onSelected(Bitmap selectedImage,File selectedImageFile);
    }

    public static void setFileSelected(fileSelected fileSelected){
        mFileSelected = fileSelected;

    }


    public static void selectImage(Activity activity,boolean aspectRation){
        CropImage.activity()
                .setFixAspectRatio(aspectRation)
                .start(activity);

        mActivity  = activity;
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                handleCrop(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private static void handleCrop(Uri resultUri){
        Converter converter = new Converter();
        final Bitmap bitmap =  converter.getBitmapFromUri(mActivity,resultUri);
        converter.convertBitmapIntofile(bitmap,mActivity);
        converter.setFileCallBackListener(new Converter.FileCompletion() {
            @Override
            public void fileCallBack(File file) {
                if(mFileSelected != null){
                    mFileSelected.onSelected(bitmap,file);
                }
            }
        });
    }
}
