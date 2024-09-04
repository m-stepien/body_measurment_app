package com.body.measurment.utils;

import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;

public interface Validator {

    public boolean validateCircumferenceData(CircumferenceData circumferenceData);

    public boolean checkRequiredField(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference);

}
