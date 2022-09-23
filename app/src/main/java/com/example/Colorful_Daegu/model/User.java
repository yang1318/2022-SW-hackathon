package com.example.Colorful_Daegu.model;

import java.util.HashMap;

public class User {
    private String name;
    private int stampCount;

    public User(){}
    public User(String name, int stampCount) {
        this.name = name;
        this.stampCount = stampCount;
    }

    public int getStampCount() {
        return stampCount;
    }

    public void setStampCount(int stampCount) {
        this.stampCount = stampCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
