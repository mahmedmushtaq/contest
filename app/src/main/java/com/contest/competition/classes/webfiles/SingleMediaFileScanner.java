package com.contest.competition.classes.webfiles;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/**
 * Created by M Ahmed Mushtaq on 7/8/2018.
 */

public class SingleMediaFileScanner implements MediaScannerConnection.MediaScannerConnectionClient{

    private MediaScannerConnection mMs;
    private File mFile;

    public SingleMediaFileScanner(Context context, File f) {
        mFile = f;
        mMs = new MediaScannerConnection(context, this);
        mMs.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMs.scanFile(mFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMs.disconnect();
    }
}
