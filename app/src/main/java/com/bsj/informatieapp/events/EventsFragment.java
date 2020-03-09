package com.bsj.informatieapp.events;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.weather.Weather;
import com.bsj.informatieapp.weather.WeatherRecyclerViewAdapter;
import com.bsj.informatieapp.weather.WeatherViewModel;

public class EventsFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);

        EventViewModel eventViewModel = ViewModelProviders.of(requireActivity()).get(EventViewModel.class);
        WeatherViewModel weatherViewModel = ViewModelProviders.of(requireActivity()).get(WeatherViewModel.class);
        eventViewModel.getAllEvents(getContext()).observe(this, events -> {
            weatherViewModel.getWeather(getContext()).observe(this, weather -> {
                initializeRecyclerview(events, weather);
            });

        });

        return view;
    }


    public void initializeRecyclerview(Event[] events, Weather[] weather){
        RecyclerView recyclerView = view.findViewById(R.id.events_recyclerview);
        EventRecyclerView adapter = new EventRecyclerView(events, weather);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}

