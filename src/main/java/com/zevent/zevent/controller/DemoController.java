package com.zevent.zevent.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class DemoController {

    @GetMapping("/")
    public String demo() {
        return "Demo";
    }

}
