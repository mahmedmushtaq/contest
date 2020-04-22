package com.contest.competition.classes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contest.competition.classes.webfiles.SingleMediaFileScanner;
import com.contest.competition.utils.views.SetFonts;

import com.google.android.material.tabs.TabLayout;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Random;



/**
 * Created by M Ahmed Mushtaq on 6/23/2018.
 */

public class Converter {
    private File file;
    private  File file2;
    private  FileCompletion completion;
    public interface FileCompletion{
        void  fileCallBack(File file);
    }

    public  void setFileCallBackListener(FileCompletion comple){
        completion = comple;
    }

    public  void convertBitmapIntofile(Bitmap bitmap, Context context){
        new fileFromBitmap(bitmap,context).execute();
    }

    public  Bitmap getBitmapFromUri(Context context,Uri uri){
        Bitmap bitmap = null;
      //  try {
          bitmap =  CompressBitmap.compressImage(uri.getPath());//MediaStore.Images.Media.getBitmap(context.getContentResolver() , uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return bitmap;
    }

    public  Bitmap getBitmapIntoIntent(Intent data, Context context){
        InputStream stream = null;
        try {
            stream = context.getContentResolver().openInputStream(
                    Crop.getOutput(data));
            Bitmap   bitmap = BitmapFactory.decodeStream(stream);
            stream.close();

            return bitmap;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }

    private  class fileFromBitmap extends AsyncTask<Void, Integer, String> {

        Context context;
        Bitmap bitmap;
       // String path_external = Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg";

        public fileFromBitmap(Bitmap bitmap, Context context) {
            this.bitmap = bitmap;
            this.context= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before executing doInBackground
            // update your UI
            // exp; make progressbar visible
        }

        @Override
        protected String doInBackground(Void... params) {




             storeImageInInternalStorage();


            return null;
        }

        private void storeImageInInternalStorage(){
              String appDirectoryName = "contest";
              File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).getAbsolutePath(), appDirectoryName);
             if(!imageRoot.exists())
              imageRoot.mkdirs();


           final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
           // file = new File(Environment.getExternalStorageDirectory() + File.separator + "profiles.jpg");
            Random random = new Random();
            int ran = random.nextInt(20000);
            file = new File(imageRoot,"images"+ran+".jpg");



                storeFileInInternalStorage(context, bytes, file);




        }




        //this is used to store image in gallery






        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if(completion != null){
                completion.fileCallBack(file);
            }


        }
    }


    public static String convertIntegerIntoK(double value){
        String numberString = "";
        Format format = new DecimalFormat("0.#");
        if (Math.abs(value / 1000000) >= 1) {
            //numberString = (getNoOfFollowingUsers() / 1000000)+"" + "m";
            numberString = format.format(value / 1000000)+"" + "m";
        }else if (Math.abs(value / 1000) >= 1) {
            numberString = format.format(value / 1000) + "k";
        }else {
            numberString = format.format(value)+"";
        }

        return numberString;
    }


    public  static  int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public  Uri getBitmapUri(Context context,Bitmap bitmap){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
            return Uri.parse(path);

    }



    public  int getImageRotation(Uri uri){
        try {
            ExifInterface exif = new ExifInterface(uri.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

           return rotation;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public   int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }



    private void storeFileInInternalStorage(Context context,ByteArrayOutputStream bytes,File file){
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        new SingleMediaFileScanner(context,file);
    }




    /* ======================= CHANGE TABS FONT ==================== */

    private void changeTabsFont(Context context, TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    SetFonts.setRobotoBold(context, ((TextView) tabViewChild));
                }
            }
        }
    }


}
