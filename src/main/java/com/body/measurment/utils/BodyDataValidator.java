package com.body.measurment.utils;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.dto.BasicCircumference;

public class BodyDataValidator {
    public boolean isBodyDataValid(BasicBodyData basicBodyData) throws InvalidDataException, MissingRequiredDataException {
        checkIsAllRequiredDataSet(basicBodyData);
        checkAgeValid(basicBodyData.getAge());
        checkHeightInCmValid(basicBodyData.getHeightInCm());
        checkGenderValid(basicBodyData.getGander());
        return true;
    }


    private void checkIsAllRequiredDataSet(BasicBodyData basicBodyData) throws MissingRequiredDataException {
        if(basicBodyData.getAge() == null || basicBodyData.getGander() == null || basicBodyData.getHeightInCm() == null)
        {
            throw new MissingRequiredDataException(BasicBodyData.class.getName());
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