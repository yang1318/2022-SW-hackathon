package com.example.Colorful_Daegu.model;

public class Reply {
    private String contents;
    private String time;

    public Reply(){}
    public Reply(String contents, String time) {
        this.contents = contents;
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
