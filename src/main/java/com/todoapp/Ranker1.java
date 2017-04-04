package com.todoapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static com.todoapp.Bootstrap.TOP_K;


/**
 * Model 1: Favorite & Retweet amount-based
 * Use a fixed-size heap to store top-10 tweets
 */
public class Ranker1 {
    String username;
    PriorityQueue<TweetUnit> topK;

    public Ranker1(String username){
        this.username = username;
        topK = new PriorityQueue<>(new Comparator<TweetUnit>() {
            @Override
            public int compare(TweetUnit o1, TweetUnit o2) {
                return o1.favorite - o2.favorite;
            }
        });
    }

    public List<TweetUnit> findTop10(List<TweetUnit> tlist){
        int size = 0;
        for(TweetUnit tweet : tlist){
            if(size < TOP_K){
                topK.add(tweet);
                size++;
            } else {
                if(topK.peek().favorite > tweet.favorite){
                    continue;
                } else{
                    topK.poll();
                    topK.add(tweet);
                }
            }
        }

        List<TweetUnit> result = new ArrayList<>();
        while(!topK.isEmpty()){
            result.add(0, topK.poll());
        }

        for (TweetUnit tu: result){
            System.out.println("@" + tu.username
                    + " - favorite " + Integer.toString(tu.favorite)
                    + " - Retweet" + Integer.toString(tu.retweet)
                    + "\n - content: " + tu.text);
        }
        return result;
    }
}
