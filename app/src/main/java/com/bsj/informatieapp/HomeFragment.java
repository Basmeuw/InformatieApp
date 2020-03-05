package com.bsj.informatieapp;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ImageView;
import android.widget.TextView;


import com.bsj.informatieapp.events.EventViewModel;
import com.bsj.informatieapp.events.EventsFragment;
import com.bsj.informatieapp.news.NewsFragment;
import com.bsj.informatieapp.news.NewsViewModel;
import com.bsj.informatieapp.traffic.TrafficFragment;
import com.bsj.informatieapp.traffic.TrafficViewModel;
import com.bsj.informatieapp.weather.WeatherFragment;
import com.bsj.informatieapp.weather.WeatherViewModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private NewsViewModel newsViewModel;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();



        initializeWeather();
        initializeNews();
        initializeTraffic();
        initializeEvents();

        //newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

//        newsViewModel.getAllNewsArticles().observe(getViewLifecycleOwner(), new Observer<ArrayList<News>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList<News> newsArticles) {
//                News mostImportant = newsArticles.get(0);
//                newsText.setText(mostImportant.getTitle());
//                newsSource.setText(mostImportant.getSource());
////                newsItem.setOnClickListener(this);
//            }
//        });


        // News handling



//        newsViewModel.getAllNewsArticles().observe();


        return view;
    }

    private void initializeEvents(){
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

        EventViewModel model = ViewModelProviders.of(requireActivity()).get(EventViewModel.class);
        model.getAllEvents(getContext()).observe(this, events -> {
            eventName.setText(events[0].name);
            eventLocation.setText(events[0].place);
            eventTime.setText(events[0].startTime + " tot " + events[0].endTime);
            eventDate.setText(events[0].date);
            eventAttendees.setText(events[0].attendingCount + " bezoekers");
            eventSource.setText(events[0].informationSource);
            eventDay.setText(events[0].getEventDay());
            eventMonth.setText(events[0].getEventMonth());
            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new EventsFragment()).addToBackStack(null).commit();
                }
            });
        });
    }

    private void initializeTraffic(){
        final ConstraintLayout trafficItem = view.findViewById(R.id.TrafficLayout);
        final TextView road = view.findViewById(R.id.home_file_weg);
        final TextView time = view.findViewById(R.id.home_file_time);
        final TextView length = view.findViewById(R.id.home_file_length);


        TrafficViewModel model = ViewModelProviders.of(requireActivity()).get(TrafficViewModel.class);
        model.getTraffic(getContext()).observe(this,traffic -> {
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
            temp.setText(weather[0].temp - 273+ "\u00B0" + "C");
            rain.setText(weather[0].rain + "mm");
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
