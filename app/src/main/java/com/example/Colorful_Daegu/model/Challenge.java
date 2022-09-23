package com.example.Colorful_Daegu.model;

import com.example.Colorful_Daegu.model.Post;

import java.util.ArrayList;
import java.util.HashMap;

public class Challenge {
    private String description;
    private ArrayList<Post> post;


    public Challenge(){}

    public Challenge(String description, ArrayList<Post> post) {
        this.description = description;
        this.post = post;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<Post> getPost() {
        return post;
    }

    public void setPost(ArrayList<Post> post) {
        this.post = post;
    }
}
