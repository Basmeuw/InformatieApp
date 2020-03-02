package com.bsj.informatieapp.traffic;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_view_news, viewGroup, false);
        TrafficViewHolder viewHolder = new TrafficViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrafficViewHolder viewHolder, int i) {
        viewHolder.wegType.setText(traffic[i].roadType);
        viewHolder.startSegment.setText(traffic[i].fromLoc);
        viewHolder.endSegment.setText(traffic[i].toLoc);
        viewHolder.distance.setText(traffic[i].distance);
        viewHolder.delay.setText(traffic[i].delay);
        viewHolder.description.setText(traffic[i].description);

    }

    @Override
    public int getItemCount() {
        return traffic.length;
    }



    public class TrafficViewHolder extends RecyclerView.ViewHolder{

        TextView wegType;
        TextView startSegment;
        TextView endSegment;
        TextView distance;
        TextView delay;
        TextView description;

        public TrafficViewHolder(@NonNull View itemView){
            super(itemView);

            wegType = itemView.findViewById(R.id.traffic_autoweg);
            startSegment = itemView.findViewById(R.id.traffic_startsegment);
            endSegment = itemView.findViewById(R.id.traffic_eindesegment);
            distance = itemView.findViewById(R.id.traffic_kilometer);
            delay = itemView.findViewById(R.id.traffic_vertraging);
            description = itemView.findViewById(R.id.traffic_beschrijving);

        }
    }
}
