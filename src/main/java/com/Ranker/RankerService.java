package com.Ranker;

import com.Data.Collector;
import com.Data.TweetUnit;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.io.IOException;
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
        return top10;
    }

    public void rank2(String username) throws IOException {
        Collector col = new Collector(username);
        Ranker2 ranker2 = new Ranker2(username);
        ranker2.findKeyWords(col.getTweets());
        return;
    }
}
