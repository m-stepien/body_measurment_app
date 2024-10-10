package com.body.measurement.controllers;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurement.dto.BodyDetails;
import com.body.measurement.dto.Weight;
import com.body.measurement.services.BodyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/body")
public class BodyController {
    private final static Logger log = LoggerFactory.getLogger(BodyController.class);
    private final BodyService bodyService;

    @Autowired
    public BodyController(BodyService bodyService){
        this.bodyService = bodyService;
    }

    @PostMapping("/details/save")
    public ResponseEntity<Void> saveBodyDetails(@RequestParam("gender") String gender, @ModelAttribute BodyDetails bodyDetails) throws MissingRequiredDataException, InvalidDataException {
        log.info("Request to save body details received. Processing...");
        //temp drut
        bodyDetails.setGender(gender);
        bodyDetails.setId(1);
        this.bodyService.saveBodyDetails(bodyDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/details/get/{id}")
    public ResponseEntity<BodyDetails> fetchBodyDetails(@PathVariable("id") long id){
        log.info("Fetching body details for ID: {}", id);
        BodyDetails bodyDetails = this.bodyService.getBodyDetailsData(id);
        return bodyDetails != null ? ResponseEntity.ok(bodyDetails) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/details/delete/{id}")
    public ResponseEntity<Void> deleteBodyDetailsById(@PathVariable("id") long id){
        log.info("Deleting body details entry with ID: {}", id);
        this.bodyService.deleteBodyDetailsData(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/weight/save")
    public ResponseEntity<Void> saveNewWeight(@RequestBody Weight weight) throws MissingRequiredDataException, InvalidDataException {
        log.info("Request to save weight received. Processing...");
        this.bodyService.saveWeight(weight);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/weight/update")
    public ResponseEntity<Weight> updateWeight(@ModelAttribute Weight weight) throws MissingRequiredDataException, InvalidDataException, NoSuchObjectInDatabaseException {
        log.info("Updating weight entry with ID: {}", weight.getId());
        Weight updatedWeight = this.bodyService.updateWeight(weight);
        return ResponseEntity.ok(updatedWeight);
    }

    @GetMapping("/weight/get/{id}")
    public ResponseEntity<Weight> fetchWeightById(@PathVariable("id") long id){
        log.info("Fetching weight data for ID: {}", id);
        Weight weight = this.bodyService.getWeightById(id);
        return weight != null ? ResponseEntity.ok(weight) : ResponseEntity.notFound().build();
    }

    @GetMapping("/weight/get/last")
    public ResponseEntity<Weight> fetchLastWeight(){
        log.info("Fetching last weight data");
        Weight weight = this.bodyService.getWeightLast();
        return weight != null ? ResponseEntity.ok(weight) : ResponseEntity.notFound().build();
    }

    @GetMapping("/weight/get/betweendates")
    public ResponseEntity<List<Weight>> findWeightBetweenDates(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end){
        log.info("Fetching weight data between dates: {} {}", start, end);
        List<Weight> weightList = this.bodyService.getWeightBetweenDates(start, end);
        return weightList != null ? ResponseEntity.ok(weightList) : ResponseEntity.notFound().build();
    }

    @GetMapping("/weight/get/first")
    public ResponseEntity<Weight> findFirstWeight(){
        log.info("Fetching first weight data");
        Weight weight = this.bodyService.getWeightFirst();
        return weight != null ? ResponseEntity.ok(weight) : ResponseEntity.notFound().build();
    }

    @GetMapping("/weight/get/before")
    public ResponseEntity<Weight> findOneBeforeDate(@RequestParam("date") LocalDate date){
        log.info("Fetching weight data before date: {}", date);
        Weight weight = this.bodyService.getWeightOneBefore(date);
        return weight != null ? ResponseEntity.ok(weight) : ResponseEntity.notFound().build();
    }

    @GetMapping("/weight/get/after")
    public ResponseEntity<Weight> findOneAfterDate(@RequestParam("date") LocalDate date){
        log.info("Fetching weight data after date: {}", date);
        Weight weight = this.bodyService.getWeightOneAfter(date);
        return weight != null ? ResponseEntity.ok(weight) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/weight/delete/{id}")
    public ResponseEntity<Void> deleteWeightById(@PathVariable("id")long id){
        log.info("Deleting weight entry with ID: {}", id);
        this.bodyService.deleteWeight(id);
        return ResponseEntity.noContent().build();
    }
}
