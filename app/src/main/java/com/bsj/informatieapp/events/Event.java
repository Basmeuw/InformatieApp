package com.bsj.informatieapp.events;

import java.util.Date;

public class Event {
    private Date date;
    private String name;
    private float lat;
    private float lon;
    private float startTime;
    private float endTime;

    public Event(Date date, String name, float lat, float lon, float startTime, float endTime) {
        this.date = date;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(float lat, float lon){
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat(){
        return lat;
    }

    public float getLon(){
        return lon;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }
}
