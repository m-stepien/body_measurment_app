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

    @GetMapping("/addWeight")
    public String addWeight(){
        return "add_weight";
    }

    @GetMapping("/addBodyDetails")
    public String addBasicBodyData(){
        return "add_body_details";
    }

    @GetMapping("/measurement")
    public String getMeasurement(){
        return "measurement";
    }

    @GetMapping("/body/data")
    public String getBasicBodyData(){
        return "basic_body_data";
    }
}
