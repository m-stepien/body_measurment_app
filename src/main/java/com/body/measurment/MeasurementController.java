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
        try {
            this.measurementService.addNewMeasurement(basicCircumference);
        }
        catch(Exception e) {
            System.out.println("Something is fucked");
        }
    }

    @GetMapping("/bodyMonitoring/getAllCircumference")
    public CircumferenceData getAllCircumference(){
        return null;
    }

    @PatchMapping("/bodyMonitoring/update/circumference/basic/{id}")
    public void updateBasicCircumference(@RequestBody BasicCircumference basicCircumference){
        //czy powinno być oddzielne dla basic czy additional
    }

    @DeleteMapping("/bodyMonitoring/circumference/deleta/{id}")
    public void deleteCircumference(@RequestParam long id){

    }

}
