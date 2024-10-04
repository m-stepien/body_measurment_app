package com.body.measurement.controllers;

import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.services.CircumferenceMeasurementService;
import com.body.measurement.dto.CircumferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MeasurementController {
    private final CircumferenceMeasurementService circumferenceMeasurementService;

    @Autowired
    public MeasurementController(CircumferenceMeasurementService circumferenceMeasurementService){
        this.circumferenceMeasurementService = circumferenceMeasurementService;
    }

    @PostMapping("/bodyMonitoring/addNewCircumference")
    public void addNewMeasurement(@ModelAttribute CircumferenceData circumferenceData){
            this.circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }

    @PatchMapping("/bodyMonitoring/update/circumference/{id}")
    public void updateCircumference(@PathVariable("id") long id, @RequestBody CircumferenceData circumferenceData){
        this.circumferenceMeasurementService.updateCircumference(circumferenceData);
    }

    @GetMapping("/bodyMonitoring/getCircumference/{id}")
    public CircumferenceData getCircumferenceData(@PathVariable("id") long id){
        return this.circumferenceMeasurementService.getCircumferenceDataById(id);
    }

    @GetMapping("bodyMonitoring/getBasicCircumferece")
    public BasicCircumference getBasicCircumferenceByDate(@RequestParam("date") LocalDate date){
        return this.circumferenceMeasurementService.getBasicCircumferenceByDate(date);
    }

    @GetMapping("/bodyMonitoring/getCircumference/betweenDate")
    public List<CircumferenceData> getCircumferenceDataBetweenDate(@RequestParam LocalDate start,@RequestParam LocalDate end){
        return this.circumferenceMeasurementService.getCircumferenceDataInDateRange(start, end);
    }

    @GetMapping("/bodyMonitoring/getCircumference/sinceDate")
    public List<CircumferenceData> getCircumferenceDataSinceDate(@RequestParam LocalDate start){
        return this.circumferenceMeasurementService.getCircumferenceDataFromDate(start);
    }

    @GetMapping("/bodyMonitoring/getCircumference/all")
    public List<CircumferenceData> getCircumferenceDataAll(){
        return this.circumferenceMeasurementService.getCircumferenceDataAll();
    }

    @DeleteMapping("/bodyMonitoring/circumference/deleta/{id}")
    public void deleteCircumference(@PathVariable("id") long id){
        this.circumferenceMeasurementService.deleteCircumferenceById(id);
    }

    @DeleteMapping("/bodyMonitoring/circumference/delete/all")
    public void deleteAllCircumference(){
        this.circumferenceMeasurementService.deleteAllCircumference();
    }
}
