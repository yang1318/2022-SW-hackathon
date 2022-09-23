package com.example.Colorful_Daegu.model;

public class Post {
    private String pictureUrl;
    private int recommendNum;
    private String userId;

    public Post(){}
    public Post(String pictureUrl, int recommendNum, String userId) {
        this.pictureUrl = pictureUrl;
        this.recommendNum = recommendNum;
        this.userId = userId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(int recommendNum) {
        this.recommendNum = recommendNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
