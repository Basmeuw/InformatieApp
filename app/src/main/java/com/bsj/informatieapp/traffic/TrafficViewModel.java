package com.bsj.informatieapp.traffic;

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
import com.bsj.informatieapp.events.Event;
import com.google.gson.Gson;

import org.json.JSONArray;

public class TrafficViewModel extends ViewModel {
    private MutableLiveData<Traffic[]> traffic;

    public LiveData<
            Traffic[]> getAllTraffic(Context context) {
        checkIfNewsLoaded(context);
        return traffic;
    }

    private void checkIfNewsLoaded(Context context) {
        if (traffic == null) {
            traffic = new MutableLiveData<>();
            loadNews(context);
        }
    }

    private void loadNews(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://schondeln.eitilop-bali.nl/get.php?category[0]=Traffic";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Log.e("customdebug", response.toString());
                        Traffic[] trafficArray = gson.fromJson(response.toString(), Traffic[].class);
                        traffic.postValue(trafficArray);
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
