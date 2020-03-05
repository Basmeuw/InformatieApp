package com.bsj.informatieapp.events;

import java.util.Date;

public class Event {
    public String id;
    public String category;
    public String startTime;
    public String endTime;
    public String name;
    public String owner;
    public String place;
    public String type;
    public String description;
    public int attendingCount;
    public String informationSource;
    public String date;

    public String getEventDay(){
        String eventDay = date.substring(8,10);
        return eventDay;
    }

    public String getEventMonth(){
        String eventMonth = "";
        String eventMonthNumber = date.substring(5,7);
        if(eventMonthNumber.equals("01")){
            eventMonth = "JAN";
        } else if(eventMonthNumber.equals("02")){
            eventMonth = "FEB";
        } else if(eventMonthNumber.equals("03")){
            eventMonth = "MAR";
        } else if(eventMonthNumber.equals("04")){
            eventMonth = "APR";
        } else if(eventMonthNumber.equals("05")){
            eventMonth = "MAY";
        } else if(eventMonthNumber.equals("06")){
            eventMonth = "JUN";
        } else if(eventMonthNumber.equals("07")){
            eventMonth = "JUL";
        } else if(eventMonthNumber.equals("08")){
            eventMonth = "AUG";
        } else if(eventMonthNumber.equals("09")){
            eventMonth = "SEP";
        } else if(eventMonthNumber.equals("10")){
            eventMonth = "OCT";
        } else if(eventMonthNumber.equals("11")){
            eventMonth = "NOV";
        } else if(eventMonthNumber.equals("12")){
            eventMonth = "DEC";
        }

        return  eventMonth;
    }

//    public Event(Date date, String name, float lat, float lon, float startTime, float endTime) {
//        this.date = date;
//        this.name = name;
//        this.lat = lat;
//        this.lon = lon;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setLocation(float lat, float lon){
//        this.lat = lat;
//        this.lon = lon;
//    }
//
//    public float getLat(){
//        return lat;
//    }
//
//    public float getLon(){
//        return lon;
//    }
//
//    public float getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(float startTime) {
//        this.startTime = startTime;
//    }
//
//    public float getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(float endTime) {
//        this.endTime = endTime;
//    }
}
