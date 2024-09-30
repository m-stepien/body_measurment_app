package com.body.measurement.controllers;

import com.body.measurement.dto.BasicBodyData;
import com.body.measurement.dto.Weight;
import com.body.measurement.dto.responses.BodySaveResponse;
import com.body.measurement.services.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BodyController {
    private final BodyService bodyService;

    @Autowired
    public BodyController(BodyService bodyService){
        this.bodyService = bodyService;
    }

    @PostMapping("/body/basic/save")
    public void saveBasicBodyData(@RequestParam("gender") String gender, @ModelAttribute BasicBodyData basicBodyData){
        //temp drut
        basicBodyData.setGender(gender);
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

    @PostMapping("/weight/save")
    public BodySaveResponse saveNewWeight(@ModelAttribute Weight weight){
        return this.bodyService.saveWeight(weight);
    }

    @PatchMapping("/weight/update")
    public BodySaveResponse updateWeight(@ModelAttribute Weight weight){
        return this.bodyService.updateWeight(weight);
    }

    @GetMapping("/weight/get/{id}")
    public Weight getWeightById(@PathVariable("id")long id){
        return this.bodyService.getWeightById(id);
    }

    @GetMapping("/weight/get/last")
    public Weight getLastWeight(){
        return this.bodyService.getWeightLast();
    }

//    @GetMapping("weight/get/betweendates")
//    public List<Weight> findWeightBetweenDates(@RequestParam LocalDate start, @RequestParam LocalDate end){
//        return this.bodyService.
//    }


    @DeleteMapping("/weight/delete/{id}")
    public void deleteWeightById(@PathVariable("id")long id){
        this.bodyService.deleteWeight(id);
    }
}
