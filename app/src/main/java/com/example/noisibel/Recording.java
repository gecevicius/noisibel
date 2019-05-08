package com.example.noisibel;

import java.io.File;
import java.security.Timestamp;
import java.util.Date;

public class Recording {

    private File file;
    private Date date;
    private double length;

    private double[] dbs;
    private double avg = 0;
    private double min = 0;
    private double max = 0;

    public Recording(File file,double[] dbs){
        this.file = file;
        this.dbs = dbs;

        String stringTimestamp = file.getName().substring(8);
        date = new Date(Integer.parseInt(stringTimestamp) * 1000);

        for(int i = 0; i < dbs.length ; i++){
            avg = avg + dbs[i];

            if(min < dbs[i])
                min = dbs[i];

            if (max > dbs[i])
                max = dbs[i];
        }
        avg = avg / dbs.length;

    }

    public File getFile() {
        return file;
    }

    public double[] getDbs() {
        return dbs;
    }

    public Date getDate(){
       return date;
    }
}
