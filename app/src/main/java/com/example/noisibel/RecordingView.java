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

    private TextView date, name, length, avg, min, max,time;
    private Recording recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording_view);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                recording= null;
            } else {
                int index = (int) extras.get("recording");
                recording = ListOfRecordings.getRecording(index);
            }
        } else {
            recording = (Recording) savedInstanceState.getSerializable("recording");
        }

        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        avg = findViewById(R.id.avg);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);

        date.setText(recording.getFile().getName());
        time.setText(String.valueOf(recording.getDate().getTime()));
        avg.setText(String.valueOf(recording.getAvg()));
        min.setText(String.valueOf(recording.getMin()));
        max.setText(String.valueOf(recording.getMax()));

    }

    private void displayDb(){

    }
}
