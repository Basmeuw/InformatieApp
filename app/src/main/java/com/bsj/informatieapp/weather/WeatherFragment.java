package com.bsj.informatieapp.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.bsj.informatieapp.R;

public class WeatherFragment extends Fragment {

    private CombinedChart combinedChart;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        initializeChart();

        return view;
    }

    private void initializeChart(){



        combinedChart = (CombinedChart) view.findViewById(R.id.combinedChart);

        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
            CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        setAxis();
        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData());
        combinedData.setData(generateBarData());

        combinedChart.setData(combinedData);
        combinedChart.invalidate();

        combinedChart.getLegend().setEnabled(false);

    }

    private void setAxis(){
        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setAxisMaximum(2);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(15);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
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

    //haal informatie uit de database voor de data
    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries){
        entries.add(new Entry(0, 10));
        entries.add(new Entry(1, 12));
        entries.add(new Entry(2, 13));
        entries.add(new Entry(3, 12));
        entries.add(new Entry(4, 10));
        entries.add(new Entry(5, 8));
        entries.add(new Entry(6, 3));
        entries.add(new Entry(7, 5));
        entries.add(new Entry(8, 9));


        return entries;
    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries){
        entries.add(new BarEntry(0, 0));
        entries.add(new BarEntry(1, 0));
        entries.add(new BarEntry(2, 0.24f));
        entries.add(new BarEntry(3, 0.75f));
        entries.add(new BarEntry(4, 1));
        entries.add(new BarEntry(5, 0));
        entries.add(new BarEntry(6, 0.57f));
        entries.add(new BarEntry(7, 1.5f));
        entries.add(new BarEntry(8, 0.78f));
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
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        float barWidth = 0.45f;


        BarData d = new BarData(barDataSet);
        d.setBarWidth(barWidth);

        return d;
    }

    protected String[] times = new String[]{
            "00:00", "03:00", "06:00", "09:00", "12:00", "15:00", "18:00", "21:00", "24:00"
    };
}
