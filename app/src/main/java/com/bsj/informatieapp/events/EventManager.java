package com.bsj.informatieapp.events;

import android.util.Log;

import java.util.ArrayList;

public class EventManager {
    private ArrayList<Event> events;

    public EventManager(ArrayList<Event> events){
        this.events = events;
    }

    public void addEvent(Event e){
        events.add(e);
    }

    public void getUpcomingEvents(){
        if(hasEvents()){
            events.get(0);
        }else{
            Log.e("cdb", "No events!");
        }
    }

    public boolean hasEvents(){
        return events != null || events.size() != 0;
    }

}
