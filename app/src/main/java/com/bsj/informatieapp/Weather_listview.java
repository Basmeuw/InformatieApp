package com.bsj.informatieapp;

import android.os.Bundle;
import android.app.ListActivity;
import android.widget.TextView;
import android.view.View;
import java.util.Calendar;
import java.util.Date;

/*

public class Weather_listview extends ListActivity {
    public TextView textViewDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_customlistview);

        textViewDate = findViewById(R.id.text_view_date);
    }
    public void getDate(View v) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();

        c.add(Calendar.DATE, 3);
        c.add(Calendar.MONTH, 1);
        Date future = c.getTime();

        textViewDate.setText("Today: " + today + "\n" + "Future: " + future);
    }
}
 */
