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

import com.example.noisibel.Recording;

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
        ListOfRecordings.setRecordings();
        recordingsView = (RecyclerView) findViewById(R.id.recordingsRecycler);
        recyclerLayoutManager = new LinearLayoutManager(this);
        recordingsView.setLayoutManager(recyclerLayoutManager);
        recyclerAdapter = new RecordingListAdapter(this);
        recordingsView.setAdapter(recyclerAdapter);
    }

}
