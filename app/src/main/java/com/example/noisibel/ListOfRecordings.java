package com.example.noisibel;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//singleton class for holding list of recordings
public class ListOfRecordings {
    private static List<Recording> recordings = new ArrayList<>();

    private static ListOfRecordings instance;

    private static boolean set = false;

    public static ListOfRecordings getInstance() {
        if (instance == null)
            instance = new ListOfRecordings();
        return instance;
    }

    public static void add(Recording recording){
        recordings.add(recording);
    }

    public static void setRecordings(){
        if(set == false){
            String path =  Environment.getExternalStorageDirectory() + "/noisibel_recordings";
            File directory = new File(path);
            List<File> files = Arrays.asList(directory.listFiles());

            if( recordings.size() > 0){
                recordings.clear();
            }

            

            for(File file : files){
                Recording recording = new Recording(file.getPath());
                add(recording);
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
}
