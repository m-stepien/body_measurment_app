package com.body.measurment.controllers;

import com.body.measurment.services.MeasurementService;
import com.body.measurment.dto.CircumferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService){
        this.measurementService = measurementService;
    }

    @PostMapping("/bodyMonitoring/addNewCircumference")
    public void addNewMeasurement(@RequestBody CircumferenceData circumferenceData){
            this.measurementService.saveMeasurement(circumferenceData);
    }

    @GetMapping("/bodyMonitoring/getCircumference/{id}")
    public CircumferenceData getCircumferenceData(@PathVariable("id") long id){
        return this.measurementService.getCircumferenceDataById(id);
    }
    @GetMapping("/bodyMonitoring/getCircumference/betweenDate")
    public List<CircumferenceData> getCircumferenceDataBetweenDate(@RequestParam LocalDate start,@RequestParam LocalDate end){
        return this.measurementService.getCircumferenceDataInDateRange(start, end);
    }

    @GetMapping("/bodyMonitoring/getCircumference/sinceDate")
    public List<CircumferenceData> getCircumferenceDataSinceDate(@RequestParam LocalDate start){
        return this.measurementService.getCircumferenceDataFromDate(start);
    }

    @GetMapping("/bodyMonitoring/getCircumference/all")
    public List<CircumferenceData> getCircumferenceDataAll(){
        return this.measurementService.getCircumferenceDataAll();
    }

    //TODO repair issue when not every field is in class
    //TODO change CircumferenceData structure in database
    @PatchMapping("/bodyMonitoring/update/circumference/{id}")
    public void updateCircumference(@PathVariable("id") long id, @RequestBody CircumferenceData circumferenceData){
        this.measurementService.updateCircumference(circumferenceData);
    }

    @DeleteMapping("/bodyMonitoring/circumference/deleta/{id}")
    public void deleteCircumference(@PathVariable("id") long id){
        this.measurementService.deleteCircumferenceById(id);
    }

    @DeleteMapping("/bodyMonitoring/circumference/delete/all")
    public void deleteAllCircumference(){
        this.measurementService.deleteAllCircumference();
    }
    @GetMapping("/bodyMonitoring/circumference")
    public String helloThere(){
        return "hello there";
    }

}
