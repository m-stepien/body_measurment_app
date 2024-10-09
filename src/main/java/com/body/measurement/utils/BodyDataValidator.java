package com.body.measurement.utils;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.BodyDetails;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.Weight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BodyDataValidator {
    private final static Logger log = LoggerFactory.getLogger(BodyDataValidator.class);
    public boolean isBodyDetailsValid(BodyDetails bodyDetails) throws InvalidDataException, MissingRequiredDataException {
        log.info("Start body details validation {}", bodyDetails);
        checkIsAllRequiredDataSet(bodyDetails);
        checkAgeValid(bodyDetails.getAge());
        checkHeightInCmValid(bodyDetails.getHeightInCm());
        checkGenderValid(bodyDetails.getGender());
        log.info("Body details validation completed");
        return true;
    }

    public boolean isWeightValid(Weight weight) throws InvalidDataException, MissingRequiredDataException{
        log.info("Start weight validation {}", weight);
        checkIsAllRequiredDataSet(weight);
        checkDateValid(weight.getDate());
        checkWeightInKgValid(weight.getWeightInKg());
        log.info("Weight validation completed");
        return true;
    }

    private void checkIsAllRequiredDataSet(Weight weight) throws MissingRequiredDataException{
        if(weight.getWeightInKg() == null || weight.getDate() == null)
        {
            throw new MissingRequiredDataException(Weight.class.getName());
        }
    }


    private void checkIsAllRequiredDataSet(BodyDetails bodyDetails) throws MissingRequiredDataException {
        if(bodyDetails.getAge() == null || bodyDetails.getGender() == null || bodyDetails.getHeightInCm() == null)
        {
            throw new MissingRequiredDataException(BodyDetails.class.getName());
        }
    }

    private void checkWeightInKgValid(Double weightInKg) throws InvalidDataException{
        if(weightInKg <= 0 || weightInKg > 800){
            throw new InvalidDataException(Weight.class.getName(), "weightInKg");
        }
    }

    private void checkDateValid(LocalDate localDate) throws InvalidDataException{
        if(localDate.isAfter(LocalDate.now()) || localDate.isBefore(LocalDate.of(1800, 12, 31))){
            throw new InvalidDataException(BasicCircumference.class.getName(), "date");
        }
    }

    private void checkHeightInCmValid(Double heightInCm) throws InvalidDataException {
        if(heightInCm <= 0 || heightInCm > 275){
            throw new InvalidDataException(BasicCircumference.class.getName(), "age");
        }
    }

    private void checkAgeValid(Integer age) throws InvalidDataException {
        if(age<=0 || age>150){
            throw new InvalidDataException(BasicCircumference.class.getName(), "heightInCm");
        }
    }

    private void checkGenderValid(String gender) throws InvalidDataException {
        if(!(gender.equals("m") || gender.equals("f") || gender.equals("o"))){
            throw new InvalidDataException(BasicCircumference.class.getName(), "gender");
        }
    }
}