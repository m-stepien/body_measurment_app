package com.body.measurment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService){
        this.measurementService = measurementService;
    }

    @PostMapping("/bodyMonitoring/addNewCircumference")
    public void addNewMeasurement(@RequestBody BasicCircumference basicCircumference){
        try {
            this.measurementService.addNewMeasurement(basicCircumference);
        }
        catch(Exception e) {
            System.out.println("Something is fucked");
        }
    }

    @GetMapping("bodyMonitoring/getAllCircumference")
    public void getAllCircumference(){

    }

}
