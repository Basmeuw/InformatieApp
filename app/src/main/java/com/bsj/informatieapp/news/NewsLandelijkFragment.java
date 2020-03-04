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

import com.bsj.informatieapp.R;

public class NewsLandelijkFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_landelijk, container, false);

        WebView webView = v.findViewById(R.id.webview_lokaal);

        NewsViewModel newsViewModel = ViewModelProviders.of(requireActivity()).get(NewsViewModel.class);
        newsViewModel.getAllNewsArticles(getContext()).observe(this, news -> {
            RecyclerView recyclerViewLokaal = v.findViewById(R.id.news_landelijk_recyclerview);
            NewsRecyclerViewAdapter adapterLokaal = new NewsRecyclerViewAdapter(News.filterNews(news, "landelijk"), webView, this.getContext());
            recyclerViewLokaal.setAdapter(adapterLokaal);
            recyclerViewLokaal.setLayoutManager(new LinearLayoutManager(this.getContext()));
        });

        return v;
    }
}
