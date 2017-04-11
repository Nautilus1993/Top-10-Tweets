package com.Server;

import com.Data.Collector;
import com.Ranker.Ranker1;
import com.Ranker.Ranker2;
import com.Ranker.RankerController;
import com.Ranker.RankerService;
import com.mongodb.*;

import static spark.SparkBase.*;

public class Bootstrap {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_IP") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_IP")) : 8080;
    public static final int TOP_K = 10;
    public static void main(String[] args) throws Exception {
        setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        new RankerController(new RankerService());
    }
}
