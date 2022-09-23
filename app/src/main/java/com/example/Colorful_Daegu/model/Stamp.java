package com.example.Colorful_Daegu.model;

import java.util.ArrayList;

public class Stamp {
    private String name;
    private String id;
    private int index;
    private String pictureUrl;
    private String description;
    private ArrayList<Reply> replys;
    private Challenge challenge;


    public Stamp(){}

    public Stamp(String name, String id, int index, String pictureUrl, String description,
                 ArrayList<Reply> replys, Challenge challenge) {
        this.name = name;
        this.id = id;
        this.index = index;
        this.pictureUrl = pictureUrl;
        this.description = description;
        this.replys = replys;
        this.challenge = challenge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Reply> getReplys() {
        return replys;
    }

    public void setReplys(ArrayList<Reply> replys) {
        this.replys = replys;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
