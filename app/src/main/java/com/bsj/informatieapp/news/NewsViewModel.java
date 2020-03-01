package com.bsj.informatieapp.news;

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
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

//https://medium.com/@elye.project/understanding-live-data-made-simple-a820fcd7b4d0

public class NewsViewModel extends ViewModel {

    private MutableLiveData<News[]> newsArticles;

    public LiveData<News[]> getAllNewsArticles(Context context){
        checkIfNewsLoaded(context);
        return newsArticles;
    }

    private void checkIfNewsLoaded(Context context){
        if(newsArticles == null){
            newsArticles = new MutableLiveData<>();
            loadNews(context);
        }
    }

    private void loadNews(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://schondeln.eitilop-bali.nl/get.php?category[0]=News";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Log.e("customdebug", response.toString());
                        News[] news = gson.fromJson(response.toString(), News[].class);
                        newsArticles.postValue(news);
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
