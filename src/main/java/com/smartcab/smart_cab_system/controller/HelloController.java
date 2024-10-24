package com.smartcab.smart_cab_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String sayHi() {
        System.out.println("Hello");
        return "Hi";
    }

    @GetMapping("/secure")
    public String sayHiSecure() {
        System.out.println("Secure Hello");
        return "Secure Hi";
    }
}
