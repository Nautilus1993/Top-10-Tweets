package com.Ranker;

import com.Server.JsonTransformer;
import spark.Spark;

import static spark.route.HttpMethod.post;

public class RankerController {
    private static final String API_CONTEXT = "/tweet";
    private RankerService rankerService;
    public RankerController(RankerService rankerService){
        this.rankerService = rankerService;
        setupEndpoints();
    }

    private void setupEndpoints(){
        Spark.get(API_CONTEXT + ":uname", "application/json", (request, response) -> {
            System.out.println("received username" + request.params(":uname"));
            response.body("I am server");
            return response;
        }, new JsonTransformer());

        // return a user's top-10 representative tweets
        Spark.get(API_CONTEXT + "/display", "application/json", (request, response) ->
            rankerService.rank(request.params(":uname")), new JsonTransformer());

    }
}
