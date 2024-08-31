package com.body.measurment;

import com.body.measurment.dto.CircumferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public CircumferenceData getAllCircumference(){
        this.measurementService.
    }

    @PatchMapping("/bodyMonitoring/update/circumference/{id}")
    public void updateCircumference(@RequestBody CircumferenceData circumferenceData){
        this.measurementService.updateCircumgerence(circumferenceData);
    }

    @DeleteMapping("/bodyMonitoring/circumference/deleta/{id}")
    public void deleteCircumference(@RequestParam long id){
        this.measurementService.deleteCircumferenceById(id);
    }

}
