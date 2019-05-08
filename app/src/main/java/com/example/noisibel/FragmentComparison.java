package com.example.noisibel;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.XYPlot;

import java.util.ArrayList;

public class FragmentComparison extends Fragment{
    private View view;
    private RecyclerView recyclerView;
    private ComparisonRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager recyclerLayoutManager;


    public FragmentComparison() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.comparison,container,false);
        ArrayList<String> noiseLevels = new ArrayList<>();
        noiseLevels.add("Silence");
        noiseLevels.add("Breathing");
        noiseLevels.add("Whispering, Light snoring");
        noiseLevels.add("Quiet room");
        noiseLevels.add("Moderate snoring");
        noiseLevels.add("Loud conversation");
        noiseLevels.add("Busy street");
        noiseLevels.add("Hairdryer");
        noiseLevels.add("Loud radio");
        noiseLevels.add("Bass drum");
        noiseLevels.add("Subway train");
        noiseLevels.add("Industrial noise");
        noiseLevels.add("Jet plane take off");
        noiseLevels.add("Gunshot, Metal concert");
        // set up the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.comparisonRecycler);
        recyclerLayoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerAdapter = new ComparisonRecyclerAdapter(container.getContext(),noiseLevels);
        recyclerView.setAdapter(recyclerAdapter);

        //adding listeners
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisible = recyclerLayoutManager.findFirstCompletelyVisibleItemPosition();
                recyclerAdapter.changeItem(firstVisible);
            }
        });

        return view;
    }

    public void setActive(double db){
        int position = 0;
        position = (int) (db / 10) - 1;
        recyclerAdapter.changeItem(position);
    }
}
