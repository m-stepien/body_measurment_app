package com.body.measurment.utils;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.Weight;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BodyDataValidator {
    public boolean isBodyDataValid(BasicBodyData basicBodyData) throws InvalidDataException, MissingRequiredDataException {
        checkIsAllRequiredDataSet(basicBodyData);
        checkAgeValid(basicBodyData.getAge());
        checkHeightInCmValid(basicBodyData.getHeightInCm());
        checkGenderValid(basicBodyData.getGander());
        return true;
    }

    public boolean isWeightValid(Weight weight) throws InvalidDataException, MissingRequiredDataException{
        checkIsAllRequiredDataSet(weight);
        checkDateValid(weight.getDate());
        checkWeightInKgValid(weight.getWeightInKg());
        return true;
    }

    private void checkIsAllRequiredDataSet(Weight weight) throws MissingRequiredDataException{
        if(weight.getWeightInKg() == null || weight.getDate() == null)
        {
            throw new MissingRequiredDataException(Weight.class.getName());
        }
    }


    private void checkIsAllRequiredDataSet(BasicBodyData basicBodyData) throws MissingRequiredDataException {
        if(basicBodyData.getAge() == null || basicBodyData.getGander() == null || basicBodyData.getHeightInCm() == null)
        {
            throw new MissingRequiredDataException(BasicBodyData.class.getName());
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
        if(!(gender.equals("M") || gender.equals("F") || gender.equals("O"))){
            throw new InvalidDataException(BasicCircumference.class.getName(), "gender");
        }
    }
}