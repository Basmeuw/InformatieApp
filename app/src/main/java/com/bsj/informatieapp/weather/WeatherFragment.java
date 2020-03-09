package com.bsj.informatieapp.weather;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import com.bsj.informatieapp.R;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class WeatherFragment extends Fragment {

    private CombinedChart combinedChart;
    private View view;
    private Context activity;
    private ArrayList<String> dates = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);


        WeatherViewModel model = ViewModelProviders.of(requireActivity()).get(WeatherViewModel.class);
        model.getWeather(getContext()).observe(this, weather -> {
            setupRecyclerView(weather);
            initializeChart(weather);
            initializeView(weather);
        });

        return view;
    }

    private void initializeView(Weather[] weather){
        TextView currentTemp = view.findViewById(R.id.weather_temperatuur);
        TextView feelTemp = view.findViewById(R.id.weather_gevoelstemperatuur);
        TextView windSpeed = view.findViewById(R.id.weather_windsnelheid);
        TextView clouds = view.findViewById(R.id.weather_bewolking);
        ImageView kompas = view.findViewById(R.id.weather_afbeelding_kompasnaald);

        currentTemp.setText(weather[0].temp - 273 + "\u00B0" + "C");
        feelTemp.setText(weather[0].feelTemp - 273 + "\u00B0" + "C");
        windSpeed.setText(weather[0].windSpeed + "m/s");
        clouds.setText(weather[0].clouds + "%");
        kompas.setRotation(weather[0].windDirection);

    }

    private void setupRecyclerView(Weather[] weather){
        RecyclerView recyclerView = view.findViewById(R.id.weather_recyclerview_komend);
         WeatherRecyclerViewAdapter adapter = new WeatherRecyclerViewAdapter(weather);
               recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        this.activity = context;

    }

    private void initializeChart(Weather[] weather){



        combinedChart = (CombinedChart) view.findViewById(R.id.events_weather_chart);

        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
            CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        setAxis(weather);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData(weather));
        combinedData.setData(generateBarData(weather));

        combinedChart.setData(combinedData);
        combinedChart.invalidate();
        combinedChart.setDescription(null);

        combinedChart.getLegend().setEnabled(false);

        combinedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                combinedChart.getBarData().setDrawValues(true);
                combinedChart.getLineData().setDrawValues(true);
            }

            @Override
            public void onNothingSelected() {
                combinedChart.getBarData().setDrawValues(false);
                combinedChart.getLineData().setDrawValues(false);
            }
        });

    }

    private void setAxis(Weather[] weather){
        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
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
        for(int j = 0; j < 9; j++){
            entries.add(new Entry(j, weather[j].temp - 273));
        }


        return entries;
    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries, Weather[] weather){
        for(int j = 0; j < 9; j++){
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

}
