package com.body.measurement.repositories;

import com.body.measurement.dto.CircumferenceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CircumferenceDataRepository  extends JpaRepository<CircumferenceData, Long> {
    List<CircumferenceData> findByMeasurementDateBetweenOrderByMeasurementDate(LocalDate startData,
                                                                               LocalDate stopDate);
    List<CircumferenceData> findByMeasurementDateGreaterThanEqual(LocalDate measurementDate);
    Optional<CircumferenceData> findByMeasurementDate(LocalDate measurementDate);
}
