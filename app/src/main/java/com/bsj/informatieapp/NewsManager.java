package com.bsj.informatieapp;

import android.util.Log;

import java.util.ArrayList;

public class NewsManager {
    private ArrayList<News> articles;

    public NewsManager(){
        articles = new ArrayList<>();
    }

    public News getMostImportantArticle(){
        if(articles.size() != 0){
            return articles.get(0);
        }else{
            Log.e("custom", "no articles found");
            return null;
        }
    }

    public ArrayList<News> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<News> articles) {
        this.articles = articles;
    }

    public void addArticle(News article){
        articles.add(article);
    }
}
