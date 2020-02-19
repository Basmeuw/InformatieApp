package com.bsj.informatieapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bsj.informatieapp.events.EventsFragment;
import com.bsj.informatieapp.news.News;
import com.bsj.informatieapp.news.NewsFragment;
import com.bsj.informatieapp.news.NewsManager;
import com.bsj.informatieapp.weather.WeatherFragment;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_start);

        //
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://schondeln.eitilop-bali.nl/get.php?category[0]=weather&category[1]=traffic";
//        //136.144.231.50
//        //http://schondeln.eitilop-bali.nl/get.php?category[0]=weather&category[1]=traffic
//        Log.e("customdebug", "test");
//        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // Display the first 500 characters of the response string.
//                        Log.e("customdebug", "Response is: "+ response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Log.e("customdebug", "Mission failed! We'll get em next time.");
//            }
//        });
//        queue.add(stringRequest);



        // Open the start page
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_start:
                            selectedFragment = new HomeFragment();
                            item.setChecked(true);
                            break;
                        case R.id.nav_nieuws:
                            selectedFragment = new NewsFragment();
                            item.setChecked(true);
                            break;
                        case R.id.nav_events:
                            selectedFragment = new WeatherFragment();
                            item.setChecked(true);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                    return true;
                }
            };
}
