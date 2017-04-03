package com.todoapp;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class TodoResource {

    private static final String API_CONTEXT = "/api/v1";

    private final TodoService todoService;

    public TodoResource(TodoService todoService) {

        this.todoService = todoService;
        setupEndpoints();
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/todos", "application/json", (request, response) -> {
            todoService.createNewTodo(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/todos/:id", "application/json", (request, response)

                -> todoService.find(request.params(":id")), new JsonTransformer());

        get(API_CONTEXT + "/todos", "application/json", (request, response)

                -> todoService.findAll(), new JsonTransformer());

        put(API_CONTEXT + "/todos/:id", "application/json", (request, response)

                -> todoService.update(request.params(":id"), request.body()), new JsonTransformer());
    }


}
