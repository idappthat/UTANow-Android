package com.mobi.utanow.models;

/**
 * Created by Kevin on 2/26/2016.
 */
public class Comment {
    String comment, author;

    public Comment(String comment, String author){
        this.comment = comment;
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
