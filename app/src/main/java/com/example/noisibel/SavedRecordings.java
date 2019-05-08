package com.example.noisibel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SavedRecordings extends AppCompatActivity {

    private RecyclerView recordingsView;
    private RecordingListAdapter recyclerAdapter;
    private LinearLayoutManager recyclerLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recordings);
        String path =  Environment.getExternalStorageDirectory() + "/noisibel_recordings";

        File directory = new File(path);
        List<File> files = Arrays.asList(directory.listFiles());

        recordingsView = (RecyclerView) findViewById(R.id.recordingsRecycler);
         recyclerLayoutManager = new LinearLayoutManager(this);
        recordingsView.setLayoutManager(recyclerLayoutManager);
        recyclerAdapter = new RecordingListAdapter(this,files);
        recordingsView.setAdapter(recyclerAdapter);

    }
}
