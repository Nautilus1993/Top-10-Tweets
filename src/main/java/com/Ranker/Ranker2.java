package com.Ranker;

import com.Data.KeyWord;
import com.Data.TweetUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Data.KeyWord.ExtractKeyWord;

/**
 * Model 2: Text-Based Analysis
 * 1. Based on tweets text contents, find the most representative keywords.
 */
public class Ranker2 {
    String username;

    public Ranker2(String username){
        System.out.println(username);
        this.username = username;
    }

    public List<String> findKeyWords(List<TweetUnit> tlist) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> results = new ArrayList<>();
        List<KeyWord> keywords;
        for(TweetUnit tweet: tlist){
            sb.append(tweet.text + " ");
        }
        //String test = "";
        keywords = ExtractKeyWord(sb.toString());
        List<String> result = new ArrayList<>();

        for(KeyWord keyWord: keywords){
            if(keyWord.getStem().length() > 1){
                result.add(keyWord.getStem());
            }
        }
        System.out.println("Keyword Number : " + result.size());
        for(String word: result){
            System.out.println(word);
        }
        return results;
    }
}
