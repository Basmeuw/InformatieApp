package com.bsj.informatieapp.news;

import java.util.ArrayList;
import java.util.List;

public class News {

    public String title;
    public String link;
    public String description;
    public String region;
    public String imageLink;
    public String pubDate;
    public String category;
    public String krant;

    public static News[] filterNews(News[] news, String filter){
        List<News> newsList = new ArrayList<News>();
        for(News newsItem : news){

            if(newsItem.category.equals(filter)){
                newsList.add(newsItem);
            }
        }
        News[] newsArray = new News[newsList.size()];
        newsArray = newsList.toArray(newsArray);

        return newsArray;
    }


//    public News(String imageFilename, String title, String link, String source) {
//        this.imageFilename = imageFilename;
//        this.title = title;
//        this.link = link;
//        this.source = source;
//    }
//
//    public String getImageFilename() {
//        return imageFilename;
//    }
//
//    public void setImageFilename(String imageFilename) {
//        this.imageFilename = imageFilename;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
}
