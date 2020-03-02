package com.bsj.informatieapp.news;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private News[] news;
    private Button[] buttons;
    private WebView webView;

    public NewsRecyclerViewAdapter(News[] news, Button[] buttons, WebView webView){
        this.news = news;
        this.webView = webView;
        this.buttons = buttons;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_view_news, viewGroup, false);


        NewsViewHolder viewHolder = new NewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder viewHolder, int i) {

            viewHolder.title.setText(news[i].title);
            viewHolder.source.setText(news[i].krant);
            viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(news[i].link);
                    webView.setVisibility(View.VISIBLE);
                    for(Button button : buttons){
                        button.setVisibility(View.INVISIBLE);
                    }
                }
            });



    }

    @Override
    public int getItemCount() {
        return news.length;
    }








    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView source;
        ImageView image;
        ConstraintLayout constraintLayout;

        public NewsViewHolder(@NonNull View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.adapter_title);
            source = itemView.findViewById(R.id.adapter_source);
            image = itemView.findViewById(R.id.adapter_thumbnail);
            constraintLayout = itemView.findViewById(R.id.adapter_constraint_layout);

        }
    }
}
