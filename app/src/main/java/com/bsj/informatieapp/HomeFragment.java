package com.bsj.informatieapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bsj.informatieapp.events.Event;
import com.bsj.informatieapp.events.EventViewModel;
import com.bsj.informatieapp.events.EventsFragment;
import com.bsj.informatieapp.news.NewsFragment;
import com.bsj.informatieapp.news.NewsViewModel;
import com.bsj.informatieapp.traffic.TrafficFragment;
import com.bsj.informatieapp.traffic.TrafficViewModel;
import com.bsj.informatieapp.weather.Weather;
import com.bsj.informatieapp.weather.WeatherFragment;
import com.bsj.informatieapp.weather.WeatherViewModel;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private NewsViewModel newsViewModel;
    private CombinedChart weatherChart;
    private Boolean chartVisible = false;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();



        initializeWeather();
        initializeNews();
        initializeTraffic();


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void initializeEvents(Weather[] weather){
        final ConstraintLayout eventItem = view.findViewById(R.id.EventsLayout);
        final View eventsView = view.findViewById(R.id.eventsitem1);
        final TextView eventDate = eventsView.findViewById(R.id.events_datum);
        final TextView eventTime = eventsView.findViewById(R.id.events_tijd);
        final TextView eventLocation = eventsView.findViewById(R.id.events_plaats);
        final TextView eventName = eventsView.findViewById(R.id.events_evenement);
        final TextView eventSource = eventsView.findViewById(R.id.events_bron);
        final TextView eventAttendees = eventsView.findViewById(R.id.events_bezoekersaantal);
        final TextView eventDay = eventsView.findViewById(R.id.events_day);
        final TextView eventMonth = eventsView.findViewById(R.id.events_month);
        final Button rainButton = eventsView.findViewById(R.id.events_weather_button);

        EventViewModel model = ViewModelProviders.of(requireActivity()).get(EventViewModel.class);
        model.getAllEvents(getContext()).observe(this, events -> {
            assert events != null;
            eventName.setText(events[0].name);
            eventLocation.setText(events[0].place);
            eventTime.setText(events[0].startTime + " tot " + events[0].endTime);
            eventDate.setText(events[0].date);
            eventAttendees.setText(events[0].attendingCount + " bezoekers");
            eventSource.setText(events[0].informationSource);
            eventDay.setText(events[0].getEventDay());
            eventMonth.setText(events[0].getEventMonth());
            initializeEventWeatherChart(events, Weather.weatherDuringEvent(weather, events[1]));


            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new EventsFragment()).addToBackStack(null).commit();
                }
            });

            rainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!chartVisible){
                        weatherChart.setVisibility(View.VISIBLE);
                        eventName.setVisibility(View.INVISIBLE);
                        eventLocation.setVisibility(View.INVISIBLE);
                        eventTime.setVisibility(View.INVISIBLE);
                        eventDate.setVisibility(View.INVISIBLE);
                        eventAttendees.setVisibility(View.INVISIBLE);
                        eventSource.setVisibility(View.INVISIBLE);

                    } else{
                        weatherChart.setVisibility(View.INVISIBLE);
                        eventName.setVisibility(View.VISIBLE);
                        eventLocation.setVisibility(View.VISIBLE);
                        eventTime.setVisibility(View.VISIBLE);
                        eventDate.setVisibility(View.VISIBLE);
                        eventAttendees.setVisibility(View.VISIBLE);
                        eventSource.setVisibility(View.VISIBLE);
                    }
                    chartVisible = !chartVisible;

                }
            });

        });
    }

    private void initializeEventWeatherChart(Event[] events, Weather[] weather){
        weatherChart = view.findViewById(R.id.events_weather_chart);
        weatherChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        setAxis(weather);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData(weather));
        combinedData.setData(generateBarData(weather));


        weatherChart.setData(combinedData);
        weatherChart.invalidate();
        weatherChart.setDescription(null);

        weatherChart.getLegend().setEnabled(false);

    }



    private void setAxis(Weather[] weather){
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

    @SuppressLint("SetTextI18n")
    private void initializeTraffic(){
        final ConstraintLayout trafficItem = view.findViewById(R.id.TrafficLayout);
        final TextView road = view.findViewById(R.id.home_file_weg);
        final TextView time = view.findViewById(R.id.home_file_time);
        final TextView length = view.findViewById(R.id.home_file_length);


        TrafficViewModel model = ViewModelProviders.of(requireActivity()).get(TrafficViewModel.class);
        model.getTraffic(getContext()).observe(this,traffic -> {

            assert traffic != null;
            if(traffic.length > 0){
                road.setText(traffic[0].road);
                time.setText(traffic[0].delay / 60 + " min");
                length.setText(traffic[0].distance / 1000+ " km");
                trafficItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new TrafficFragment()).addToBackStack(null).commit();
                    }
                });
            }
            else{
                road.setText("Geen file");
            }

        });

    }

    private void initializeNews(){

        final View newsItem = view.findViewById(R.id.newsItem1);
        final TextView newsText = newsItem.findViewById(R.id.adapter_title);
        final TextView newsSource = newsItem.findViewById(R.id.adapter_source);
        final WebView webView = view.findViewById(R.id.home_webView);
        ImageView newsImage = newsItem.findViewById(R.id.adapter_thumbnail);
///
        NewsViewModel model = ViewModelProviders.of(requireActivity()).get(NewsViewModel.class);
        model.getAllNewsArticles(getContext()).observe(this,news -> {
            assert news != null;
            newsText.setText(news[0].title);
            newsSource.setText(news[0].krant);
            Picasso.with(this.getContext()).load(news[0].imageLink).into(newsImage);
            newsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(news[0].link);
                    webView.setVisibility(View.VISIBLE);
                }
            });
        });///////





        Button newsReadMore = view.findViewById(R.id.home_newsReadMore);
        newsReadMore.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initializeWeather(){
        TextView temp = view.findViewById(R.id.home_temp);
        TextView rain = view.findViewById(R.id.home_rain);
        ConstraintLayout weatherLayout = view.findViewById(R.id.WeatherLayout);
        weatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction().replace(R.id.fragment_container, new WeatherFragment()).addToBackStack(null).commit();
            }
        });

        WeatherViewModel model = ViewModelProviders.of(requireActivity()).get(WeatherViewModel.class);
        model.getWeather(getContext()).observe(this, weather -> {
            assert weather != null;
            temp.setText(weather[0].temp - 273+ "\u00B0" + "C");
            rain.setText(weather[0].rain + "mm");
            initializeEvents(weather);
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_newsReadMore:{
                // Load news page
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new NewsFragment()).addToBackStack(null).commit();
            }
            case R.id.newsItem1:{

            }
//            case R.id.home_eventsReadMore:{
//                // Load events page
//                fragmentManager.beginTransaction().replace(R.id.fragment_container, new EventsFragment()).addToBackStack(null).commit();
//            }
        }
    }
}
