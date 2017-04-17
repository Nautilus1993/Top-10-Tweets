package com.Server;

import com.Ranker.RankerController;
import com.Ranker.RankerService;

import static spark.SparkBase.*;

public class Bootstrap {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_IP") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_IP")) : 8080;
    public static final int TOP_K = 10;
    public static void main(String[] args) throws Exception {
        setIpAddress(IP_ADDRESS);
        setPort(getHerokuAssignedPort());
        staticFileLocation("/public");
        new RankerController(new RankerService());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 5000; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
