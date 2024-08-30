package com.body.measurment;


import org.springframework.stereotype.Component;

@Component
public class CircumferenceValidator implements Validator{

    @Override
    public boolean checkRequiredField(Circumference circumference) {
        return circumference.abdominal() != null && circumference.hip() != null
                && circumference.waist() != null && circumference.chest() != null;
    }

    @Override
    public boolean checkSignOnFields(Circumference circumference) {
        boolean result=true;
        if(circumference.chest()<=0){
            result = false;
        }
        else if(circumference.hip()<=0){
            result = false;
        }
        else if(circumference.abdominal()<=0){
            result = false;
        }
        else if(circumference.waist()<=0){
            result = false;
        }
        return result;
    }
}
