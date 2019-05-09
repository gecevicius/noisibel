package com.example.noisibel;

import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Recording implements Serializable {

    private File file;
    private Date date;
    private double length;
    private String timestamp;

    private transient List<Number> dbs;
    private double avg = 0;
    private double min = 0;
    private double max = 0;


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

    public File getFile() {
        return file;
    }

    public List<Number> getDbs() {
        return dbs;
    }

    public Date getDate(){
        return date;
    }

    public String getTimestamp(){
        return timestamp;
    }


    public Recording(String file) {
        this.file = new File(file);
        String stringTs = this.file.getName().replaceAll("^recording?-?", "").replaceAll("\\.([A-Za-z0-9])*$","");
        try{
         long intTs = Long.parseLong(stringTs,25);
            this.timestamp = String.valueOf(intTs);
            date = new Date(intTs);
        }
        catch(Exception e){
            Log.d("error",e.getMessage());
        }

    }

    public void setDbs(List<Number> dbs) {
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

        //encryptying decibels and storing it as a string in a new file
        File encryptedFile = new File(this.file.getParentFile().getPath() + "encrypted-"+timestamp);


    }





}
