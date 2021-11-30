package com.example.appengine.quarkus;

public class Request {
    private final String title;
    private final String body;

    public Request(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
