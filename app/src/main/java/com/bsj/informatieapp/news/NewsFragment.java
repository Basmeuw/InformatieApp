package com.bsj.informatieapp.news;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.weather.WeatherRecyclerViewAdapter;
import com.bsj.informatieapp.weather.WeatherViewModel;
import com.squareup.picasso.Picasso;

public class NewsFragment extends Fragment {

    private View view;
    private WebView webView;
    private Button leesMeerButton1;
    private Button leesMeerButton2;
    private View lokaalItem1;
    private View lokaalItem2;
    private View landelijkItem1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        webView = view.findViewById(R.id.news_webView);

        lokaalItem1 = view.findViewById(R.id.news_lokaalItem1);
        lokaalItem2 = view.findViewById(R.id.news_lokaalItem2);
        landelijkItem1 = view.findViewById(R.id.news_landelijkItem1);

        leesMeerButton1 = view.findViewById(R.id.news_readmore_lokaal);
        leesMeerButton2 = view.findViewById(R.id.news_readmore_landelijk);

        leesMeerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsLokaalFragment()).addToBackStack(null).commit();
            }
        });

        leesMeerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsLandelijkFragment()).addToBackStack(null).commit();
            }
        });

        NewsViewModel newsViewModel = ViewModelProviders.of(requireActivity()).get(NewsViewModel.class);
        newsViewModel.getAllNewsArticles(getContext()).observe(this, news -> {
            fillNews(news);
        });

        return  view;
    }

    private void fillNews(News[] news){
        News[] lokaalNews = News.filterNews(news, "lokaal");
        News[] landelijkNews = News.filterNews(news, "landelijk");

        try {
            TextView lokaalText1 = lokaalItem1.findViewById(R.id.adapter_title);
            TextView lokaalSource1 = lokaalItem1.findViewById(R.id.adapter_source);
            ImageView lokaalImage1 = lokaalItem1.findViewById(R.id.adapter_thumbnail);
            Picasso.with(getContext()).load(lokaalNews[0].imageLink).into(lokaalImage1);
            lokaalText1.setText(lokaalNews[0].title);
            lokaalSource1.setText(lokaalNews[0].krant);
            lokaalItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(lokaalNews[0].link);
                    webView.setVisibility(View.VISIBLE);
                    leesMeerButton1.setVisibility(View.INVISIBLE);
                    leesMeerButton2.setVisibility(View.INVISIBLE);
                }
            });


            TextView lokaalText2 = lokaalItem2.findViewById(R.id.adapter_title);
            TextView lokaalSource2 = lokaalItem2.findViewById(R.id.adapter_source);
            ImageView lokaalImage2 = lokaalItem2.findViewById(R.id.adapter_thumbnail);
            Picasso.with(getContext()).load(lokaalNews[1].imageLink).into(lokaalImage2);
            lokaalText2.setText(lokaalNews[1].title);
            lokaalSource2.setText(lokaalNews[1].krant);
            lokaalItem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(lokaalNews[1].link);
                    webView.setVisibility(View.VISIBLE);
                    leesMeerButton1.setVisibility(View.INVISIBLE);
                    leesMeerButton2.setVisibility(View.INVISIBLE);
                }
            });
        }catch(Exception e){
            Log.e("customdebug", "GEEN LOKALE ARTIKELEN");
            e.printStackTrace();
        }

        try {
            TextView landelijkText1 = landelijkItem1.findViewById(R.id.adapter_title);
            TextView landelijkSource1 = landelijkItem1.findViewById(R.id.adapter_source);
            ImageView landelijkImage1 = landelijkItem1.findViewById(R.id.adapter_thumbnail);
            Picasso.with(getContext()).load(landelijkNews[0].imageLink).into(landelijkImage1);
            landelijkText1.setText(landelijkNews[0].title);
            landelijkSource1.setText(landelijkNews[0].krant);
            landelijkItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(landelijkNews[0].link);
                    webView.setVisibility(View.VISIBLE);
                    leesMeerButton1.setVisibility(View.INVISIBLE);
                    leesMeerButton2.setVisibility(View.INVISIBLE);
                }
            });
        }catch(Exception e){
            Log.e("customdebug", "GEEN LOKAAL NIEUWS");
            e.printStackTrace();
        }
    }

//    private void setupRecyclerView(News[] news){
//
//        RecyclerView recyclerViewLokaal = view.findViewById(R.id.news_recyclerview_lokaal);
//        NewsRecyclerViewAdapter adapterLokaal = new NewsRecyclerViewAdapter(News.filterNews(news, "lokaal"), leesMeerButton1, leesMeerButton2, webView, this.getContext());
//        recyclerViewLokaal.setAdapter(adapterLokaal);
//        recyclerViewLokaal.setLayoutManager(new LinearLayoutManager(this.getContext()));
//
//        RecyclerView recyclerViewAlgemeen = view.findViewById(R.id.news_recyclerview_landelijk);
//        NewsRecyclerViewAdapter adapterAlgemeen = new NewsRecyclerViewAdapter(News.filterNews(news, "algemeen"), leesMeerButton1, leesMeerButton2, webView, this.getContext());
//        recyclerViewAlgemeen.setAdapter(adapterAlgemeen);
//        recyclerViewAlgemeen.setLayoutManager(new LinearLayoutManager(this.getContext()));
//
//
//    }
}
