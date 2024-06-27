package com.jinwei.S3_simple_RESTful;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting1 greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        final String str_message = "Get Return message";
        return new Greeting1(str_message, counter.incrementAndGet(), String.format(template, name));
    }
}