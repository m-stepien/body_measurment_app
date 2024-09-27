package com.body.measurement.controllers;

import com.body.measurement.dto.BasicBodyData;
import com.body.measurement.dto.Weight;
import com.body.measurement.dto.responses.BodySaveResponse;
import com.body.measurement.services.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BodyController {
    private final BodyService bodyService;

    @Autowired
    public BodyController(BodyService bodyService){
        this.bodyService = bodyService;
    }

    @PostMapping("/body/basic/save")
    public void saveBasicBodyData(BasicBodyData basicBodyData){
        this.bodyService.saveBasicBodyData(basicBodyData);
    }

    @GetMapping("/body/basic/get/{id}")
    public BasicBodyData getBasicBodyDataById(@PathVariable("id") long id){
        return this.bodyService.getBasicBodyData(id);
    }

    @DeleteMapping("/body/basic/delete/{id}")
    public void deleteBasicBodyDataById(@PathVariable("id") long id){
        this.bodyService.deleteBasicBodyData(id);
    }

    @PostMapping("weight/save")
    public BodySaveResponse saveNewWeight(Weight weight){
        return this.bodyService.saveWeight(weight);
    }

    @PatchMapping("weight/update")
    public BodySaveResponse updateWeight(Weight weight){
        return this.bodyService.updateWeight(weight);
    }

    @GetMapping("weight/get/{id}")
    public Weight getWeightById(@PathVariable("id")long id){
        return this.bodyService.getWeightById(id);
    }

    @DeleteMapping("weight/delete/{id}")
    public void deleteWeightById(@PathVariable("id")long id){
        this.bodyService.deleteWeight(id);
    }
}
