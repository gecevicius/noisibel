package com.example.noisibel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.noisibel.Recording;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recorder {

    private Button start;
    private Button stop;

    MediaRecorder mRecorder;
    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;


    private List<Number> dbs = new ArrayList<>();
    private String outputFile;

    public void startRecorder(){

        if (mRecorder == null)
        {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            Date date= new Date();
            long time = date. getTime();

            String folder_main = "noisibel_recordings";

            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdirs();
            }

            //NEED TO CREATE A FOLDER HERE
            outputFile = Environment.getExternalStorageDirectory() + "/noisibel_recordings/recording-"+ time +".3gp";
            Log.d("path",String.valueOf(outputFile));
            mRecorder.setOutputFile(outputFile);

            File recordingFile = new File(outputFile);
            try
            {
                mRecorder.prepare();
            }catch (java.io.IOException ioe) {
                android.util.Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try
            {
                mRecorder.start();
            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            //mEMA = 0.0;
        }
    }

    public void stopRecorder() {
        Recording recording = new Recording(outputFile);
        recording.setDbs(dbs);
        ListOfRecordings.add(recording);
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude());
        else
            return 0;
    }

    public double soundDb(boolean capture){
        double db = 20 * Math.log10((double)Math.abs(getAmplitudeEMA()));
        if(capture){
            dbs.add(db);
        }
        return  db;
    }

    public double getAmplitudeEMA() {
        double amp =  getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }
}
