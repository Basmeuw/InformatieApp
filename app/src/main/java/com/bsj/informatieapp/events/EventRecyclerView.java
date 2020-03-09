package com.bsj.informatieapp.events;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.news.NewsRecyclerViewAdapter;
import com.bsj.informatieapp.weather.Weather;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventRecyclerView extends RecyclerView.Adapter<EventRecyclerView.EventViewHolder> {

    private Event[] events;
    private Weather[] weather;
    private boolean chartVisible;

    EventRecyclerView(Event[] events, Weather[] weather){
        this.events = events;
        this.weather = weather;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_view_events, viewGroup, false);


        EventViewHolder viewHolder = new EventViewHolder(view);

        return viewHolder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        eventViewHolder.eventName.setText(events[i].name);
        eventViewHolder.eventLocation.setText(events[i].place);
        eventViewHolder.eventTime.setText(events[i].startTime.substring(0,5) + " tot " + events[i].endTime.substring(0,5));
        eventViewHolder.eventDate.setText(events[i].date);
        eventViewHolder.eventAttendees.setText(events[i].attendingCount + " bezoekers");
        eventViewHolder.eventSource.setText(events[i].informationSource);
        eventViewHolder.eventDay.setText(events[i].getEventDay());
        eventViewHolder.eventMonth.setText(events[i].getEventMonth());

        Weather[] eventWeather = Weather.weatherDuringEvent(weather, events[i]);

        if(eventWeather[0] != null){

            eventViewHolder.weatherButton.setVisibility(View.VISIBLE);
            eventViewHolder.weatherChart.setVisibility(View.INVISIBLE);

            initializeEventWeatherChart(eventWeather, eventViewHolder.weatherChart);

            eventViewHolder.weatherButton.setOnClickListener(v -> {
                if(!chartVisible){
                    eventViewHolder.weatherChart.setVisibility(View.VISIBLE);
                    eventViewHolder.eventName.setVisibility(View.INVISIBLE);
                    eventViewHolder.eventLocation.setVisibility(View.INVISIBLE);
                    eventViewHolder.eventTime.setVisibility(View.INVISIBLE);
                    eventViewHolder.eventDate.setVisibility(View.INVISIBLE);
                    eventViewHolder.eventAttendees.setVisibility(View.INVISIBLE);

                } else{
                    eventViewHolder.weatherChart.setVisibility(View.INVISIBLE);
                    eventViewHolder.eventName.setVisibility(View.VISIBLE);
                    eventViewHolder.eventLocation.setVisibility(View.VISIBLE);
                    eventViewHolder.eventTime.setVisibility(View.VISIBLE);
                    eventViewHolder.eventDate.setVisibility(View.VISIBLE);
                    eventViewHolder.eventAttendees.setVisibility(View.VISIBLE);
                }
                chartVisible = !chartVisible;

            });
        }


    }

    private void initializeEventWeatherChart(Weather[] weather, CombinedChart weatherChart){

        weatherChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        setAxis(weather, weatherChart);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData(weather));
        combinedData.setData(generateBarData(weather));


        weatherChart.setData(combinedData);
        weatherChart.invalidate();
        weatherChart.setDescription(null);

        weatherChart.getLegend().setEnabled(false);

    }



    private void setAxis(Weather[] weather, CombinedChart weatherChart){
        YAxis rightAxis = weatherChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = weatherChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = weatherChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        ValueFormatter valueFormatter = new ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return weather[(int) value].getTime();
            }
        };
        xAxis.setValueFormatter(valueFormatter);

    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries, Weather[] weather){
        for(int j = 0; j < weather.length; j++){
            entries.add(new Entry(j, weather[j].temp - 273));
        }


        return entries;
    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries, Weather[] weather){
        for(int j = 0; j < weather.length; j++){
            entries.add(new BarEntry(j, weather[j].rain));
        }
        return  entries;
    }

    private LineData generateLineData(Weather[] weather){
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();
        entries = getLineEntriesData(entries, weather);

        LineDataSet lineDataSet = new LineDataSet(entries, "temperatuur");

        lineDataSet.setColors(Color.rgb(255,0,0));
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleColor(Color.rgb(255,0,0));
        lineDataSet.setCircleRadius(3);
        lineDataSet.setFillColor(Color.rgb(255,0,0));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.rgb(255, 0, 0));

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineData.addDataSet(lineDataSet);

        return lineData;
    }

    private BarData generateBarData(Weather[] weather){
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries = getBarEnteries(entries, weather);

        BarDataSet barDataSet = new BarDataSet(entries, "Regen");
        barDataSet.setColors(Color.rgb(0,0,255));
        barDataSet.setValueTextColor(Color.rgb(0, 0, 255));
        barDataSet.setValueTextSize(10f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        barDataSet.setDrawValues(false);
        float barWidth = 0.45f;


        BarData d = new BarData(barDataSet);
        d.setBarWidth(barWidth);

        return d;
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
        TextView eventDay;
        TextView eventMonth;
        Button weatherButton;
        CombinedChart weatherChart;


        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDate = itemView.findViewById(R.id.events_datum);
            eventTime = itemView.findViewById(R.id.events_tijd);
            eventLocation = itemView.findViewById(R.id.events_plaats);
            eventName = itemView.findViewById(R.id.events_evenement);
            eventSource = itemView.findViewById(R.id.events_bron);
            eventAttendees = itemView.findViewById(R.id.events_bezoekersaantal);
            eventDay = itemView.findViewById(R.id.events_day);
            eventMonth = itemView.findViewById(R.id.events_month);
            weatherButton = itemView.findViewById(R.id.events_weather_button);
            weatherChart = itemView.findViewById(R.id.events_weather_chart);


        }
    }
}
