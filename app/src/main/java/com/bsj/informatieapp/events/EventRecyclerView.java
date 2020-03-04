package com.bsj.informatieapp.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.news.NewsRecyclerViewAdapter;

import org.w3c.dom.Text;

public class EventRecyclerView extends RecyclerView.Adapter<EventRecyclerView.EventViewHolder> {

    private Event[] events;

    EventRecyclerView(Event[] events){
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_view_events, viewGroup, false);


        EventViewHolder viewHolder = new EventViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        eventViewHolder.eventName.setText(events[i].name);
        eventViewHolder.eventLocation.setText(events[i].place);
        eventViewHolder.eventTime.setText(events[i].startTime + " tot " + events[i].endTime);
        eventViewHolder.eventDate.setText(events[i].date);
        eventViewHolder.eventAttendees.setText(events[i].name);
        eventViewHolder.eventSource.setText(events[i].name);


    }

    @Override
    public int getItemCount() {
        return events.length;
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        TextView eventDate;
        TextView eventTime;
        TextView eventLocation;
        TextView eventName;
        TextView eventSource;
        TextView eventAttendees;


        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDate = itemView.findViewById(R.id.events_datum);
            eventTime = itemView.findViewById(R.id.events_tijd);
            eventLocation = itemView.findViewById(R.id.events_plaats);
            eventName = itemView.findViewById(R.id.events_evenement);
            eventSource = itemView.findViewById(R.id.events_bron);
            eventAttendees = itemView.findViewById(R.id.events_bezoekersaantal);


        }
    }
}
