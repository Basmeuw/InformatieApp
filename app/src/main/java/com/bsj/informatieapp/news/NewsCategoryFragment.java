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
import android.widget.TextView;

import com.bsj.informatieapp.R;

import org.w3c.dom.Text;

public class NewsCategoryFragment extends Fragment {

    String category;

    View view;
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_news_category, container, false);

        TextView title = view.findViewById(R.id.news_category_textview);
        title.setText(category);

        webView = view.findViewById(R.id.webview_category);


        NewsViewModel newsViewModel = ViewModelProviders.of(requireActivity()).get(NewsViewModel.class);

        newsViewModel.getAllNewsArticles(getContext()).observe(this, this::initializeRecyclerView);

        return view;
    }

    public void initializeRecyclerView(News[] news){
        RecyclerView recyclerViewLokaal = view.findViewById(R.id.news_category_recyclerview);
        NewsRecyclerViewAdapter adapterLokaal = new NewsRecyclerViewAdapter(News.filterNews(news, category), webView, this.getContext());
        recyclerViewLokaal.setAdapter(adapterLokaal);
        recyclerViewLokaal.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onStart() {

        super.onStart();

        Bundle bundle = this.getArguments();
        if(bundle != null){
            category = bundle.getString("category");
        }
    }

}
