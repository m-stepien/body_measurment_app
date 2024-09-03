package com.body.measurment.utils;

import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;

public interface Validator {

    public boolean checkRequiredField(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference);

}
