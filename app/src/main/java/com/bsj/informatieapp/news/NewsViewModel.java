package com.bsj.informatieapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

//https://medium.com/@elye.project/understanding-live-data-made-simple-a820fcd7b4d0

public class NewsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<News>> newsArticles;


    public LiveData<ArrayList<News>> getAllNewsArticles(){
        checkIfNewsLoaded();
        return newsArticles;
    }



    private void checkIfNewsLoaded(){
        if(newsArticles == null){
            newsArticles = new MutableLiveData<>();
            loadNews();
        }
    }

    private void loadNews(){
        ArrayList<News> articles = new ArrayList<>();
        // Make HTTP request
        News article = new News("nieuws_afbeelding", "aaaaaaaaaaaaaaa", "http://google.com", "NU.nl");
        articles.add(article);

        newsArticles.setValue(articles);
    }
}
