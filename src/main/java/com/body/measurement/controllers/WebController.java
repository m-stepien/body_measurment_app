package com.body.measurement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/addNewMeasurement")
    public String getAddNewMeasurement(){
        return "new_measurement";
    }

    @GetMapping("/getMeasurement")
    public String getMeasurement(){
        return "measurement";
    }

    @GetMapping("/addWeight")
    public String addWeight(){
        return "add_weight";
    }

    @GetMapping("/addBasicBodyData")
    public String addBasicBodyData(){
        return "add_basic_body_data";
    }
}
