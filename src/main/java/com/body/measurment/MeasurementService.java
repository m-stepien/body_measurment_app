package com.body.measurment;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.CircumferenceData;
import com.body.measurment.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeasurementService{
    private final Validator validator;
    private final BasicCircumferenceRepository basicCircumferenceRepository;
    private final AdditionalCircumferenceRepository additionalCircumferenceRepository;
    @Autowired
    public MeasurementService(Validator validator,
                              BasicCircumferenceRepository basicCircumferenceRepository,
                              AdditionalCircumferenceRepository additionalCircumferenceRepository){
        this.basicCircumferenceRepository = basicCircumferenceRepository;
        this.additionalCircumferenceRepository = additionalCircumferenceRepository;
        this.validator = validator;
    }

    public void addNewMeasurement(CircumferenceData circumferenceData){
        try {
            this.addNewBasicCircumference(circumferenceData.getBasicCircumference());
            this.addNewAdditionalCircumference(circumferenceData.getAdditionalCircumference());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteCircumferenceById(long id){
            this.basicCircumferenceRepository.deleteById(id);
    }

    public void updateCircumgerence(CircumferenceData circumferenceData){
        if(checkIsBasicCircumferenceUpdated(circumferenceData.getBasicCircumference())){
            this.basicCircumferenceRepository.updateBasicCircumference(circumferenceData.getBasicCircumference());
        }
        if(checkIsAdditionalCircumferenceUpdated(circumferenceData.getAdditionalCircumference())){
            this.additionalCircumferenceRepository.updateAdditionalCircumference(circumferenceData.getAdditionalCircumference());
        }
    }

    public Optional<BasicCircumference> circumferenceData(long id){
        //propably no sense to do that. Better option returning CircumferenceData full.
        Optional<BasicCircumference> basicCircumference = this.basicCircumferenceRepository.findById(id);
        return basicCircumference;
    }

    private void addNewBasicCircumference(BasicCircumference basicCircumference) throws Exception{
        if(this.validator.checkRequiredField(basicCircumference)){
            if(this.validator.checkSignOnFields(basicCircumference)){
                basicCircumferenceRepository.save(basicCircumference);
            }
            else{
                throw new InvalidDataException(basicCircumference.getClass().getName());
            }
        }
        else{
            throw new MissingRequiredDataException(basicCircumference.getClass().getName());
        }
    }

    private void addNewAdditionalCircumference(AdditionalCircumference additionalCircumference) throws Exception{
        if(this.validator.checkSignOnFields(additionalCircumference)){
            additionalCircumferenceRepository.save(additionalCircumference);
        }
        else{
            throw new InvalidDataException(additionalCircumference.getClass().getName());
        }
    }

    private boolean checkIsBasicCircumferenceUpdated(BasicCircumference basicCircumference){
        return basicCircumference!=null;
    }

    private boolean checkIsAdditionalCircumferenceUpdated(AdditionalCircumference additionalCircumference){
        return additionalCircumferenceRepository!=null;
    }
}