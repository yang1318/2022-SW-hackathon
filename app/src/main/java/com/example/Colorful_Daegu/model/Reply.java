package com.example.Colorful_Daegu.model;

public class Reply {
    private String contents;
    private long time;

    public Reply(){}
    public Reply(String contents, long time) {
        this.contents = contents;
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
