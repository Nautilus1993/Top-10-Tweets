package com.todoapp;

import static spark.Spark.get;
import static spark.Spark.post;

public class RankerController {
    private static final String API_CONTEXT = "representative/";
    private RankerService rankerService;
    public RankerController(RankerService rankerService){
        this.rankerService = rankerService;
        setupEndpoints();
    }

    private void setupEndpoints(){
        // get a user's top-10 representative tweets
        get(API_CONTEXT + ":uname", "application/json", (request, response) ->
            rankerService.rank(request.params(":uname")), new JsonTransformer());
    }
}
