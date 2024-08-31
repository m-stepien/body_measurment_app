package com.body.measurment;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.CircumferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService{
    private final Validator validator;
    private final BasicCircumferenceRepository basicCircumferenceRepository;
    @Autowired
    public MeasurementService(Validator validator,
                              BasicCircumferenceRepository basicCircumferenceRepository){
        this.basicCircumferenceRepository = basicCircumferenceRepository;
        this.validator = validator;
    }

    public void addNewMeasurement(CircumferenceData circumferenceData) throws Exception{
        try {
            this.addNewBasicCircumference(circumferenceData.getBasicCircumference());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void addNewBasicCircumference(BasicCircumference basicCircumference) throws Exception{
        if(this.validator.checkRequiredField(basicCircumference)){
            if(this.validator.checkSignOnFields(basicCircumference)){
                basicCircumferenceRepository.save(basicCircumference);
            }
            else{
                throw new InvalidDataException();
            }
        }
        else{
            throw new MissingRequiredDataException(basicCircumference.getClass().getName());
        }
    }
}
