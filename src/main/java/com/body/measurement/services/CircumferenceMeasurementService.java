package com.body.measurement.services;

import com.body.measurement.custom.exception.DatabaseException;
import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurement.dto.AdditionalCircumference;
import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.CircumferenceData;
import com.body.measurement.repositories.AdditionalCircumferenceRepository;
import com.body.measurement.repositories.BasicCircumferenceRepository;
import com.body.measurement.repositories.CircumferenceDataRepository;
import com.body.measurement.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

@Service
public class CircumferenceMeasurementService {
    private static final Logger log = LoggerFactory.getLogger(CircumferenceMeasurementService.class);
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

    public CircumferenceData saveCircumferenceMeasurement(CircumferenceData circumferenceData) throws InvalidDataException, MissingRequiredDataException, DatabaseException {
        log.info("Starting save circumgerence measurement {}", circumferenceData);
        try {
            this.validator.validateCircumferenceData(circumferenceData);
        } catch (InvalidDataException | MissingRequiredDataException e) {
            log.error("Exception during validation CircumferenceData {}", circumferenceData);
            log.error(e.getMessage());
            throw e;
        }
        this.setDefaultDataIfNeeded(circumferenceData);
        try {
            circumferenceData = this.saveCircumferenceData(circumferenceData);
        } catch (DatabaseException e) {
            log.error("Failed to save CircumfereceData {} in database exception {}", circumferenceData, e.getMessage());
            throw e;
        }
        log.info("Saving circumference data completed");
        return circumferenceData;
    }

    public CircumferenceData updateCircumference(CircumferenceData circumferenceData) throws NoSuchObjectInDatabaseException, MissingRequiredDataException, InvalidDataException, DatabaseException {
        log.info("Start update CircumferenceData {}", circumferenceData);
        CircumferenceData updatedCircumferenceData;
        if (circumferenceData.getId() != null) {
            try {
                updatedCircumferenceData = this.mapCircumferenceData(circumferenceData);
                this.saveCircumferenceMeasurement(updatedCircumferenceData);
            }
            catch (NoSuchObjectInDatabaseException e){
                log.error("Failed to update CircumferenceData {}", circumferenceData);
                log.error(e.getMessage());
                throw e;
            }
        }
        else{
            log.info("Cannot update CircumferenceData because id is null");
            throw new MissingRequiredDataException(BasicCircumference.class.getName());
        }
        log.info("Finished update circumference data {}", circumferenceData);
        return updatedCircumferenceData;
    }

    public CircumferenceData getCircumferenceDataById(long id) {
        log.info("Start searching for circumference data with id {}", id);
        CircumferenceData circumferenceData = this.circumferenceDataRepository.findById(id).orElse(null);
        log.info("Found circumference data in database for id {} {}", id, circumferenceData);
        return circumferenceData;
    }

    public BasicCircumference getBasicCircumferenceByDate(LocalDate date){
        log.info("Start searching basic circumference with date {}", date);
        BasicCircumference basicCircumference = this.circumferenceDataRepository.findBasicCircumferenceByDate(date)
                                                                                .orElse(new BasicCircumference());
        log.info("Found basic circumference with date {} {}", date, basicCircumference);
        return basicCircumference;
    }

    public List<CircumferenceData> getCircumferenceDataFromDate(LocalDate startDate) {
        log.info("Start searching for circumference data with date greater then {}", startDate);
        List<CircumferenceData> circumferenceDataList = this.circumferenceDataRepository
                                                                    .findByMeasurementDateGreaterThanEqual(startDate);
        log.info("Circumference data with date greater then {}\n{}", startDate, circumferenceDataList);
        return circumferenceDataList;
    }

