package com.body.measurment;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;
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
    public MeasurementService(Validator validator,
                              BasicCircumferenceRepository basicCircumferenceRepository,
                              AdditionalCircumferenceRepository additionalCircumferenceRepository,
                              CircumferenceDataRepository circumferenceDataRepository) {
        this.basicCircumferenceRepository = basicCircumferenceRepository;
        this.additionalCircumferenceRepository = additionalCircumferenceRepository;
        this.circumferenceDataRepository = circumferenceDataRepository;
        this.validator = validator;
    }

    public void saveMeasurement(CircumferenceData circumferenceData) {
        //todo validation
        this.circumferenceDataRepository.save(circumferenceData);
    }

    public void updateCircumference(CircumferenceData circumferenceData) {
        if (circumferenceData.getBasicCircumference().getId() != null) {
            saveMeasurement(circumferenceData);
        }
    }

    public CircumferenceData getCircumferenceDataById(long id) {
        return this.circumferenceDataRepository.findById(id).orElse(null);
    }

    public List<CircumferenceData> getCircumferenceDataFromDate(LocalDate startDate) {
        return this.circumferenceDataRepository
                .findByMeasurmentDateGreaterThanEqual(startDate);
    }

    public List<CircumferenceData> getCircumferenceDataInDateRange(LocalDate startDate, LocalDate endDate) {
        return this.circumferenceDataRepository
                .findByMeasurmentDateBetweenOrderByMeasurmentDateDesc(startDate, endDate);
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
}
