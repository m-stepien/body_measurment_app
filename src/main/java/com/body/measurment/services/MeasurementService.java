package com.body.measurment.services;

import com.body.measurment.dto.AdditionalCircumference;
import com.body.measurment.dto.BasicCircumference;
import com.body.measurment.dto.CircumferenceData;
import com.body.measurment.repositories.AdditionalCircumferenceRepository;
import com.body.measurment.repositories.BasicCircumferenceRepository;
import com.body.measurment.repositories.CircumferenceDataRepository;
import com.body.measurment.utils.Validator;
import jakarta.persistence.Basic;
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
        boolean dataValid = this.validator.validateCircumferenceDatra(circumferenceData);
        if(dataValid) {
            if (circumferenceData.getMeasurmentDate() == null) {
                circumferenceData.setMeasurmentDate(LocalDate.now());
            }
            if (circumferenceData.getAdditionalCircumference() != null) {
                this.additionalCircumferenceRepository.save(circumferenceData.getAdditionalCircumference());
            }
            this.basicCircumferenceRepository.save(circumferenceData.getBasicCircumference());
            this.circumferenceDataRepository.save(circumferenceData);
        }
    }

    public void updateCircumference(CircumferenceData circumferenceData) {
        if (circumferenceData.getId() != null) {
            CircumferenceData updatedCircumferenceData = this.updateCircumferenceData(circumferenceData);
            saveMeasurement(updatedCircumferenceData);
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

    private CircumferenceData updateCircumferenceData(CircumferenceData circumferenceData) {
        CircumferenceData oldCircumfarenceData = this.circumferenceDataRepository.findById(circumferenceData.getId())
                .orElse(null);
        if (oldCircumfarenceData != null) {
            if (circumferenceData.getAdditionalCircumference() != null) {
                AdditionalCircumference oldAdditionalCircumference = oldCircumfarenceData.getAdditionalCircumference();
                if(oldAdditionalCircumference == null){
                    oldAdditionalCircumference = new AdditionalCircumference();
                }
                AdditionalCircumference additionalCircumference = circumferenceData.getAdditionalCircumference();
                if (additionalCircumference.getArmL() != null) {
                    oldAdditionalCircumference.setArmL(additionalCircumference.getArmL());
                }
                if (additionalCircumference.getArmR() != null) {
                    oldAdditionalCircumference.setArmR(additionalCircumference.getArmR());
                }
                if (additionalCircumference.getCalfL() != null) {
                    oldAdditionalCircumference.setCalfL(additionalCircumference.getCalfL());
                }
                if (additionalCircumference.getCalfR() != null) {
                    oldAdditionalCircumference.setCalfR(additionalCircumference.getCalfR());
                }
                if (additionalCircumference.getForarmR() != null) {
                    oldAdditionalCircumference.setForarmR(additionalCircumference.getForarmR());
                }
                if (additionalCircumference.getThighL() != null) {
                    oldAdditionalCircumference.setThighL(additionalCircumference.getThighL());
                }
                if (additionalCircumference.getThighR() != null) {
                    oldAdditionalCircumference.setThighR(additionalCircumference.getThighR());
                }
                if (additionalCircumference.getNeck() != null) {
                    oldAdditionalCircumference.setNeck(additionalCircumference.getNeck());
                }
                oldCircumfarenceData.setAdditionalCircumference(oldAdditionalCircumference);
            }
            if (circumferenceData.getBasicCircumference() != null){
                BasicCircumference basicCircumference = circumferenceData.getBasicCircumference();
                BasicCircumference oldBasicCircumference = oldCircumfarenceData.getBasicCircumference();
                if(basicCircumference.getAbdominal()!=null){
                    oldBasicCircumference.setAbdominal(basicCircumference.getAbdominal());
                }
                if(basicCircumference.getHip()!=null){
                    oldBasicCircumference.setHip(basicCircumference.getHip());
                }
                if(basicCircumference.getChest()!=null){
                    oldBasicCircumference.setChest(basicCircumference.getChest());
                }
                if(basicCircumference.getWaist()!=null){
                    oldBasicCircumference.setWaist(basicCircumference.getWaist());
                }
                oldCircumfarenceData.setBasicCircumference(oldBasicCircumference);
            }
            if(circumferenceData.getMeasurmentDate()!=null){
                oldCircumfarenceData.setMeasurmentDate(circumferenceData.getMeasurmentDate());
            }
        }
        return oldCircumfarenceData;
    }
}
