package com.body.measurment;

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
    public void addNewMeasurement(@RequestBody CircumferenceData basicCircumference){
            this.measurementService.addNewMeasurement(basicCircumference);
    }

    @GetMapping("/bodyMonitoring/getCircumference/{id}")
    public CircumferenceData getCircumferenceData(@PathVariable long id){
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

    @PatchMapping("/bodyMonitoring/update/circumference/{id}")
    public void updateCircumference(@RequestBody CircumferenceData circumferenceData){
        this.measurementService.updateCircumference(circumferenceData);
    }

    @DeleteMapping("/bodyMonitoring/circumference/deleta/{id}")
    public void deleteCircumference(@PathVariable long id){
        this.measurementService.deleteCircumferenceById(id);
    }

    @DeleteMapping("/bodyMonitoring/circumference/delete/all")
    public void deleteAllCircumference(){
        this.measurementService.deleteAllCircumference();
    }

}
