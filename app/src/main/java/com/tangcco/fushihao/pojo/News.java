package com.tangcco.fushihao.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/18.
 */
public class News implements Serializable {
    private int newsId;
    private String newsTitle;
    private String newsContent;
    private NewsType newsType;
    private NewsUser newsUser;
    private int newsCount;
    private NewsImage newsImage;
    private int news_state;
    private String newsAbstract;
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public NewsUser getNewsUser() {
        return newsUser;
    }

    public void setNewsUser(NewsUser newsUser) {
        this.newsUser = newsUser;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public NewsImage getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(NewsImage newsImage) {
        this.newsImage = newsImage;
    }

    public int getNews_state() {
        return news_state;
    }

    public void setNews_state(int news_state) {
        this.news_state = news_state;
    }

    public String getNewsAbstract() {
        return newsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        this.newsAbstract = newsAbstract;
    }

    public News(){}
}
