package com.body.measurment.utils;

import com.body.measurment.AdditionalCircumference;
import com.body.measurment.BasicCircumference;

public interface Validator {

    public boolean checkRequiredField(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(BasicCircumference basicCircumference);

    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference);

}
