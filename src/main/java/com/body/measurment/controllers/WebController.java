package com.body.measurment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String home(){
        return "hello there";
    }
    @GetMapping("/addNewMeasurment")
    public String getAddNewMeasurment(){
        return "";
    }

    @GetMapping("/measurmentHistoryBrief")
    public String getBriefHistoryOfMeasurment(){
        return "";
    }

    @GetMapping("/getMeasurment")
    public String getMeasurment(){
        return "";
    }
}
