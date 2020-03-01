package com.bsj.informatieapp.weather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

import androidx.annotation.RequiresApi;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<Weather[]> weatherData;
    public LiveData<Weather[]> getWeather(Context context){
        if(weatherData == null){
            weatherData = new MutableLiveData<>();
            loadWeatherDB(context);
        }
        return weatherData;
    }

    private void loadWeatherDB(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
            String url ="http://schondeln.eitilop-bali.nl/get.php?category[0]=Weather";

            JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            convertWeather(response);

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

    private void convertWeather(JSONArray data){

        Gson gson = new Gson();
        Weather[] weatherArray = gson.fromJson(data.toString(),Weather[].class);
        weatherData.postValue(weatherArray);

    }

}
