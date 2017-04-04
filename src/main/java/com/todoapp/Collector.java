package com.todoapp;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * This class used for collect user's ariginal data given a username.
 */
public class Collector {
    String username;
    public static String consumerKey = "U4X8F1eGfMLvTr1JStHtyY1qM";
    public static String consumerSecret = "KL9oYJ8qMQP3Rxul10Sk0FccRZCbdVwRAFewOgjyNi7xPhnmaS";
    public static String accessToken = "773970732913131522-23qwfpivVrSgEzN7nKZPtevCNwUCLBC";
    public static String accessSecretToken = "c0KUhFwA6M5IQe9jnGAXXxkOXUoS8L3iClNmyS982r6ji";


    public Collector(String uname){
        this.username = uname;
    }


    public List<TweetUnit> getTweets(){
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.setDebugEnabled(true);
        config.setOAuthAccessToken(accessToken);
        config.setOAuthAccessTokenSecret(accessSecretToken);
        config.setOAuthConsumerKey(consumerKey);
        config.setOAuthConsumerSecret(consumerSecret);
        TwitterFactory tf = new TwitterFactory(config.build());
        Twitter twitter = tf.getInstance();

        List<TweetUnit> datas = new ArrayList<>();
        try {
            List<Status> statuses = twitter.getUserTimeline(username);
            System.out.println("Showing @" + username + "'s home timeline") ;
            for (Status status: statuses){
//                System.out.println("@" + status.getUser().getScreenName() + " - "
//                        + " Favorated number: " + Integer.toString(status.getRetweetCount()) + " - "
//                        + " Retweeted number: " + Integer.toString(status.getFavoriteCount()) + " - "
//                        + "\n - " + status.getText());
                TweetUnit tweet = new TweetUnit(
                        status.getUser().getScreenName(),
                        status.getFavoriteCount(),
                        status.getRetweetCount(),
                        status.getText());
                datas.add(tweet);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Failed to get timeline:" + e.getMessage());
            System.exit(-1);
        }
        return datas;
    }
}
