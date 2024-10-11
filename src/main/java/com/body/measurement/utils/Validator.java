package com.body.measurement.utils;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.AdditionalCircumference;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.CircumferenceData;

public interface Validator {

    public boolean validateCircumferenceData(CircumferenceData circumferenceData) throws MissingRequiredDataException, InvalidDataException;

    public boolean validateUpdateCircumferenceData(CircumferenceData circumferenceData) throws InvalidDataException;

    public boolean checkRequiredField(BasicCircumference basicCircumference) throws MissingRequiredDataException;

    public boolean checkSignOnFields(BasicCircumference basicCircumference) throws InvalidDataException;

    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference) throws InvalidDataException;
}
