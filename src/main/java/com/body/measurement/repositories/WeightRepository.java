package com.body.measurement.repositories;

import com.body.measurement.dto.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    Optional<Weight> findTopByOrderByDateDesc();
    List<Weight> findByDateBetweenOrderByDateDesc(LocalDate startData, LocalDate stopDate);
    Optional<Weight> findTopByOrderByDateAsc();
    Optional<Weight> findByDate(LocalDate date);

}
