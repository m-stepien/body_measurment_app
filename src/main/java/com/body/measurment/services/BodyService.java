package com.body.measurment.services;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.dto.Weight;
import com.body.measurment.dto.responses.BodySaveResponse;
import com.body.measurment.repositories.BasicBodyDataRepository;
import com.body.measurment.repositories.WeightRepository;
import com.body.measurment.utils.BodyDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BodyService {
    private final WeightRepository weightRepository;
    private final BasicBodyDataRepository basicBodyDataRepository;
    private final BodyDataValidator validator;

    @Autowired
    public BodyService(WeightRepository weightRepository, BasicBodyDataRepository basicBodyDataRepository, BodyDataValidator validator) {
        this.weightRepository = weightRepository;
        this.basicBodyDataRepository = basicBodyDataRepository;
        this.validator = validator;
    }

    public BodySaveResponse saveBasicBodyData(BasicBodyData basicBodyData){
        BodySaveResponse bodySaveResponse = new BodySaveResponse();
        try{
            this.validator.isBodyDataValid(basicBodyData);
            this.basicBodyDataRepository.save(basicBodyData);
            bodySaveResponse.setBasicBodyData(basicBodyData);
            bodySaveResponse.setMessage("Basic body data save successful");
            bodySaveResponse.setSuccess(true);
        } catch (MissingRequiredDataException | InvalidDataException e) {
            bodySaveResponse.setBasicBodyData(basicBodyData);
            bodySaveResponse.setSuccess(false);
            bodySaveResponse.setMessage(e.getMessage());
        }
        return bodySaveResponse;
    }

    public BasicBodyData getBasicBodyData(long id){
        return this.basicBodyDataRepository.findById(id).orElse(null);
    }

    public void deleteBasicBodyData(long id){
        this.basicBodyDataRepository.deleteById(id);
    }

    public BodySaveResponse saveWeight(Weight weight){
        BodySaveResponse response = new BodySaveResponse();
        try{
            this.validator.isWeightValid(weight);
            this.setDefaultDataIfNeeded(weight);
            this.weightRepository.save(weight);
            response.setMessage("Weight save successful");
            response.setSuccess(true);
        } catch (MissingRequiredDataException | InvalidDataException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BodySaveResponse updateWeight(Weight weight){
        if (weight.getId() != null) {
        try {
            Weight updatedWeight = this.mapWeight(weight);
            return saveWeight(updatedWeight);
        }
        catch (NoSuchObjectInDatabaseException e){
            return setWeightSaveResponse(false, e.getMessage());
        }
        }
        else{
            return setWeightSaveResponse(false, "Id is required for update");
        }
    }

    public Weight getWeightById(long id){
        return this.weightRepository.findById(id).orElse(null);
    }

    public void deleteWeight(long id){
        this.weightRepository.deleteById(id);
    }

    private Weight mapWeight(Weight newWeight) throws NoSuchObjectInDatabaseException {
        Weight weight = this.weightRepository
                .findById(newWeight.getId())
                .orElseThrow(() -> new NoSuchObjectInDatabaseException("There is no Weight object with this id in database"));
        if(newWeight.getWeightInKg()!=null){
            weight.setWeightInKg(newWeight.getWeightInKg());
        }
        if(newWeight.getDate()!=null){
            weight.setDate(newWeight.getDate());
        }
        return weight;
    }

    private void setDefaultDataIfNeeded(Weight weight) {
        if (weight.getDate() == null) {
            weight.setDate(LocalDate.now());
        }
    }

    private BodySaveResponse setWeightSaveResponse(boolean success, String message){
        BodySaveResponse bodySaveResponse = new BodySaveResponse();
        bodySaveResponse.setSuccess(success);
        bodySaveResponse.setMessage(message);
        return bodySaveResponse;
    }
}
