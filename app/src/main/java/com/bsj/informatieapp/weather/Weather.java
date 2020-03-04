package com.bsj.informatieapp.weather;


import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.annotation.RequiresApi;

public class Weather{
    public int temp;
    public int feelTemp;
    public int windSpeed;
    public int windDirection;
    public String cityName;
    public int clouds;
    public float rain;
    public String date;
    public boolean snow;



    public String getTime(){
        String newDate = date.substring(11, 16);

        return newDate;
    }

    public String getDate(){
        String newDate = "";
        newDate += date.substring(8,10);
        newDate += " ";
        for (int i = 0; i < 12; i++){
            if(Integer.parseInt(date.substring(5,7)) == i){
                newDate += months[i];
            }
        }
        return newDate;
    }

    private String[] months = {
            "Januari",
            "Februari",
            "Maart",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Augustus",
            "September",
            "Oktober",
            "Nomvember",
            "December"
    };


}
