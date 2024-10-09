package com.body.measurement.controllers;

import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.services.CircumferenceMeasurementService;
import com.body.measurement.dto.CircumferenceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MeasurementController {
    private final static Logger log = LoggerFactory.getLogger(MeasurementController.class);
    private final CircumferenceMeasurementService circumferenceMeasurementService;

    @Autowired
    public MeasurementController(CircumferenceMeasurementService circumferenceMeasurementService){
        this.circumferenceMeasurementService = circumferenceMeasurementService;
    }

    @PostMapping("/bodyMonitoring/addNewCircumference")
    public void addNewMeasurement(@RequestBody CircumferenceData circumferenceData){
        log.info("Get request POST to add new circumference data {}", circumferenceData);
        this.circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
    }

    @PatchMapping("/bodyMonitoring/update/circumference/{id}")
    public void updateCircumference(@PathVariable("id") long id, @RequestBody CircumferenceData circumferenceData){
        log.info("Get request PATCH to update circumferece data with id {} {}", id, circumferenceData);
        this.circumferenceMeasurementService.updateCircumference(circumferenceData);
    }

    @GetMapping("/bodyMonitoring/getCircumference/{id}")
    public CircumferenceData getCircumferenceData(@PathVariable("id") long id){
        log.info("Get request GET for circumference data with id {} ", id);
        return this.circumferenceMeasurementService.getCircumferenceDataById(id);
    }

    @GetMapping("bodyMonitoring/getBasicCircumferece")
    public BasicCircumference getBasicCircumferenceByDate(@RequestParam("date") LocalDate date){
        log.info("Get request GET for circumference data with date {} ", date);
        return this.circumferenceMeasurementService.getBasicCircumferenceByDate(date);
    }

    @GetMapping("/bodyMonitoring/getCircumference/betweenDate")
    public List<CircumferenceData> getCircumferenceDataBetweenDate(@RequestParam("start") LocalDate start,@RequestParam("end") LocalDate end){
        log.info("Get request GET for circumference data between dates {} {}", start, end);
        return this.circumferenceMeasurementService.getCircumferenceDataInDateRange(start, end);
    }

    @GetMapping("/bodyMonitoring/getCircumference/sinceDate")
    public List<CircumferenceData> getCircumferenceDataSinceDate(@RequestParam LocalDate start){
        log.info("Get request GET for circumference data before date {}", start);
        return this.circumferenceMeasurementService.getCircumferenceDataFromDate(start);
    }

    @GetMapping("/bodyMonitoring/getCircumference/all")
    public List<CircumferenceData> getCircumferenceDataAll(){
        log.info("Get request GET for circumference data all");
        return this.circumferenceMeasurementService.getCircumferenceDataAll();
    }

    @DeleteMapping("/bodyMonitoring/circumference/deleta/{id}")
    public void deleteCircumference(@PathVariable("id") long id){
        log.info("Get request DELETE for circumference data with id {}", id);
        this.circumferenceMeasurementService.deleteCircumferenceById(id);
    }

    @DeleteMapping("/bodyMonitoring/circumference/delete/all")
    public void deleteAllCircumference(){
        log.info("Get request DELETE for circumference data all");
        this.circumferenceMeasurementService.deleteAllCircumference();
    }
}
