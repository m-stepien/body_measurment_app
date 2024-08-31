package com.body.measurment.dto;

import com.body.measurment.AdditionalCircumference;
import com.body.measurment.BasicCircumference;

public class CircumferenceData {
    private BasicCircumference basicCircumference;
    private AdditionalCircumference additionalCircumference;

    public CircumferenceData(BasicCircumference basicCircumference, AdditionalCircumference additionalCircumference) {
        this.basicCircumference = basicCircumference;
        this.additionalCircumference = additionalCircumference;
    }

    public BasicCircumference getBasicCircumference() {
        return basicCircumference;
    }

    public void setBasicCircumference(BasicCircumference basicCircumference) {
        this.basicCircumference = basicCircumference;
    }

    public AdditionalCircumference getAdditionalCircumference() {
        return additionalCircumference;
    }

    public void setAdditionalCircumference(AdditionalCircumference additionalCircumference) {
        this.additionalCircumference = additionalCircumference;
    }
}
