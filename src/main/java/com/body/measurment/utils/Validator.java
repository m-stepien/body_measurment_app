package com.body.measurment.utils;

import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;

public interface Validator {

    public boolean validateCircumferenceDatra(CircumferenceData circumferenceData);

    public boolean checkRequiredField(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference);

}
