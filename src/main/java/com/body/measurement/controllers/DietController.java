package com.body.measurement.controllers;

import com.body.measurement.services.DietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diet")
public class DietController {
    private final static Logger log = LoggerFactory.getLogger(DietController.class);
    private final DietService dietService;

    @Autowired
    public DietController(DietService dietService){
        this.dietService = dietService;
    }


    @GetMapping("/demand")
    public getUserDemand(){
        log.info("Request to get eating demand for user. Processing...");
        dietService
    }



}
