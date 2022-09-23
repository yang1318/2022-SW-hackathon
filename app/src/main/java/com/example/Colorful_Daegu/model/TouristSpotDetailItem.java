package com.example.Colorful_Daegu.model;

public class TouristSpotDetailItem {
    String pictureUrl;
    String name;
    int success;

    public TouristSpotDetailItem(String picture, String name, int success) {
        this.pictureUrl = picture;
        this.name = name;
        this.success = success;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
