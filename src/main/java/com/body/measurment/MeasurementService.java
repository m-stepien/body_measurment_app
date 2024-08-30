package com.body.measurment;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService{
    private Validator validator;
    @Autowired
    public MeasurementService(Validator validator){
        this.validator = validator;
    }

    public void addNewMeasurement(Circumference circumference) throws Exception{
        if(this.validator.checkRequiredField(circumference)){
            if(this.validator.checkSignOnFields(circumference)){

            }
            else{
                throw new InvalidDataException();
            }
        }
        else{
            throw new MissingRequiredDataException(circumference.getClass().getName());
        }

    }
}
