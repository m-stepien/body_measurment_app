package com.body.measurement.utils;


import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.dto.AdditionalCircumference;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.CircumferenceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CircumferenceValidator implements Validator {
    private final static Logger log = LoggerFactory.getLogger(CircumferenceValidator.class);

    @Override
    public boolean validateCircumferenceData(CircumferenceData circumferenceData) throws MissingRequiredDataException, InvalidDataException{
        log.info("Start circumference data validation {}", circumferenceData);
        boolean result = false;
        if (circumferenceData.getBasicCircumference() != null) {
            result = this.checkRequiredField(circumferenceData.getBasicCircumference())
                    && this.checkSignOnFields(circumferenceData.getBasicCircumference());
            if (circumferenceData.getAdditionalCircumference() != null) {
                result = result && this.checkSignOnFields(circumferenceData.getAdditionalCircumference());
            }
        }
        log.info("Circumference data validation completed");
        return result;
    }

    @Override
    public boolean checkRequiredField(BasicCircumference basicCircumference) throws MissingRequiredDataException {
        log.info("Start checking required field in basic circumference {}", basicCircumference);
        if(basicCircumference.getAbdominal() == null || basicCircumference.getHip() == null
                || basicCircumference.getWaist() == null || basicCircumference.getChest() == null){
            throw new MissingRequiredDataException(BasicCircumference.class.getName());
        }
        log.info("Checking required field in basic circumference completed");
        return true;
    }

    @Override
    public boolean checkSignOnFields(BasicCircumference basicCircumference) throws InvalidDataException{
        log.info("Start check sign on fields for basic circumference {}", basicCircumference);
        if (basicCircumference.getChest() <= 0) {
            throw new InvalidDataException(BasicCircumference.class.getName(), "chest");
        } else if (basicCircumference.getHip() <= 0) {
            throw new InvalidDataException(BasicCircumference.class.getName(), "hip");
        } else if (basicCircumference.getAbdominal() <= 0) {
            throw new InvalidDataException(BasicCircumference.class.getName(), "abdominal");
        } else if (basicCircumference.getWaist() <= 0) {
            throw new InvalidDataException(BasicCircumference.class.getName(), "waist");
        }
        log.info("Check sign on fields for basic circumference completed");
        return true;
    }

    @Override
    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference) throws InvalidDataException{
        log.info("Start check sign on fields for additional circumference {}", additionalCircumference);
        if (additionalCircumference.getArmL() != null) {
            if (additionalCircumference.getArmL() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "armL");
            }
        }
        if (additionalCircumference.getArmR() != null) {
            if (additionalCircumference.getArmR() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "armR");
            }
        }
        if (additionalCircumference.getCalfR() != null) {
            if (additionalCircumference.getCalfR() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "calfR");
            }
        }
        if (additionalCircumference.getCalfL() != null) {
            if (additionalCircumference.getCalfL() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "calfL");
            }
        }
        if (additionalCircumference.getForarmL() != null) {
            if (additionalCircumference.getForarmL() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "forarmL");
            }
        }
        if (additionalCircumference.getForarmR() != null) {
            if (additionalCircumference.getForarmR() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "forarmR");
            }
        }
        if (additionalCircumference.getThighL() != null) {
            if (additionalCircumference.getThighL() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "thighL");
            }
        }
        if (additionalCircumference.getThighR() != null) {
            if (additionalCircumference.getThighR() <= 0) {
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "thighR");
            }
        }
        if (additionalCircumference.getNeck() != null) {
            if(additionalCircumference.getNeck() <= 0){
                throw new InvalidDataException(AdditionalCircumference.class.getName(), "neck");
            }
        }
        log.info("Check sign on fields for additional circumference completed");
        return true;
    }

}
