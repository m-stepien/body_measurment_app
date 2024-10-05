package com.body.measurement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/addWeight")
    public String addWeight(){
        return "add_weight";
    }

    @GetMapping("/addBodyDetails")
    public String addBasicBodyData(){
        return "add_body_details";
    }

    @GetMapping("/body-data")
    public String getBodyData(@RequestParam("date")LocalDate date, Model model){
        model.addAttribute("date", date);
        return "body_data";
    }

    @GetMapping("/body/data")
    public String getBasicBodyData(){
        return "basic_body_data";
    }
}
