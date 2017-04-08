package com.Ranker;

import com.Data.KeyWord;
import com.Data.TweetUnit;

import java.io.IOException;
import java.util.*;

import static com.Data.KeyWord.ExtractKeyWord;

/**
 * Model 2: Text-Based Analysis
 * 1. Based on tweets text contents, find the most representative keywords.
 * 2. Based on top-25 key words, find top-10 representative tweets.
 */
public class Ranker2 {
    String username;
    HashSet<KeyWord> top25;
    PriorityQueue<TweetUnit> heap;  // store the tweets with higher score

    public Ranker2(String username){
        System.out.println(username);
        this.username = username;
        this.top25 = new HashSet<>();
        this.heap = new PriorityQueue<>(new Comparator<TweetUnit>() {
            @Override
            public int compare(TweetUnit o1, TweetUnit o2) {
                return o1.score - o2.score;
            }
        });
    }

    public void findKeyWords(List<TweetUnit> tlist) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> results = new ArrayList<>();
        List<KeyWord> keywords;
        for(TweetUnit tweet: tlist){
            sb.append(tweet.text + " ");
        }
        keywords = ExtractKeyWord(sb.toString());
        List<String> result = new ArrayList<>();

        int count = 0;
        for(KeyWord keyword: keywords) {
            if (keyword.getStem().length() > 1) {
                result.add(keyword.getStem());
                count++;
                if (count == 25) {
                    top25.add(keyword);
                    break;
                }
            }
        }
    }

    public void CalRepresentative(TweetUnit tweet) throws IOException {
        List<KeyWord> words = ExtractKeyWord(tweet.text);
        tweet.score = 0;
        for(KeyWord word: words){
             if(top25.contains(word)){
                 tweet.score++;
             }
        }
    }

    public List<TweetUnit> findTop10(List<TweetUnit> tweetUnitList) throws IOException {

        findKeyWords(tweetUnitList);

        List<TweetUnit> results = new ArrayList<>();
        for(TweetUnit tweetUnit: tweetUnitList){
            CalRepresentative(tweetUnit);

            if(heap.size() == 10){
                if(heap.peek().score < tweetUnit.score){
                    heap.poll();
                    heap.add(tweetUnit);
                }
            } else {
                heap.add(tweetUnit);
            }
        }
        while(!heap.isEmpty()){
            results.add(0, heap.poll());
        }

        for(TweetUnit tweetUnit: results){
            System.out.println("@" + tweetUnit.username +
            "\n - content:  " + tweetUnit.text);
        }
        return results;
    }
}
