package com.bsj.informatieapp;

import android.util.Log;

public class NewsManager {
    private News[] articles;

    public News getMostImportantArticle(){
        if(articles.length != 0){
            return articles[0];
        }else{
            Log.e("custom", "no articles found");
            return null;
        }
    }

    public News[] getArticles() {
        return articles;
    }

    public void setArticles(News[] articles) {
        this.articles = articles;
    }
}
