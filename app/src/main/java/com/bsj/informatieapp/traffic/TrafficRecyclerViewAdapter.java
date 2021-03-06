package com.bsj.informatieapp.traffic;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.traffic.Traffic;
import com.bsj.informatieapp.weather.WeatherRecyclerViewAdapter;

import org.w3c.dom.Text;

public class TrafficRecyclerViewAdapter extends RecyclerView.Adapter<TrafficRecyclerViewAdapter.TrafficViewHolder> {

    private Traffic[] traffic;


    public TrafficRecyclerViewAdapter(Traffic[] traffic){
        this.traffic = traffic;
    }

    @NonNull
    @Override
    public TrafficViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_view_traffic, viewGroup, false);

        return new TrafficViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrafficViewHolder viewHolder, int i) {

        viewHolder.road.setText(traffic[i].road);
        viewHolder.startSegment.setText(traffic[i].fromLoc);
        viewHolder.endSegment.setText(traffic[i].toLoc);
        viewHolder.distance.setText(traffic[i].distance / 1000 + "km");
        viewHolder.delay.setText(traffic[i].delay / 60 + "m");
        viewHolder.description.setText(traffic[i].description);
        if(traffic[i].roadType.equals("aWegen")){
            viewHolder.road.setBackgroundColor(Color.RED);
        } else if(traffic[i].roadType.equals("nWegen")){
            viewHolder.road.setBackgroundColor(Color.YELLOW);
        } else{
            viewHolder.road.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    @Override
    public int getItemCount() {
        return traffic.length;
    }



    class TrafficViewHolder extends RecyclerView.ViewHolder{

        TextView road;
        TextView startSegment;
        TextView endSegment;
        TextView distance;
        TextView delay;
        TextView description;

        TrafficViewHolder(@NonNull View itemView){
            super(itemView);

            road = itemView.findViewById(R.id.traffic_autoweg);
            startSegment = itemView.findViewById(R.id.traffic_startsegment);
            endSegment = itemView.findViewById(R.id.traffic_eindesegment);
            distance = itemView.findViewById(R.id.traffic_kilometer);
            delay = itemView.findViewById(R.id.traffic_vertraging);
            description = itemView.findViewById(R.id.traffic_beschrijving);

        }
    }
}
