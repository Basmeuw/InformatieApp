package com.bsj.informatieapp.weather;


import android.icu.text.DateFormat;
import android.os.Build;
import android.util.Log;

import com.bsj.informatieapp.events.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public boolean snow;//



    public String getTime(){
        String newDate = date.substring(11, 16);

        return newDate;
    }

    public String getTimeMiliseconds(){
        String newDate = date.substring(11, 19);

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

    public static Weather[] weatherDuringEvent(Weather[] weather, Event event) {

        int startTime = Integer.parseInt(event.startTime.substring(0,event.startTime.indexOf(":")));
        int endTime = Integer.parseInt(event.endTime.substring(0,event.endTime.indexOf(":")));
        int startIndex = 0;

        float newTime = 0;
        if(endTime > startTime){
            newTime = endTime - startTime;
        }
        else if(endTime < startTime){
            newTime = 24 - startTime + endTime;
        }

        int i = Math.round(newTime/3);

        Weather[] newWeather = new Weather[i];


        if(startTime >= 0 && startTime < 3 ){
            startIndex = findIndex(weather, "00:00", event.getEventDay());
        } else if(startTime >= 3 && startTime < 6){
            startIndex = findIndex(weather, "03:00", event.getEventDay());
        } else if(startTime >= 6 && startTime < 9){
            startIndex = findIndex(weather, "06:00", event.getEventDay());
        }else if(startTime >= 9 && startTime < 12){
            startIndex = findIndex(weather, "09:00", event.getEventDay());
        }else if(startTime >= 12 && startTime < 15){
            startIndex = findIndex(weather, "12:00", event.getEventDay());
        }else if(startTime >= 15 && startTime < 18){
            startIndex = findIndex(weather, "15:00", event.getEventDay());
        }else if(startTime >= 18 && startTime < 21){
            startIndex = findIndex(weather, "18:00", event.getEventDay());
        }else if(startTime >= 21 && startTime < 24){
            startIndex = findIndex(weather, "21:00", event.getEventDay());
        }


        Log.d("customdebug", String.valueOf(startIndex));
        Log.d("customdebug", String.valueOf(event.getEventDay()));

        for(int j = 0; j < newWeather.length; j++){
            newWeather[j] = weather[startIndex + j];
        }

        return newWeather;

    }

    private static int findIndex(Weather[] weather, String time, String day){
        int i = 0;
        while( i < weather.length){
            if(weather[i].getTime().equals(time) && weather[i].date.substring(8,10).equals(day)){
                return i;
            }
            else{
                i = i + 1;
            }
        }
        return -1;
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
