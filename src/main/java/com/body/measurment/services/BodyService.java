package com.body.measurment.services;

import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.dto.responses.BodySaveResponse;
import com.body.measurment.repositories.BasicBodyDataRepository;
import com.body.measurment.repositories.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodyService {
    private final WeightRepository weightRepository;
    private final BasicBodyDataRepository basicBodyDataRepository;

    @Autowired
    public BodyService(WeightRepository weightRepository, BasicBodyDataRepository basicBodyDataRepository) {
        this.weightRepository = weightRepository;
        this.basicBodyDataRepository = basicBodyDataRepository;
    }

    public BodySaveResponse saveBasicBodyData(BasicBodyData){

    }
}
