package com.body.measurement.services;

import com.body.measurement.custom.exception.DatabaseException;
import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurement.dto.AdditionalCircumference;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.CircumferenceData;
import com.body.measurement.dto.responses.CircumferenceDataSaveResponse;
import com.body.measurement.repositories.AdditionalCircumferenceRepository;
import com.body.measurement.repositories.BasicCircumferenceRepository;
import com.body.measurement.repositories.CircumferenceDataRepository;
import com.body.measurement.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CircumferenceMeasurementService {
    private final Validator validator;
    private final BasicCircumferenceRepository basicCircumferenceRepository;
    private final AdditionalCircumferenceRepository additionalCircumferenceRepository;
    private final CircumferenceDataRepository circumferenceDataRepository;

    @Autowired
    public CircumferenceMeasurementService(Validator validator, BasicCircumferenceRepository basicCircumferenceRepository, AdditionalCircumferenceRepository additionalCircumferenceRepository, CircumferenceDataRepository circumferenceDataRepository) {
        this.basicCircumferenceRepository = basicCircumferenceRepository;
        this.additionalCircumferenceRepository = additionalCircumferenceRepository;
        this.circumferenceDataRepository = circumferenceDataRepository;
        this.validator = validator;
    }

    public CircumferenceDataSaveResponse saveCircumferenceMeasurement(CircumferenceData circumferenceData) {
        try {
            this.validator.validateCircumferenceData(circumferenceData);
        } catch (InvalidDataException | MissingRequiredDataException e) {
            return this.prepareResponseForSaveOperation(false, e.getMessage(), circumferenceData);
        }
        this.setDefaultDataIfNeeded(circumferenceData);
        try {
            circumferenceData = this.saveCircumferenceData(circumferenceData);
        } catch (DatabaseException e) {
            return this.prepareResponseForSaveOperation(false, "Failed save to database", circumferenceData);
        }
        return this.prepareResponseForSaveOperation(true, "Save CircumferenceData successful", circumferenceData);

    }

    public CircumferenceDataSaveResponse updateCircumference(CircumferenceData circumferenceData) {
        if (circumferenceData.getId() != null) {
            try {
                CircumferenceData updatedCircumferenceData = this.mapCircumferenceData(circumferenceData);
                return saveCircumferenceMeasurement(updatedCircumferenceData);
            }
            catch (NoSuchObjectInDatabaseException e){
                return prepareResponseForSaveOperation(false, e.getMessage(), circumferenceData);
            }
        }
        else{
            return this.prepareResponseForSaveOperation(
                    false, "Id is required for update",
                    circumferenceData);
        }

    }

    public CircumferenceData getCircumferenceDataById(long id) {
        return this.circumferenceDataRepository.findById(id).orElse(null);
    }

    public List<CircumferenceData> getCircumferenceDataFromDate(LocalDate startDate) {
        return this.circumferenceDataRepository.findByMeasurementDateGreaterThanEqual(startDate);
    }

    public List<CircumferenceData> getCircumferenceDataInDateRange(LocalDate startDate, LocalDate endDate) {
        return this.circumferenceDataRepository.findByMeasurementDateBetweenOrderByMeasurementDateDesc(startDate, endDate);
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
            if (circumferenceData.getMeasurementDate() != null) {
                oldCircumfarenceData.setMeasurementDate(circumferenceData.getMeasurementDate());
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

    private void setDefaultDataIfNeeded(CircumferenceData circumferenceData) {
        if (circumferenceData.getMeasurementDate() == null) {
            circumferenceData.setMeasurementDate(LocalDate.now());
        }
    }

    private CircumferenceDataSaveResponse prepareResponseForSaveOperation(boolean success, String message, CircumferenceData circumferenceData) {
        CircumferenceDataSaveResponse circumferenceDataSaveResponse = new CircumferenceDataSaveResponse();
        circumferenceDataSaveResponse.setSuccess(success);
        circumferenceDataSaveResponse.setMessage(message);
        circumferenceDataSaveResponse.setCircumferenceData(circumferenceData);
        return circumferenceDataSaveResponse;
    }
}
