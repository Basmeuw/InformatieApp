package com.bsj.informatieapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<News>> newsArticles;

    LiveData<ArrayList<News>> getAllNewsArticles(){
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
