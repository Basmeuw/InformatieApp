package com.bsj.informatieapp.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bsj.informatieapp.R;
import com.bsj.informatieapp.news.News;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<News> {

    private Context context;
    private int resource;

    public NewsListAdapter(Context context, int resource, ArrayList<News> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
      //  String imageFilename = getItem(position).getImageFilename();
     //   String title = getItem(position).getTitle();
     //   String link = getItem(position).getLink();
     //   String source = getItem(position).getSource();

        //News article = new News(imageFilename, title, link, source);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.adapter_title);
        TextView sourceTextView = (TextView) convertView.findViewById(R.id.adapter_source);

        //titleTextView.setText(title);
    //    sourceTextView.setText(source);

        return convertView;
    }
}
