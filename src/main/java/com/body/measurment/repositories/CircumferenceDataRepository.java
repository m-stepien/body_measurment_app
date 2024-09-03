package com.body.measurment.repositories;

import com.body.measurment.dto.CircumferenceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CircumferenceDataRepository  extends JpaRepository<CircumferenceData, Long> {
    List<CircumferenceData> findByMeasurmentDateBetweenOrderByMeasurmentDateDesc(LocalDate startData,
                                                                                  LocalDate stopDate);
    List<CircumferenceData> findByMeasurmentDateGreaterThanEqual(LocalDate measurmentDate);
}
