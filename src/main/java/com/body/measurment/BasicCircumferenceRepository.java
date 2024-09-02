package com.body.measurment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BasicCircumferenceRepository extends JpaRepository<BasicCircumference, Long> {
    public List<BasicCircumference> findByMeasurmentDateBetweenOrderByMeasurmentDateDesc(LocalDate startData, LocalDate stopDate);
    public List<BasicCircumference> findByMeasurmentDateGreaterThanEqual(LocalDate measurmentDate);
}
