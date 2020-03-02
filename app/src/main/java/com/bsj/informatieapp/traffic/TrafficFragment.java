package com.bsj.informatieapp.traffic;

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
import com.bsj.informatieapp.weather.WeatherRecyclerViewAdapter;

public class TrafficFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traffic, container, false);
        initializeRecyclerView();

        return view;
    }

    private void initializeRecyclerView(){
        TrafficViewModel model = ViewModelProviders.of(requireActivity()).get(TrafficViewModel.class);

        model.getTraffic(getContext()).observe(this,traffic -> {
            RecyclerView recyclerView = view.findViewById(R.id.traffic_recyclerview);
            TrafficRecyclerViewAdapter adapter = new TrafficRecyclerViewAdapter(traffic);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        });
    }
}
