package com.body.measurement.controllers;

import com.body.measurement.dto.BodyDetails;
import com.body.measurement.dto.Weight;
import com.body.measurement.dto.responses.BodySaveResponse;
import com.body.measurement.services.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BodyController {
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
        System.out.println(bodyDetails);
        this.bodyService.saveBodyDetails(bodyDetails);
    }

    @GetMapping("/body/basic/get/{id}")
    public BodyDetails getBodyDetails(@PathVariable("id") long id){
        return this.bodyService.getBodyDetailsData(id);
    }

    @DeleteMapping("/body/details/delete/{id}")
    public void deleteBodyDetailsById(@PathVariable("id") long id){
        this.bodyService.deleteBodyDetailsData(id);
    }

    @PostMapping("/weight/save")
    public BodySaveResponse saveNewWeight(@ModelAttribute Weight weight){
        return this.bodyService.saveWeight(weight);
    }

    @PatchMapping("/weight/update")
    public BodySaveResponse updateWeight(@ModelAttribute Weight weight){
        return this.bodyService.updateWeight(weight);
    }

    @GetMapping("/weight/get/{id}")
    public Weight getWeightById(@PathVariable("id")long id){
        return this.bodyService.getWeightById(id);
    }

    @GetMapping("/weight/get/last")
    public Weight getLastWeight(){
        return this.bodyService.getWeightLast();
    }

    @GetMapping("/weight/get/betweendates")
    public List<Weight> findWeightBetweenDates(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end){
        return this.bodyService.getWeightBetweenDates(start, end);
    }

    @GetMapping("/weight/get/first")
    public Weight findFirstWeight(){
        return this.bodyService.getWeightFirst();
    }

    @GetMapping("/weight/get/before")
    public Weight findOneBeforeDate(@RequestParam("date") LocalDate date){
        return this.bodyService.getWeightOneBefore(date);
    }

    @GetMapping("/weight/get/after")
    public Weight findOneAfterDate(@RequestParam("date") LocalDate date){
        return this.bodyService.getWeightOneAfter(date);
    }

    @DeleteMapping("/weight/delete/{id}")
    public void deleteWeightById(@PathVariable("id")long id){
        this.bodyService.deleteWeight(id);
    }
}
