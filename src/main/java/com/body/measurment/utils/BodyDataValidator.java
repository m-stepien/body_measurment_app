package com.body.measurment.utils;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.dto.BasicCircumference;

public class BodyDataValidator {
    public boolean isBodyDataValid(BasicBodyData basicBodyData) throws InvalidDataException, MissingRequiredDataException {
        if (!isAllRequiredDataSet(basicBodyData)) {
            throw new MissingRequiredDataException(BasicBodyData.class.getName());
        }
        if (!isAgeValid(basicBodyData.getAge())){
            throw new InvalidDataException(BasicCircumference.class.getName(), "age");
        }
        if(!isHeightInCmValid(basicBodyData.getHeightInCm())){
            throw new InvalidDataException(BasicCircumference.class.getName(), "heightInCm");
        }
        if(!isGenderValid(basicBodyData.getGander())){
            throw new InvalidDataException(BasicCircumference.class.getName(), "gender");
        }
        return true;
    }


    private boolean isAllRequiredDataSet(BasicBodyData basicBodyData) {
        return basicBodyData.getAge() != null && basicBodyData.getGander() != null && basicBodyData.getHeightInCm() != null;
    }

    private boolean isHeightInCmValid(Double heightInCm){
        return heightInCm > 0 && heightInCm <= 275;
    }

    private boolean isAgeValid(Integer age){
        return age>0 && age<=150;
    }

    private boolean isGenderValid(String gender){
        return gender.equals("M") || gender.equals("F") || gender.equals("O");
    }
}