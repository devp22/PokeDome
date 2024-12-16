package edu.gatech.pokedome.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple controller that returns a hello world message. For testing purposes.
 */
@CrossOrigin(origins = "http://localhost:3001")
@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }
}
