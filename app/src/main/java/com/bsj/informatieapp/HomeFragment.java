package com.bsj.informatieapp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bsj.informatieapp.events.EventsFragment;
import com.bsj.informatieapp.news.NewsFragment;
import com.bsj.informatieapp.news.NewsManager;
import com.bsj.informatieapp.news.NewsViewModel;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private NewsManager newsManager;
    private NewsViewModel newsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        // News handling

        View newsItem = v.findViewById(R.id.newsItem1);
        TextView newsText = newsItem.findViewById(R.id.adapter_title);
        TextView newsSource = newsItem.findViewById(R.id.adapter_source);
        ImageView newsImage = newsItem.findViewById(R.id.adapter_thumbnail);
        Button newsReadMore = v.findViewById(R.id.home_newsReadMore);
        newsReadMore.setOnClickListener(this);

//        newsViewModel.getAllNewsArticles().observe();


        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_newsReadMore:{
                // Load news page
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new NewsFragment()).addToBackStack(null).commit();
            }
//            case R.id.home_eventsReadMore:{
//                // Load events page
//                fragmentManager.beginTransaction().replace(R.id.fragment_container, new EventsFragment()).addToBackStack(null).commit();
//            }
        }
    }
}
