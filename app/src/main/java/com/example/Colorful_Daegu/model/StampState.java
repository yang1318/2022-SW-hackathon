package com.example.Colorful_Daegu.model;

import java.util.ArrayList;
import java.util.HashMap;

public class StampState {
    private HashMap<String, ArrayList<Integer>> stampState;

    public StampState(){}

    public StampState(HashMap<String, ArrayList<Integer>> stampState) {
        this.stampState = stampState;
    }

    public HashMap<String, ArrayList<Integer>> getStampState() {
        return stampState;
    }

    public void setStampState(HashMap<String, ArrayList<Integer>> stampState) {
        this.stampState = stampState;
    }
}
