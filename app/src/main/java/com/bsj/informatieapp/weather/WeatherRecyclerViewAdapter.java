package com.bsj.informatieapp.weather;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsj.informatieapp.R;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {

    private static final String TAG = "WeatherRecyclerViewAdapter";

    private Weather[] weather;
    private Calendar calendar;
    private int days = 5;
    private int dayNumber;

    public WeatherRecyclerViewAdapter(Weather[] weather) {
        this.weather = weather;
        calendar = Calendar.getInstance(TimeZone.getDefault());
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_view_weather, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder viewHolder, int i) {
        setLabels(viewHolder,i);
        initializeChart(viewHolder, i);

    }

    @Override
    public int getItemCount() {
        return days - 1;
    }

    private void setLabels(WeatherViewHolder viewHolder, int i){
        dayNumber = calendar.get(Calendar.DAY_OF_MONTH) + 1 + i;

        viewHolder.dateText.setText(weather[Weather.findIndex(weather, "00:00", getFormattedDay(dayNumber))].getDate());
    }

    private String getFormattedDay(int day){
        if(day < 10){
            return "0" + day;
        } else{
            return String.valueOf(day);
        }
    }


    private void initializeChart(WeatherViewHolder viewHolder, int i){
        viewHolder.weatherChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        setAxis(viewHolder);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData(i));
        combinedData.setData(generateBarData(i));

        viewHolder.weatherChart.setData(combinedData);
        viewHolder.weatherChart.invalidate();
        viewHolder.weatherChart.setDescription(null);
        viewHolder.weatherChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //viewHolder.weatherChart.getBarData().setDrawValues(true);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        viewHolder.weatherChart.getLegend().setEnabled(false);
    }

    private void setAxis(WeatherViewHolder viewHolder){
        YAxis rightAxis = viewHolder.weatherChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = viewHolder.weatherChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = viewHolder.weatherChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        ValueFormatter valueFormatter = new ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return times[(int) value % times.length];
            }
        };
        xAxis.setValueFormatter(valueFormatter);

    }

    private LineData generateLineData(int i){
        LineData lineData = new LineData();


        ArrayList<Entry> entries = new ArrayList<>();
        getLineEntriesData(entries, i);

        LineDataSet lineDataSet = new LineDataSet(entries, "temperatuur");

        lineDataSet.setColors(Color.rgb(255,0,0));
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleColor(Color.rgb(255,0,0));
        lineDataSet.setCircleRadius(3);
        lineDataSet.setFillColor(Color.rgb(255,0,0));
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.rgb(255, 0, 0));

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineData.addDataSet(lineDataSet);


        return lineData;
    }

    private BarData generateBarData(int i){
        ArrayList<BarEntry> entries = new ArrayList<>();
        getBarEntries(entries, i);

        BarDataSet barDataSet = new BarDataSet(entries, "Regen");
        barDataSet.setColors(Color.rgb(0,0,255));
        barDataSet.setValueTextColor(Color.rgb(0, 0, 255));
        barDataSet.setDrawValues(false);
        barDataSet.setValueTextSize(10f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);


        float barWidth = 0.45f;


        BarData d = new BarData(barDataSet);
        d.setBarWidth(barWidth);

        return d;
    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries, int i){

        for(int j = 0; j < 9; j++) {
            entries.add(new Entry(j,weather[Weather.findIndex(weather, "00:00", getFormattedDay(dayNumber)) + j].temp - 273));
        }

        return entries;
    }

    private ArrayList<BarEntry> getBarEntries(ArrayList<BarEntry> entries, int i){

        for(int j = 0; j < 9; j++) {
            entries.add(new BarEntry(j,weather[Weather.findIndex(weather, "00:00", getFormattedDay(dayNumber)) + j].rain));
        }

        return entries;
    }

    private String[] times = new String[]{
            "00:00", "03:00", "06:00", "09:00", "12:00", "15:00", "18:00", "21:00", "24:00"
    };



    static class WeatherViewHolder extends  RecyclerView.ViewHolder{

        CombinedChart weatherChart;
        TextView dateText;
        ConstraintLayout constraintLayout;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherChart = itemView.findViewById(R.id.weather_customlistview_combinedChart);
            dateText = itemView.findViewById(R.id.weather_customlistview_date);

            constraintLayout = itemView.findViewById(R.id.weather_customlistview_constraintLayout);




        }
    }
}
