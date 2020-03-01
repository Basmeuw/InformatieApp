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
    public String time;
    public boolean snow;


}
