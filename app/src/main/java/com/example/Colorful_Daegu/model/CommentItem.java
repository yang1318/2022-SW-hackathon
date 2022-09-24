package com.example.Colorful_Daegu.model;

public class CommentItem {
    String comment ;
    String time;

    public CommentItem(){}
    public CommentItem(String comment, String time) {
        this.comment = comment;
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
