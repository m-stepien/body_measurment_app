package com.body.measurement.services;

import com.body.measurement.dto.BodyData;
import com.body.measurement.dto.BodyDetails;
import com.body.measurement.dto.Weight;
import org.springframework.beans.factory.annotation.Autowired;

public class BodyDataProviderImpl implements BodyDataProvider{
    BodyService bodyService;

    @Autowired
    public BodyDataProviderImpl(BodyService bodyService) {
        this.bodyService = bodyService;
    }

    @Override
    public BodyData getBodyDataByUserId(Long userId) {
        Weight weight = this.bodyService.getWeightLast();
        BodyDetails bodyDetails = this.bodyService.getBodyDetailsData(userId);
        return createBodyData(weight, bodyDetails);
    }

    private BodyData createBodyData(Weight weight, BodyDetails bodyDetails){
        return new BodyData(bodyDetails.getHeightInCm(),bodyDetails.getAge(),
                bodyDetails.getGender(), weight.getWeightInKg());
    }
}
