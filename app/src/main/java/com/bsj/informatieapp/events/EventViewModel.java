package com.bsj.informatieapp.events;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bsj.informatieapp.news.News;
import com.google.gson.Gson;

import org.json.JSONArray;

public class EventViewModel extends ViewModel {

    private MutableLiveData<Event[]> events;

    public LiveData<Event[]> getAllEvents(Context context) {
        checkIfNewsLoaded(context);
        return events;
    }

    private void checkIfNewsLoaded(Context context) {
        if (events == null) {
            events = new MutableLiveData<>();
            loadNews(context);
        }
    }

    private void loadNews(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://schondeln.eitilop-bali.nl/get.php?category[0]=Events";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Log.e("customdebug", response.toString());
                        Event[] event = gson.fromJson(response.toString(), Event[].class);
                        events.postValue(event);
                        //Log.e("customdebug", newsArticles.getValue()[0].title);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("customdebug", "Mission failed! We'll get em next time.");
            }
        });
        queue.add(stringRequest);
    }


}
