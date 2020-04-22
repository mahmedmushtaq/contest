package com.contest.competition.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import androidx.core.app.ShareCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by M Ahmed Mushtaq on 8/12/2018.
 */

public class ShareApp {

    public static void shareAppLink(Activity activity){
        ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Select")
                .setText("http://play.google.com/store/apps/details?id=" + activity.getPackageName())
                .startChooser();
    }

    public static void shareText(Activity activity,String text){
        ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Select")
                .setText("Selfie Contest\n"+text+"\nVote your friend by clicking above link \n"+"Download app to use all of its features\n"+"http://play.google.com/store/apps/details?id=" + activity.getPackageName())
                .startChooser();
    }

    public static void shareApk(Context context){
        ApplicationInfo app = context.getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;
        Intent intent = new Intent(Intent.ACTION_SEND);

        // but Bluetooth does not accept this. Let’s use “*/*” instead.
        intent.setType("application/vnd.android.package-archive");

        // Append file and send Intent
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        context.startActivity(Intent.createChooser(intent, "share app using"));
    }

    public static void shareApplication(Context context) {
        ApplicationInfo app = context.getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location
            File tempFile = new File(context.getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            Log.e("copied", "shareApplication: file copied" );
            //Open share dialog
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            context.startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getString(int labelRes) {
        return  (labelRes+"").toLowerCase();
    }

}
