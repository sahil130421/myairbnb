package com.airbnb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class ContryController {
    @PostMapping("/addcon")
    public String addCountry(){
        return "done";
    }
}
