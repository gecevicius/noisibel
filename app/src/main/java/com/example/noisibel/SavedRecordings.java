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
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SavedRecordings extends AppCompatActivity {

    private RecyclerView recordingsView;
    private RecordingListAdapter recyclerAdapter;
    private LinearLayoutManager recyclerLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recordings);
        //ListOfRecordings.setRecordings();
        ListOfRecordings.loadData();
        recordingsView = (RecyclerView) findViewById(R.id.recordingsRecycler);
        recyclerLayoutManager = new LinearLayoutManager(this);
        recordingsView.setLayoutManager(recyclerLayoutManager);
        recyclerAdapter = new RecordingListAdapter(this);
        recordingsView.setAdapter(recyclerAdapter);
    }

}
