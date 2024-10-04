package com.body.measurement.repositories;

import com.body.measurement.dto.BasicCircumference;
import com.body.measurement.dto.CircumferenceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CircumferenceDataRepository  extends JpaRepository<CircumferenceData, Long> {
    List<CircumferenceData> findByMeasurementDateBetweenOrderByMeasurementDate(LocalDate startData,
                                                                               LocalDate stopDate);
    List<CircumferenceData> findByMeasurementDateGreaterThanEqual(LocalDate measurementDate);
    Optional<CircumferenceData> findByMeasurementDate(LocalDate measurementDate);
    @Query("SELECT cd.basicCircumference FROM CircumferenceData cd WHERE measurementDate=:md ORDER BY id LIMIT 1")
    Optional<BasicCircumference> findBasicCircumferenceByDate(@Param("md") LocalDate measurementDate);
}
