package com.example.noisibel;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;



import com.androidplot.xy.XYPlot;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentGraph extends Fragment{
    private View view;
    private int chart;
    private SimpleXYSeries chartSeries;
    private LineAndPointFormatter chartSeriesFormat;
    private final String[] domainLabels = {"10s", "9s", "8s", "7s", "6s", "5s", "4s", "3s", "2s", "1s"};
    private int ticks = 0;
    private XYPlot plot;
    public FragmentGraph() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.graph,container,false);
        setUpChart();
        return view;
    }

    public void addPoint(double decibel){
        if(chartSeries.size() >= 10){
            chartSeries.addFirst(chartSeries.size(),(int) decibel);
            chartSeries.removeLast();
            for(int i = 0;i<chartSeries.size();i++){
                Number newVal = chartSeries.getX(i).intValue() - 1;
                chartSeries.setX(newVal,i);
            }

            plot.clear();
            plot.addSeries(chartSeriesFormat,chartSeries);
        }
        else{
            chartSeries.addFirst(chartSeries.size(),(int) decibel);
        }
        Log.d("data",String.valueOf(chartSeries.size()));

        plot.redraw();
    }
    private void setUpChart(){
        // initialize our XYPlot reference:
        plot = (XYPlot) view.findViewById(R.id.plot);
        ArrayList<Number> data = new ArrayList<>();
        plot.setRangeBoundaries(0, 135, BoundaryMode.FIXED);
        plot.setDomainBoundaries(0,10,BoundaryMode.FIXED);
        plot.setDomainStepValue(1);
        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        chartSeries = new SimpleXYSeries(
                data, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "dB Levels");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
         chartSeriesFormat =
                new LineAndPointFormatter(view.getContext(), R.xml.line_point_formatter_with_labels);

        // see: http://androidplot.com/smooth-curves-and-androidplot/
        chartSeriesFormat.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(chartSeries, chartSeriesFormat);
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM);
    }

    }





