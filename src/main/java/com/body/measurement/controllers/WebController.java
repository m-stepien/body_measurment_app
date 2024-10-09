package com.body.measurement.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WebController {
    private final static Logger log = LoggerFactory.getLogger(WebController.class);
    @GetMapping("/")
    public String home(){
        log.info("Get request GET index");
        return "index";
    }

    @GetMapping("/addWeight")
    public String addWeight(){
        log.info("Get request GET add_weight");
        return "add_weight";
    }

    @GetMapping("/addBodyDetails")
    public String addBasicBodyData(){
        log.info("Get request GET add_body_details");
        return "add_body_details";
    }

    @GetMapping("/body-data")
    public String getBodyData(@RequestParam("date")LocalDate date, Model model){
        model.addAttribute("date", date);
        log.info("Get request GET body_data");
        return "body_data";
    }

    @GetMapping("/body/data")
    public String getBasicBodyData(){
        log.info("Get request GET basic_body_data");
        return "basic_body_data";
    }
}
