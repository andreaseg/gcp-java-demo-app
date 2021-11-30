package com.example.appengine.quarkus;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HtmlResourceTest {

    @Test
    void templateLoads() {
        assertNotNull(HtmlResource.TEMPLATE);
        System.out.println(HtmlResource.TEMPLATE);
    }

    @Test
    void checkOutput() {
        String output = HtmlResource.render(Map.of("title", "<<title>>", "body", "<<body>>", "wordcount", "3"));
        System.out.println(output);
        assertTrue(output.contains("<<title>>"));
        assertTrue(output.contains("<<body>>"));
    }

}