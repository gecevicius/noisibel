package com.example.noisibel;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import java.io.File;

public class RecordingView  extends AppCompatActivity {

    private TextView date;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording_view);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                file= null;
            } else {
                file = (File) extras.get("recording");
            }
        } else {
            file = (File) savedInstanceState.getSerializable("recording");
        }
        date = findViewById(R.id.date);
        date.setText(file.getName());
    }

    private void displayDb(){

    }
}
