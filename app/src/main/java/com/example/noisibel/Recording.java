package com.example.noisibel;

import android.util.Log;

import java.io.File;
import java.math.BigInteger;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

public class Recording {

    private File file;
    private Date date;
    private double length;

    private List<Number> dbs;
    private double avg = 0;

    public double getLength() {
        return length;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    private double min = 0;
    private double max = 0;

    public Recording(String file){
        this.file = new File(file);
        String stringTs = this.file.getName().replaceAll("^recording?-?", "").replaceAll("\\.([A-Za-z0-9])*$","");
        long intTs = Long.parseLong(stringTs,25);
        Log.d("timestamp",String.valueOf(intTs));
        date = new Date(intTs);
    }

    public void setDbs(List<Number> dbs){
        this.dbs = dbs;
        for(int i = 0; i < dbs.size() ; i++){
            double item = (double) dbs.get(i);
            avg = avg + item;

            if(min < item)
                min = item;

            if (max > item)
                max = item;
        }
        avg = avg / dbs.size();
    }

    public File getFile() {
        return file;
    }

    public List<Number> getDbs() {
        return dbs;
    }

    public Date getDate(){
       return date;
    }


}
