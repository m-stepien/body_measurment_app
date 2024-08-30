package com.body.measurment;


import org.springframework.stereotype.Component;

@Component
public class CircumferenceValidator implements Validator{

    @Override
    public boolean checkRequiredField(BasicCircumference basicCircumference) {
        return basicCircumference.getAbdominal() != null && basicCircumference.getHip() != null
                && basicCircumference.getWaist() != null && basicCircumference.getChest() != null;
    }

    @Override
    public boolean checkSignOnFields(BasicCircumference basicCircumference) {
        boolean result=true;
        if(basicCircumference.getChest()<=0){
            result = false;
        }
        else if(basicCircumference.getHip()<=0){
            result = false;
        }
        else if(basicCircumference.getAbdominal()<=0){
            result = false;
        }
        else if(basicCircumference.getWaist()<=0){
            result = false;
        }
        return result;
    }
}
