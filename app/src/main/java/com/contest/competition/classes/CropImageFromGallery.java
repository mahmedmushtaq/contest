package com.contest.competition.classes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by M Ahmed Mushtaq on 6/26/2018.
 */

public class CropImageFromGallery {

    private Activity mActivity;

    //sound cloud crop library

    private onImageSelection mOnImageSelection;

    public interface onImageSelection{
        void onSelected(Intent data,Bitmap selectedImage,File selectedImageFile);
    }

    public void setOnImageSelection(onImageSelection onImageSelection){
        this.mOnImageSelection = onImageSelection;
    }

    public CropImageFromGallery(Activity activity) {
        mActivity = activity;
    }

    public void selectImage() {
        Crop.pickImage(mActivity);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Crop.REQUEST_PICK) {
                Uri source_uri = data.getData();

                Uri destination_uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cropped"));
                Crop.of(source_uri, destination_uri).asSquare().start(mActivity);

                Log.e("result", "onActivityResult: without cropping = " + Crop.getOutput(data));
                //  mCircularImageView.setImageURI(Crop.getOutput(data));

            } else if (requestCode == Crop.REQUEST_CROP) {
                handle_crop(resultCode, data);
            }
        }
    }

    private void handle_crop(int resultCode, Intent data) {
         if(resultCode == Activity.RESULT_OK) {

          //   mOnImageSelection.onSelectedForPost(data);
             converter(data);


        }else if(resultCode ==Crop.RESULT_ERROR) {
            Log.e("result", "Result Error");
        }
    }

    private void converter(final Intent data){
        Converter converter = new Converter();

        final Bitmap bitmap = converter.getBitmapIntoIntent(data, mActivity);

        converter.convertBitmapIntofile(bitmap, mActivity);
        converter.setFileCallBackListener(new Converter.FileCompletion() {
            @Override
            public void fileCallBack(File file) {
                if (file != null)  mOnImageSelection.onSelected(data,bitmap,file);
            }
        });


    }


}
