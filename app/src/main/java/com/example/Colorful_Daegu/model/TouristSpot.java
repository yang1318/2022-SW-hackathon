package com.example.Colorful_Daegu.model;

import java.util.ArrayList;

public class TouristSpot {
    private String name;
    private Location location;
    private String pictureUrl;
    private String rating;
    private String description;
    private ArrayList<Stamp> stamps;

    public TouristSpot(){}

    public TouristSpot(String name, Location location, String pictureUrl, String rating,
                       String description, ArrayList<Stamp> stamps) {
        this.name = name;
        this.location = location;
        this.pictureUrl = pictureUrl;
        this.rating = rating;
        this.description = description;
        this.stamps = stamps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(ArrayList<Stamp> stamps) {
        this.stamps = stamps;
    }
}
