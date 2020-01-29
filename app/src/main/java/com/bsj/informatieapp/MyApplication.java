package com.bsj.informatieapp;

import android.app.Application;

public class MyApplication extends Application {
    private NewsManager newsManager;

    public NewsManager getNewsManager() {
        return newsManager;
    }

    public void setNewsManager(NewsManager newsManager) {
        this.newsManager = newsManager;
    }
}
