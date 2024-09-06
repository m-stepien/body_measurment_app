package com.body.measurment.services;

import com.body.measurment.custom.exception.DatabaseException;
import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;
import com.body.measurment.dto.responses.CircumferenceDataSaveResponse;
import com.body.measurment.repositories.AdditionalCircumferenceRepository;
import com.body.measurment.repositories.BasicCircumferenceRepository;
import com.body.measurment.repositories.CircumferenceDataRepository;
import com.body.measurment.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeasurementService {
    private final Validator validator;
    private final BasicCircumferenceRepository basicCircumferenceRepository;
    private final AdditionalCircumferenceRepository additionalCircumferenceRepository;
    private final CircumferenceDataRepository circumferenceDataRepository;

    @Autowired
    public MeasurementService(Validator validator, BasicCircumferenceRepository basicCircumferenceRepository, AdditionalCircumferenceRepository additionalCircumferenceRepository, CircumferenceDataRepository circumferenceDataRepository) {
        this.basicCircumferenceRepository = basicCircumferenceRepository;
        this.additionalCircumferenceRepository = additionalCircumferenceRepository;
        this.circumferenceDataRepository = circumferenceDataRepository;
        this.validator = validator;
    }

    public CircumferenceDataSaveResponse saveMeasurement(CircumferenceData circumferenceData) {
        CircumferenceDataSaveResponse circumferenceDataSaveResponse = new CircumferenceDataSaveResponse();
        try {
            this.validator.validateCircumferenceData(circumferenceData);
        } catch (InvalidDataException | MissingRequiredDataException e) {
            return this.prepereResponseForSaveOperation(false, e.getMessage(), circumferenceData);
        }
        this.setDefaultDataIfNeeded(circumferenceData);
        try {
            circumferenceData = this.saveCircumferenceData(circumferenceData);
        } catch (DatabaseException e) {
            return this.prepereResponseForSaveOperation(false, "Failed save to database", circumferenceData);
        }
        return this.prepereResponseForSaveOperation(true, "Save CircumferenceData successful", circumferenceData);

    }

    public CircumferenceDataSaveResponse updateCircumference(CircumferenceData circumferenceData) {
        if (circumferenceData.getId() != null) {
            try {
                CircumferenceData updatedCircumferenceData = this.mapCircumferenceData(circumferenceData);
                return saveMeasurement(updatedCircumferenceData);
            }
            catch (NoSuchObjectInDatabaseException e){
                return prepereResponseForSaveOperation(false, e.getMessage(), circumferenceData);
            }
        }
        else{
            return this.prepereResponseForSaveOperation(
                    false, "Id is required for update",
                    circumferenceData);
        }

    }

    public CircumferenceData getCircumferenceDataById(long id) {
        return this.circumferenceDataRepository.findById(id).orElse(null);
    }

    public List<CircumferenceData> getCircumferenceDataFromDate(LocalDate startDate) {
        return this.circumferenceDataRepository.findByMeasurmentDateGreaterThanEqual(startDate);
    }

    public List<CircumferenceData> getCircumferenceDataInDateRange(LocalDate startDate, LocalDate endDate) {
        return this.circumferenceDataRepository.findByMeasurmentDateBetweenOrderByMeasurmentDateDesc(startDate, endDate);
    }

    public List<CircumferenceData> getCircumferenceDataAll() {
        return this.circumferenceDataRepository.findAll();
    }

    public void deleteCircumferenceById(long id) {
        this.circumferenceDataRepository.deleteById(id);
    }

    public void deleteAllCircumference() {
        this.circumferenceDataRepository.deleteAll();
    }

    private CircumferenceData mapCircumferenceData(CircumferenceData circumferenceData) throws NoSuchObjectInDatabaseException{

        CircumferenceData oldCircumfarenceData = this.circumferenceDataRepository
                .findById(circumferenceData.getId())
                .orElseThrow(() -> new NoSuchObjectInDatabaseException("There is no CircumferenceData object with this id in database"));
        if (oldCircumfarenceData != null) {
            if (circumferenceData.getAdditionalCircumference() != null) {
                oldCircumfarenceData.setAdditionalCircumference(
                        this.mapNewDataToAdditionalCircumference(
                                oldCircumfarenceData.getAdditionalCircumference(),
                                circumferenceData.getAdditionalCircumference())
                );
            }
            if (circumferenceData.getBasicCircumference() != null) {
                oldCircumfarenceData.setBasicCircumference(this.mapNewDataToBasicCircumference(
                        oldCircumfarenceData.getBasicCircumference(),
                        circumferenceData.getBasicCircumference()));
            }
            if (circumferenceData.getMeasurmentDate() != null) {
                oldCircumfarenceData.setMeasurmentDate(circumferenceData.getMeasurmentDate());
            }
        }
        return oldCircumfarenceData;
    }

    private AdditionalCircumference mapNewDataToAdditionalCircumference(AdditionalCircumference additionalCircumference,
                                                                        AdditionalCircumference newData) {
        if (additionalCircumference == null) {
            additionalCircumference = new AdditionalCircumference();
        }
        if (newData.getArmL() != null) {
            additionalCircumference.setArmL(newData.getArmL());
        }
        if (newData.getArmR() != null) {
            additionalCircumference.setArmR(newData.getArmR());
        }
        if (newData.getCalfL() != null) {
            additionalCircumference.setCalfL(newData.getCalfL());
        }
        if (newData.getCalfR() != null) {
            additionalCircumference.setCalfR(newData.getCalfR());
        }
        if (newData.getForarmL() != null) {
            additionalCircumference.setForarmL(newData.getForarmL());
        }
        if (newData.getForarmR() != null) {
            additionalCircumference.setForarmR(newData.getForarmR());
        }
        if (newData.getThighL() != null) {
            additionalCircumference.setThighL(newData.getThighL());
        }
        if (newData.getThighR() != null) {
            additionalCircumference.setThighR(newData.getThighR());
        }
        if (newData.getNeck() != null) {
            additionalCircumference.setNeck(newData.getNeck());
        }
        return additionalCircumference;
    }

    private BasicCircumference mapNewDataToBasicCircumference(BasicCircumference basicCircumference,
                                                              BasicCircumference newData) {
        if (newData.getAbdominal() != null) {
            basicCircumference.setAbdominal(newData.getAbdominal());
        }
        if (newData.getHip() != null) {
            basicCircumference.setHip(newData.getHip());
        }
        if (newData.getChest() != null) {
            basicCircumference.setChest(newData.getChest());
        }
        if (newData.getWaist() != null) {
            basicCircumference.setWaist(newData.getWaist());
        }
        return basicCircumference;
    }

    private CircumferenceData saveCircumferenceData(CircumferenceData circumferenceData) throws DatabaseException {
        try {
            if (circumferenceData.getAdditionalCircumference() != null) {
                this.additionalCircumferenceRepository.save(circumferenceData.getAdditionalCircumference());
            }
            this.basicCircumferenceRepository.save(circumferenceData.getBasicCircumference());
            this.circumferenceDataRepository.save(circumferenceData);
        } catch (Exception e) {
            throw new DatabaseException("Failed to save CircumferenceData\n" + e.getMessage());
        }
        return circumferenceData;
    }

    private CircumferenceData setDefaultDataIfNeeded(CircumferenceData circumferenceData) {
        if (circumferenceData.getMeasurmentDate() == null) {
            circumferenceData.setMeasurmentDate(LocalDate.now());
        }
        return circumferenceData;
    }

    private CircumferenceDataSaveResponse prepereResponseForSaveOperation(boolean success, String message, CircumferenceData circumferenceData) {
        CircumferenceDataSaveResponse circumferenceDataSaveResponse = new CircumferenceDataSaveResponse();
        circumferenceDataSaveResponse.setSuccess(success);
        circumferenceDataSaveResponse.setMessage(message);
        circumferenceDataSaveResponse.setCircumferenceData(circumferenceData);
        return circumferenceDataSaveResponse;
    }
}
