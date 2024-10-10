package com.body.measurement.services;

import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.custom.exception.NoSuchObjectInDatabaseException;
import com.body.measurement.dto.BodyDetails;
import com.body.measurement.dto.Weight;
import com.body.measurement.repositories.BodyDetailsRepository;
import com.body.measurement.repositories.WeightRepository;
import com.body.measurement.utils.BodyDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BodyService {

    private static final Logger log = LoggerFactory.getLogger(BodyService.class);
    private final WeightRepository weightRepository;
    private final BodyDetailsRepository bodyDetailsRepository;
    private final BodyDataValidator validator;

    @Autowired
    public BodyService(WeightRepository weightRepository, BodyDetailsRepository bodyDetailsRepository, BodyDataValidator validator) {
        this.weightRepository = weightRepository;
        this.bodyDetailsRepository = bodyDetailsRepository;
        this.validator = validator;
    }

    public void saveBodyDetails(BodyDetails bodyDetails) throws MissingRequiredDataException, InvalidDataException {
        log.info("Start saving body details");
        try {
            this.validator.isBodyDetailsValid(bodyDetails);
            this.bodyDetailsRepository.save(bodyDetails);
            log.info("Object save successful {}", bodyDetails);
        } catch (MissingRequiredDataException | InvalidDataException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BodyDetails getBodyDetailsData(long id) {
        log.info("Searching for body details with id: {}", id);
        BodyDetails bodyDetails = this.bodyDetailsRepository.findById(id).orElse(null);
        log.info("Found in database {}", bodyDetails);
        return bodyDetails;
    }

    public void deleteBodyDetailsData(long id) {
        log.info("Deleting body details with id: {}", id);
        this.bodyDetailsRepository.deleteById(id);
        log.info("Deleting body details with id: {} completed", id);
    }

    public void saveWeight(Weight weight) throws MissingRequiredDataException, InvalidDataException {
        log.info("Starting weight save");
        try {
            this.validator.isWeightValid(weight);
            this.setDefaultDataIfNeeded(weight);
            log.info("Checking if weight for given date already exist");
            Weight currentRecordOnDatabase = this.getWeightOnDate(weight.getDate());
            if(currentRecordOnDatabase!=null) {
                log.info("Deleting old weight for date {} {}", weight.getDate(), currentRecordOnDatabase);
                this.weightRepository.deleteById(currentRecordOnDatabase.getId());
            }
            log.info("Save in database weight: {}", weight);
            this.weightRepository.save(weight);
            log.info("Save successful weight: {}", weight);
        } catch (MissingRequiredDataException | InvalidDataException e) {
            log.error("Exception while trying to save weight {}", weight);
            log.error(e.getMessage());
            throw e;
        }
    }

    public Weight updateWeight(Weight weight) throws NoSuchObjectInDatabaseException, MissingRequiredDataException, InvalidDataException {
        log.info("Starting update weight {}", weight);
        Weight updatedWeight = null;
        if (weight.getId() != null) {
            try {
                updatedWeight = this.mapWeight(weight);
                saveWeight(updatedWeight);
            } catch (NoSuchObjectInDatabaseException e) {
                log.error("Exception during update weight {}", weight);
                log.error(e.getMessage());
                throw e;
            } catch (MissingRequiredDataException | InvalidDataException e){
                throw e;
            }
        }
        else {
            log.info("Could not update weight because weight id is null {}", weight);
        }
        return updatedWeight;
    }

    public List<Weight> getWeightBetweenDates(LocalDate start, LocalDate end){
        log.info("Start search for weights between given dates. Start: {} End {}", start, end);
        List<Weight> weightList = this.weightRepository.findByDateBetween(start, end);
        log.info("Result of weight search between dates {} {} \n{}",start, end, weightList);
        return weightList;
    }

    public Weight getWeightFirst() {
        log.info("Searching for first weight in database");
        Weight weight = this.weightRepository.findTopByOrderByDateAsc().orElse(null);
        log.info("First weight in database {}", weight);
        return weight;
    }
    public Weight getWeightById(long id) {
        log.info("Searching database for weight with id {}", id);
        Weight weight = this.weightRepository.findById(id).orElse(null);
        log.info("For id {} found weight {}", id, weight);
        return weight;
    }

    public Weight getWeightOnDate(LocalDate date){
        log.info("Searching database for weight with date {}", date);
        Weight weight = this.weightRepository.findByDate(date).orElse(null);
        log.info("For date {} found weight {}", date, weight);
        return weight;
    }

    public Weight getWeightOneBefore(LocalDate date){
        log.info("Searching database for for first weight before date {}", date);
        Weight weight = this.weightRepository.findOneBeforeGiven(date).orElse(null);
        log.info("First weight before date {} {}", date, weight);
        return weight;
    }

    public Weight getWeightOneAfter(LocalDate date){
        log.info("Searching database for for first weight after date {}", date);
        Weight weight = this.weightRepository.findOneAfterGiven(date).orElse(new Weight());
        log.info("First weight after date {} {}", date, weight);
        return weight;
    }

    public Weight getWeightLast(){
        log.info("Searching for last weight in database");
        Weight weight = this.weightRepository.findTopByOrderByDateDesc().orElse(null);
        log.info("Last weight in database {}", weight);
        return weight;
    }

    public void deleteWeight(long id) {
        log.info("Deleting weight with id: {}", id);
        this.weightRepository.deleteById(id);
        log.info("Deleting weight with id: {} completed", id);
    }

    private Weight mapWeight(Weight newWeight) throws NoSuchObjectInDatabaseException {
        log.info("Starting maping data from database to update object {}", newWeight);
        Weight weight = this.weightRepository
                .findById(newWeight.getId())
                .orElseThrow(() -> new NoSuchObjectInDatabaseException("There is no Weight object with this id in database"));
        log.info("Data find in database for id {} {}", newWeight.getId(), weight);
        if (newWeight.getWeightInKg() != null) {
            log.info("setting weight {}", newWeight.getWeightInKg());
            weight.setWeightInKg(newWeight.getWeightInKg());
        }
        if (newWeight.getDate() != null) {
            log.info("setting date {}", newWeight.getDate());
            weight.setDate(newWeight.getDate());
        }
        log.info("Weight mapping complete result: {}", weight);
        return weight;
    }

    private void setDefaultDataIfNeeded(Weight weight) {
        if (weight.getDate() == null) {
            log.info("Adding default date");
            weight.setDate(LocalDate.now());
        }
    }
}
