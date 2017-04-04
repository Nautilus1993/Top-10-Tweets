package com.todoapp;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.*;

public class RankerService {
    private final DB db;
    private final DBCollection collection;

    public RankerService(DB db){
        this.db = db;
        this.collection = db.getCollection("tweets");
    }

    public List<TweetUnit> rank(String username){
        List<TweetUnit> top10 = new ArrayList<>();
        Collector col = new Collector(username);
        Ranker1 ranker = new Ranker1(username);
        top10 = ranker.findTop10(col.getTweets());
    }
}
