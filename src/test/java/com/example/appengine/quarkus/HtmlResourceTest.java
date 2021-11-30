package com.example.appengine.quarkus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlResourceTest {

    @Test
    void templateLoads() {
        assertNotNull(HtmlResource.TEMPLATE);
    }

}