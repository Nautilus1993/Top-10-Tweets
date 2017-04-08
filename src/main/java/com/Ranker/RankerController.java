package com.Ranker;

import com.Server.JsonTransformer;
import static spark.Spark.*;

public class RankerController {
    private static final String API_CONTEXT = "/tweet";
    private RankerService rankerService;
    public RankerController(RankerService rankerService){
        this.rankerService = rankerService;
        setupEndpoints();
    }

    private void setupEndpoints(){

        get(API_CONTEXT + "/:uname", "application/json", (request, response) -> {
            String uname = request.params(":uname");
            try {
                return rankerService.rank2(uname);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }, new JsonTransformer());

        // return a user's top-10 representative tweets
//        Spark.get(API_CONTEXT + "/display", "application/json", (request, response) -> {
//            try {
//                return rankerService.rank2(request.params(":uname"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }, new JsonTransformer());

    }
}
