package com.Ranker;

import com.Data.Collector;
import com.Data.TweetUnit;
import com.mongodb.*;

import java.io.IOException;
import java.util.*;

public class RankerService {
    private final DB db = mongo();
    private final DBCollection collection;

    public RankerService() throws Exception {
        this.collection = db.getCollection("tweets");
    }

    public List<TweetUnit> rank1(String username){
        List<TweetUnit> top10 = new ArrayList<>();
        Collector col = new Collector(username);
        Ranker1 ranker = new Ranker1(username);
        top10 = ranker.findTop10(col.getTweets());
        return top10;
    }

    public List<TweetUnit> rank2(String username) throws IOException {
        Collector col = new Collector(username);
        Ranker2 ranker2 = new Ranker2(username);
        Ranker1 ranker1 = new Ranker1(username);
        return ranker2.findTop10(col.getTweets());
    }

    /**
     * Handle Database
     * @return
     * @throws Exception
     */
    private static DB mongo() throws Exception {
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        if (host == null) {
            MongoClient mongoClient = new MongoClient("localhost");
            return mongoClient.getDB("Server");
        }
        int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String dbname = System.getenv("OPENSHIFT_APP_NAME");
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        System.out.println(username);
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);
//        if (db.authenticate(username, password.toCharArray())) {
//            return db;
//        } else {
//            throw new RuntimeException("Not able to authenticate with MongoDB");
//        }
        return db;
    }
}
