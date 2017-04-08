package com.Data;

public class TweetUnit {
    public String username;
    public int favorite;
    public int retweet;
    public String text;
    public int score; // used for Ranker2

    public TweetUnit(String username, int favorite, int retweet, String text){
        this.username = username;
        this.favorite = favorite;
        this.retweet = retweet;
        this.text = text;
        this.score = 0;
    }
}
