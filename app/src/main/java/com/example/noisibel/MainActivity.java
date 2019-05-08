package com.example.noisibel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button saved;
    private Button stop;
    Recorder recorder;
    private TextView mDecibels;
    private Thread runner;
    private ImageView needle;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentGraph fragmentGraph;
    private FragmentComparison fragmentComparison;

    static final private double EMA_FILTER = 0.6;
    private boolean recording;
    final Runnable updater = new Runnable(){

        public void run(){
            decibelUpdate();
        };
    };
    final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDecibels = (TextView) findViewById(R.id.status);
        needle = findViewById(R.id.needle);

        setUpTabs();
        recording = false;
        if (runner == null)
        {
            runner  = new Thread(){
                public void run()
                {
                    while (runner != null )
                    {
                        if(recording == true){
                            try
                            {
                                Log.d("recording",Boolean.toString(recording));
                                Thread.sleep(1000);
                            } catch (InterruptedException e) { };
                            mHandler.post(updater);
                        }
                    }
                }
            };
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                220);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                        225);
            } else {

                recorder = new Recorder();
                start = findViewById(R.id.start);
                stop = findViewById(R.id.stop);
                saved = findViewById(R.id.saved);

                saved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, SavedRecordings.class));
                    }
                });

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Noise", "start runner()");
                        start();

                    }
                });

                stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Noise", "stop runner()");
                        pause();
                    }
                });
                runner.start();

            }

    }



    private void setUpTabs(){
        //set up variables
        viewPager = findViewById(R.id.homeViewPager);
        tabLayout = findViewById(R.id.homeTabLayout);
        ViewPagerAdapater adapter = new ViewPagerAdapater(getSupportFragmentManager());
        //add fragments
        fragmentGraph = new FragmentGraph();
        fragmentComparison = new FragmentComparison();
        adapter.AddFragment(fragmentGraph,"Graph");
        adapter.AddFragment(fragmentComparison,"Comparison");
        //connecting the components
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void decibelUpdate(){
        //get decibel level
        double db = recorder.soundDb();

        //update Graph
        fragmentGraph.addPoint(db);

        //set up needle positioning
        double needlePercentage = 100.0 / 135.0 * db;
        double angle =   -135.0 + (needlePercentage / 100 * 270.0);
        needle.setRotation((float)angle);
        // set active member of the Comparison table
        fragmentComparison.setActive(db);


        mDecibels.setText(db + " dB");
    }

    public void start()
    {
        Log.d("onResume","here");
        recording = true;
        recorder.startRecorder();
    }

    public void pause()
    {
        recorder.stopRecorder();
        recording = false;
        mHandler.removeCallbacks(runner);
    }


}
