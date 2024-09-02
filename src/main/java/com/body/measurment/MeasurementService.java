package com.body.measurment;

import com.body.measurment.custom.exception.InvalidDataException;
import com.body.measurment.custom.exception.MissingRequiredDataException;
import com.body.measurment.dto.CircumferenceData;
import com.body.measurment.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    private final Validator validator;
    private final BasicCircumferenceRepository basicCircumferenceRepository;
    private final AdditionalCircumferenceRepository additionalCircumferenceRepository;

    @Autowired
    public MeasurementService(Validator validator,
                              BasicCircumferenceRepository basicCircumferenceRepository,
                              AdditionalCircumferenceRepository additionalCircumferenceRepository) {
        this.basicCircumferenceRepository = basicCircumferenceRepository;
        this.additionalCircumferenceRepository = additionalCircumferenceRepository;
        this.validator = validator;
    }

    public void saveMeasurement(CircumferenceData circumferenceData) {
        try {
            long id = this.saveAdditionalCircumference(circumferenceData.getAdditionalCircumference());
            circumferenceData.getBasicCircumference().setAdditionalCircumference(circumferenceData.getAdditionalCircumference());
            this.saveBasicCircumference(circumferenceData.getBasicCircumference());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCircumference(CircumferenceData circumferenceData) {
        if(circumferenceData.getBasicCircumference().getId()!=null) {
            saveMeasurement(circumferenceData);
        }
    }

    public CircumferenceData getCircumferenceDataById(long id) {
        CircumferenceData circumferenceData = null;
        Optional<BasicCircumference> basicCircumference = this.basicCircumferenceRepository.findById(id);
        AdditionalCircumference additionalCircumference;
        if (basicCircumference.isPresent()) {
            if(basicCircumference.get().getAdditionalCircumference()!=null) {
                additionalCircumference =basicCircumference.get().getAdditionalCircumference();
            }
            else{
                additionalCircumference = null;
            }
            circumferenceData = new CircumferenceData(basicCircumference.get(), additionalCircumference);
        }
        return circumferenceData;
    }

    public List<CircumferenceData> getCircumferenceDataFromDate(LocalDate startDate){
        List<BasicCircumference> basicCircumferenceList = this.basicCircumferenceRepository.findByMeasurmentDateGreaterThanEqual(startDate);
        List<CircumferenceData> circumferenceDataList = this.mapBasicCircumferenceListToCircumferenceDate(basicCircumferenceList);
        return circumferenceDataList;
    }

    public List<CircumferenceData> getCircumferenceDataInDateRange(LocalDate startDate, LocalDate endDate){
        List<BasicCircumference> basicCircumferenceList = this.basicCircumferenceRepository
                .findByMeasurmentDateBetweenOrderByMeasurmentDateDesc(startDate, endDate);
        List<CircumferenceData> circumferenceDataList = this.mapBasicCircumferenceListToCircumferenceDate(basicCircumferenceList);
        return circumferenceDataList;
    }

    public List<CircumferenceData> getCircumferenceDataAll(){
        List<BasicCircumference> basicCircumferences = this.basicCircumferenceRepository.findAll();
        List<CircumferenceData> circumferenceDataList = this.mapBasicCircumferenceListToCircumferenceDate(basicCircumferences);
        return circumferenceDataList;
    }

    public void deleteCircumferenceById(long id) {
        this.basicCircumferenceRepository.deleteById(id);
    }

    public void deleteAllCircumference(){
        this.basicCircumferenceRepository.deleteAll();
    }

    private void saveBasicCircumference(BasicCircumference basicCircumference) throws Exception {
        if (this.validator.checkRequiredField(basicCircumference)) {
            if (this.validator.checkSignOnFields(basicCircumference)) {
                basicCircumferenceRepository.save(basicCircumference);
            } else {
                throw new InvalidDataException(basicCircumference.getClass().getName());
            }
        } else {
            throw new MissingRequiredDataException(basicCircumference.getClass().getName());
        }
    }

    private long saveAdditionalCircumference(AdditionalCircumference additionalCircumference) throws Exception {
        if (this.validator.checkSignOnFields(additionalCircumference)) {
            additionalCircumferenceRepository.save(additionalCircumference);
            return additionalCircumference.getId();
        } else {
            throw new InvalidDataException(additionalCircumference.getClass().getName());
        }
    }

    private List<CircumferenceData> mapBasicCircumferenceListToCircumferenceDate(List<BasicCircumference> basicCircumferenceList) {
        List<CircumferenceData> circumferenceDataList = basicCircumferenceList.stream().map(basicCircumference -> {
            AdditionalCircumference additionalCircumference = basicCircumference.getAdditionalCircumference();
            CircumferenceData circumferenceData = new CircumferenceData(basicCircumference, additionalCircumference);
            return circumferenceData;
        }).toList();
        return circumferenceDataList;
    }

    private boolean checkIsBasicCircumferenceUpdated(BasicCircumference basicCircumference) {
        return basicCircumference != null;
    }

    private boolean checkIsAdditionalCircumferenceUpdated(AdditionalCircumference additionalCircumference) {
        return additionalCircumference != null;
    }
}