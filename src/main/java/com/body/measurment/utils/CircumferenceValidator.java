package com.body.measurment.utils;


import com.body.measurment.AdditionalCircumference;
import com.body.measurment.BasicCircumference;
import org.springframework.stereotype.Component;

@Component
public class CircumferenceValidator implements Validator {

    @Override
    public boolean checkRequiredField(BasicCircumference basicCircumference) {
        return basicCircumference.getAbdominal() != null && basicCircumference.getHip() != null
                && basicCircumference.getWaist() != null && basicCircumference.getChest() != null;
    }

    @Override
    public boolean checkSignOnFields(BasicCircumference basicCircumference) {
        boolean result = true;
        if (basicCircumference.getChest() <= 0) {
            result = false;
        } else if (basicCircumference.getHip() <= 0) {
            result = false;
        } else if (basicCircumference.getAbdominal() <= 0) {
            result = false;
        } else if (basicCircumference.getWaist() <= 0) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkSignOnFields(AdditionalCircumference additionalCircumference) {
        if (additionalCircumference.getArmL() != null) {
            if (additionalCircumference.getArmL() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getArmR() != null) {
            if (additionalCircumference.getArmR() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getCalfR() != null) {
            if (additionalCircumference.getCalfR() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getCalfL() != null) {
            if (additionalCircumference.getCalfL() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getForarmL() != null) {
            if (additionalCircumference.getForarmL() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getForarmR() != null) {
            if (additionalCircumference.getForarmR() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getThighL() != null) {
            if (additionalCircumference.getThighL() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getThighR() != null) {
            if (additionalCircumference.getThighR() <= 0) {
                return false;
            }
        }
        if (additionalCircumference.getNeck() != null) {
            return additionalCircumference.getNeck() > 0;
        }
        return true;
    }

}
