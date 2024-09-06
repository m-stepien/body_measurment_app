package com.body.measurment.utils;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;

public interface Validator {

    public boolean validateCircumferenceData(CircumferenceData circumferenceData) throws MissingRequiredDataException, InvalidDataException;

    public boolean checkRequiredField(BasicCircumference basicCircumference) throws MissingRequiredDataException;

    public boolean checkSignOnFields(BasicCircumference basicCircumference) throws InvalidDataException;

    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference) throws InvalidDataException;

}
