package com.body.measurement.utils;

import com.body.measurement.dto.FootAndInch;

import static java.lang.Math.floor;

public class UnitConverter {
    private final Double KG_TO_LBS = 2.2;
    private final Double CM_TO_INCH = 0.393700787;
    private final Integer FOOT_TO_INCH = 12;

    public Double convertKgToLbs(Double weightInKg) {
        return weightInKg * KG_TO_LBS;
    }

    public Double convertLbsToKg(Double weightInLbs) {
        return weightInLbs / KG_TO_LBS;
    }

    public Double convertCmToInch(Double sizeInCm) {
        return sizeInCm * CM_TO_INCH;
    }

    public Double convertInchToCm(Double sizeInInch) {
        return sizeInInch / CM_TO_INCH;
    }

    public FootAndInch convertInchToFootAndInch(Double sizeInInch) {
        Integer foot = (int) floor(sizeInInch / FOOT_TO_INCH);
        Double inch = sizeInInch % FOOT_TO_INCH;
        return new FootAndInch(foot, inch);
    }
}
