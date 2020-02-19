package com.bsj.informatieapp.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import java.util.ArrayList;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import com.bsj.informatieapp.R;

public class WeatherFragment extends Fragment {

    private CombinedChart combinedChart;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);



        InitializeChart();

        return view;
    }

    private void InitializeChart(){

        combinedChart = (CombinedChart) view.findViewById(R.id.combinedChart);

        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
            CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData());
        combinedData.setData(generateBarData());

        combinedChart.setData(combinedData);
        combinedChart.invalidate();

    }

    //haal informatie uit de database voor de data
    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries){
        entries.add(new Entry(1, 10));
        entries.add(new Entry(2, 12));
        entries.add(new Entry(3, 13));
        entries.add(new Entry(4, 12));
        entries.add(new Entry(5, 10));
        entries.add(new Entry(6, 8));
        entries.add(new Entry(7, 3));

        return entries;
    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries){
        entries.add(new BarEntry(1, 0));
        entries.add(new BarEntry(2, 0));
        entries.add(new BarEntry(3, 0.24f));
        entries.add(new BarEntry(4, 0.75f));
        entries.add(new BarEntry(5, 1));
        entries.add(new BarEntry(6, 0));
        entries.add(new BarEntry(7, 0.57f));
        return  entries;
    }

    private LineData generateLineData(){
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();
        entries = getLineEntriesData(entries);

        LineDataSet lineDataSet = new LineDataSet(entries, "temperatuur");

        lineDataSet.setColors(Color.rgb(255,0,0));
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleColor(Color.rgb(255,0,0));
        lineDataSet.setCircleRadius(3);
        lineDataSet.setFillColor(Color.rgb(255,0,0));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.rgb(255, 0, 0));

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineData.addDataSet(lineDataSet);

        return lineData;
    }

    private BarData generateBarData(){
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries = getBarEnteries(entries);

        BarDataSet barDataSet = new BarDataSet(entries, "Regen");
        barDataSet.setColors(Color.rgb(0,0,255));
        barDataSet.setValueTextColor(Color.rgb(0, 0, 255));
        barDataSet.setValueTextSize(10f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f;


        BarData d = new BarData(barDataSet);
        d.setBarWidth(barWidth);

        return d;
    }
}
