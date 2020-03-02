package com.bsj.informatieapp.news;

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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.weather.WeatherRecyclerViewAdapter;
import com.bsj.informatieapp.weather.WeatherViewModel;

public class NewsFragment extends Fragment {

    private View view;
    private WebView webView;
    private Button[] leesMeerButtons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        webView = view.findViewById(R.id.news_webView);
        leesMeerButtons = new Button[2];
        leesMeerButtons[0] = view.findViewById(R.id.news_readmore_lokaal);
        leesMeerButtons[1] = view.findViewById(R.id.news_readmore_landelijk);

        NewsViewModel newsViewModel = ViewModelProviders.of(requireActivity()).get(NewsViewModel.class);
        newsViewModel.getAllNewsArticles(getContext()).observe(this, news -> {
            setupRecyclerView(news);
        });

        return  view;
    }

    private void setupRecyclerView(News[] news){

        RecyclerView recyclerViewLokaal = view.findViewById(R.id.news_recyclerview_lokaal);
        NewsRecyclerViewAdapter adapterLokaal = new NewsRecyclerViewAdapter(News.filterNews(news, "lokaal"), leesMeerButtons, webView);
        recyclerViewLokaal.setAdapter(adapterLokaal);
        recyclerViewLokaal.setLayoutManager(new LinearLayoutManager(this.getContext()));

        RecyclerView recyclerViewAlgemeen = view.findViewById(R.id.news_recyclerview_landelijk);
        NewsRecyclerViewAdapter adapterAlgemeen = new NewsRecyclerViewAdapter(News.filterNews(news, "algemeen"), leesMeerButtons, webView);
        recyclerViewAlgemeen.setAdapter(adapterAlgemeen);
        recyclerViewAlgemeen.setLayoutManager(new LinearLayoutManager(this.getContext()));


    }
}
