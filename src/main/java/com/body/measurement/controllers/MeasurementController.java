package com.body.measurement.controllers;

import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.services.CircumferenceMeasurementService;
import com.body.measurement.dto.CircumferenceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/circumference")
public class MeasurementController {
    private final static Logger log = LoggerFactory.getLogger(MeasurementController.class);
    private final CircumferenceMeasurementService circumferenceMeasurementService;

    @Autowired
    public MeasurementController(CircumferenceMeasurementService circumferenceMeasurementService){
        this.circumferenceMeasurementService = circumferenceMeasurementService;
    }

    @PostMapping("/addNewCircumference")
    public ResponseEntity<Void> addNewMeasurement(@RequestBody CircumferenceData circumferenceData){
        log.info("Request to save circumference data received. Processing...");
        this.circumferenceMeasurementService.saveCircumferenceMeasurement(circumferenceData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CircumferenceData> updateCircumference(@PathVariable("id") long id, @RequestBody CircumferenceData circumferenceData){
        log.info("Updating weight circumference data with ID: {}", circumferenceData.getId());
        CircumferenceData updateCircumferenceData = this.circumferenceMeasurementService.updateCircumference(circumferenceData);
        return ResponseEntity.ok(updateCircumferenceData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CircumferenceData> getCircumferenceData(@PathVariable("id") long id){
        log.info("Fetching circumference data for ID: {}", id);
        CircumferenceData circumferenceData = this.circumferenceMeasurementService.getCircumferenceDataById(id);
        return circumferenceData != null ? ResponseEntity.ok(circumferenceData) : ResponseEntity.notFound().build();
    }

    @GetMapping("/basic")
    public ResponseEntity<BasicCircumference> getBasicCircumferenceByDate(@RequestParam("date") LocalDate date){
        log.info("Fetching circumference data for date: {}", date);
        BasicCircumference basicCircumference = this.circumferenceMeasurementService.getBasicCircumferenceByDate(date);
        return basicCircumference != null ? ResponseEntity.ok(basicCircumference) : ResponseEntity.notFound().build();
    }

    @GetMapping("/betweenDates")
    public ResponseEntity<List<CircumferenceData>> getCircumferenceDataBetweenDate(@RequestParam("start") LocalDate start,@RequestParam("end") LocalDate end){
        log.info("Fetching circumference data between dates: {} {}", start, end);
        List<CircumferenceData> circumferenceDataList = this.circumferenceMeasurementService.getCircumferenceDataInDateRange(start, end);
        return circumferenceDataList != null ? ResponseEntity.ok(circumferenceDataList) : ResponseEntity.notFound().build();
    }

    @GetMapping("/sinceDate")
    public ResponseEntity<List<CircumferenceData>> getCircumferenceDataSinceDate(@RequestParam LocalDate start){
        log.info("Fetching circumference data since dates: {}", start);
        List<CircumferenceData> circumferenceDataList = this.circumferenceMeasurementService.getCircumferenceDataFromDate(start);
        return circumferenceDataList != null ? ResponseEntity.ok(circumferenceDataList) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CircumferenceData>> getCircumferenceDataAll(){
        log.info("Fetching all circumference data");
        List<CircumferenceData> circumferenceDataList = this.circumferenceMeasurementService.getCircumferenceDataAll();
        return circumferenceDataList != null ? ResponseEntity.ok(circumferenceDataList) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCircumference(@PathVariable("id") long id){
        log.info("Deleting circumference data with ID: {}", id);
        this.circumferenceMeasurementService.deleteCircumferenceById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllCircumference(){
        log.info("Deleting all circumference data");
        this.circumferenceMeasurementService.deleteAllCircumference();
        return ResponseEntity.noContent().build();
    }
}