    public List<CircumferenceData> getCircumferenceDataInDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Start searching for circumference data with date between dates {} {}", startDate, endDate);
        List<CircumferenceData> circumferenceDataList = this.circumferenceDataRepository
                                                .findByMeasurementDateBetweenOrderByMeasurementDate(startDate, endDate);
        log.info("Circumference data with date between {} {}\n{}", startDate, endDate,  circumferenceDataList);
        return circumferenceDataList;
    }

    public List<CircumferenceData> getCircumferenceDataAll() {
        log.info("Start get all circumference data");
        List<CircumferenceData> circumferenceDataList = this.circumferenceDataRepository.findAll();
        log.info("All circumference data in database {}", circumferenceDataList);
        return circumferenceDataList;
    }

    public void deleteCircumferenceById(long id) {
        log.info("Start delete circumference data for id {}", id);
        this.circumferenceDataRepository.deleteById(id);
        log.info("Finished deleting circumfernce data for id {}", id);
    }

    public void deleteAllCircumference() {
        log.info("Start deleting all circumference data in database");
        this.circumferenceDataRepository.deleteAll();
        log.info("Finished deleting all circumference data in database");
    }

    private CircumferenceData mapCircumferenceData(CircumferenceData circumferenceData) throws NoSuchObjectInDatabaseException{
        log.info("Start mapping circumference data to object {}", circumferenceData);
        CircumferenceData oldCircumfarenceData = this.circumferenceDataRepository
                .findById(circumferenceData.getId())
                .orElseThrow(() -> new NoSuchObjectInDatabaseException("There is no CircumferenceData object with this id in database"));
        log.info("Data found in database for id {} {}", circumferenceData.getId(), oldCircumfarenceData);
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
        log.info("Finished mapping circumference data {}", oldCircumfarenceData);
        return oldCircumfarenceData;
    }

    private AdditionalCircumference mapNewDataToAdditionalCircumference(AdditionalCircumference additionalCircumference,
                                                                        AdditionalCircumference newData) {
        log.info("Start mapping additional circumference {} to object {}", newData, additionalCircumference);
        updateNotNullFields(additionalCircumference, newData);
        log.info("Finished mapping additional circumference data {}", additionalCircumference);
        return additionalCircumference;
    }

    private BasicCircumference mapNewDataToBasicCircumference(BasicCircumference basicCircumference,
                                                              BasicCircumference newData) {
        log.info("Start mapping basic circumference {} to object {}", newData, basicCircumference);
        updateNotNullFields(basicCircumference, newData);
        log.info("Finished mapping basic circumference data {}", basicCircumference);
        return basicCircumference;
    }

    @Transactional
    private CircumferenceData saveCircumferenceData(CircumferenceData circumferenceData) throws DatabaseException {
        log.info("Start save circumference data {}", circumferenceData);
        try {
            if (circumferenceData.getAdditionalCircumference() != null) {
                log.info("Saving additional circumference {}", circumferenceData.getAdditionalCircumference());
                this.additionalCircumferenceRepository.save(circumferenceData.getAdditionalCircumference());
            }
            log.info("Save basic circumference {}", circumferenceData.getBasicCircumference());
            this.basicCircumferenceRepository.save(circumferenceData.getBasicCircumference());
            log.info("Save circumference data {}", circumferenceData);
            this.circumferenceDataRepository.save(circumferenceData);
        } catch (Exception e) {
            log.error("Failed to save circumference data {}", circumferenceData);
            log.error(e.getMessage());
            throw new DatabaseException("Failed to save CircumferenceData\n" + e.getMessage());
        }
        log.info("Finised save circumference data {}", circumferenceData);
        return circumferenceData;
    }

    private void setDefaultDataIfNeeded(CircumferenceData circumferenceData) {
        if (circumferenceData.getMeasurementDate() == null) {
            log.info("Set default date for circuference datea {}", circumferenceData);
            circumferenceData.setMeasurementDate(LocalDate.now());
        }
        log.info("Setting default date for circumference data completed {}", circumferenceData);
    }

    private <T> void updateNotNullFields(T target, T source){
        Field[] fields = source.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            try{
                Object value = field.get(source);
                if(value != null){
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                log.error("Failed to map field {} due to access issues", field.getName(), e);
            }
        }
    }
}
