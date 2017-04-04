package com.todoapp;

public class TweetUnit {
    public String username;
    public int favorite;
    public int retweet;
    public String text;

    public TweetUnit(String username, int favorite, int retweet, String text){
        this.username = username;
        this.favorite = favorite;
        this.retweet = retweet;
        this.text = text;
    }
}
