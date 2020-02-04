package com.bsj.informatieapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


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

        NewsManager newsManager = new NewsManager();
        News article = new News("nieuws_afbeelding", "aaaaaaaaaaaaaaa", "http://google.com", "NU.nl");
        newsManager.addArticle(article);

        ((MyApplication)getApplication()).setNewsManager(newsManager);

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
                            selectedFragment = new EventsFragment();
                            item.setChecked(true);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                    return true;
                }
            };
}
