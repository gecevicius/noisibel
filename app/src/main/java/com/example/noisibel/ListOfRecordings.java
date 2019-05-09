package com.example.noisibel;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;



//singleton class for holding list of recordings
public class ListOfRecordings {
    private static List<Recording> recordings = new ArrayList<>();
    private static ListOfRecordings instance;
    private static File mFolder = new File(Environment.getExternalStorageDirectory() + "/noisibel_recordings");
    private static boolean set = false;

    public static ListOfRecordings getInstance() {
        if (instance == null)
            instance = new ListOfRecordings();
        return instance;
    }

    public static void add(Recording recording){
        recordings.add(recording);saveRecordings();
    }

    public static void setRecordings() {
        if(set == false){
            String path =  Environment.getExternalStorageDirectory() + "/noisibel_recordings";
            File directory = new File(path);
            List<File> files = Arrays.asList(directory.listFiles());

            if( recordings.size() > 0){
                recordings.clear();
            }


            for(File file : files){
                if(file.getPath().contains(".3gp")){
                    Recording recording = new Recording(file.getPath());
                    add(recording);
                }

            }
            set = true;
        }


    }

    public static Recording getRecording(int index){
        return recordings.get(index);
    }

    public static List<Recording> getRecordings(){
        return recordings;
    }

    public static int size(){
        return recordings.size();
    }

    public static void delete(int index){
        recordings.get(index).getFile().delete();
        recordings.remove(index);
    }





    public static void saveRecordings() {

        ObjectOutput out;
        try {
            File outFile = new File(mFolder,
                    "recordings.data");
            out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(recordings);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {

        ObjectInput in;
        try {
            FileInputStream fileIn = new FileInputStream(mFolder.getPath() + File.separator + "recordings.data");

            in = new ObjectInputStream(fileIn);
            recordings = (List<Recording>) in.readObject();
            Log.d("recording length",String.valueOf(recordings.size()));
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
