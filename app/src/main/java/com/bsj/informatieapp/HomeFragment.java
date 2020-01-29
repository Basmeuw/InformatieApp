package com.bsj.informatieapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        ListView newsView = (ListView) v.findViewById(R.id.NewsListView);

//        ArrayList<News> newsList = new ArrayList<>();
//        newsList.add(new News("nieuws_afbeelding",
//                "Aantal misdrijven stijgt weer: zo crimineel is jouw buurt",
//                "https://www.telegraaf.nl/nieuws/1730275666/aantal-misdrijven-stijgt-weer-zo-crimineel-is-jouw-buurt",
//                "Telegraaf"));
//
//        newsList.add(new News("nieuws_afbeelding",
//                "Aantal misdrijven stijgt weer: zo crimineel is jouw buurt 2",
//                "https://www.telegraaf.nl/nieuws/1730275666/aantal-misdrijven-stijgt-weer-zo-crimineel-is-jouw-buurt",
//                "Telegraaf"));
//
//        NewsListAdapter adapter = new NewsListAdapter(getContext(), R.layout.adapter_view_layout, newsList);
//        newsView.setAdapter(adapter);



        return v;
    }
}
