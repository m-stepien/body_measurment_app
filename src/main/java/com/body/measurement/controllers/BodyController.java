package com.body.measurement.controllers;

import com.body.measurement.dto.BodyDetails;
import com.body.measurement.dto.Weight;
import com.body.measurement.dto.responses.BodySaveResponse;
import com.body.measurement.services.BodyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BodyController {
    private final static Logger log = LoggerFactory.getLogger(BodyController.class);
    private final BodyService bodyService;

    @Autowired
    public BodyController(BodyService bodyService){
        this.bodyService = bodyService;
    }

    @PostMapping("/body/basic/save")
    public void saveBodyDetails(@RequestParam("gender") String gender, @ModelAttribute BodyDetails bodyDetails){
        //temp drut
        bodyDetails.setGender(gender);
        bodyDetails.setId(1);
        log.info("Get request POST for saving body detail {}", bodyDetails);
        this.bodyService.saveBodyDetails(bodyDetails);
    }

    @GetMapping("/body/basic/get/{id}")
    public BodyDetails getBodyDetails(@PathVariable("id") long id){
        log.info("Get request GET to get body details with id {}", id);
        return this.bodyService.getBodyDetailsData(id);
    }

    @DeleteMapping("/body/details/delete/{id}")
    public void deleteBodyDetailsById(@PathVariable("id") long id){
        log.info("Get request DELETE to delelete body details with id {}", id);
        this.bodyService.deleteBodyDetailsData(id);
    }

    @PostMapping("/weight/save")
    public BodySaveResponse saveNewWeight(@ModelAttribute Weight weight){
        log.info("Get request POST to save new weight {}", weight);
        return this.bodyService.saveWeight(weight);
    }

    @PatchMapping("/weight/update")
    public BodySaveResponse updateWeight(@ModelAttribute Weight weight){
        log.info("Get request PATCH to update weight {}", weight);
        return this.bodyService.updateWeight(weight);
    }

    @GetMapping("/weight/get/{id}")
    public Weight getWeightById(@PathVariable("id")long id){
        log.info("Get request GET to get weight with id {}", id);
        return this.bodyService.getWeightById(id);
    }

    @GetMapping("/weight/get/last")
    public Weight getLastWeight(){
        log.info("Get request GET to get last weight");
        return this.bodyService.getWeightLast();
    }

    @GetMapping("/weight/get/betweendates")
    public List<Weight> findWeightBetweenDates(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end){
        log.info("Get request GET to get weight between {} {}", start, end);
        return this.bodyService.getWeightBetweenDates(start, end);
    }

    @GetMapping("/weight/get/first")
    public Weight findFirstWeight(){
        log.info("Get request GET to get first weight");
        return this.bodyService.getWeightFirst();
    }

    @GetMapping("/weight/get/before")
    public Weight findOneBeforeDate(@RequestParam("date") LocalDate date){
        log.info("Get request GET to get weight before {}", date);
        return this.bodyService.getWeightOneBefore(date);
    }

    @GetMapping("/weight/get/after")
    public Weight findOneAfterDate(@RequestParam("date") LocalDate date){
        log.info("Get request GET to get weight after {}", date);
        return this.bodyService.getWeightOneAfter(date);
    }

    @DeleteMapping("/weight/delete/{id}")
    public void deleteWeightById(@PathVariable("id")long id){
        log.info("Get request DELETE for weight with id{}", id);
        this.bodyService.deleteWeight(id);
    }
}
