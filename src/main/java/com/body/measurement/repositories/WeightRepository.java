package com.body.measurement.repositories;

import com.body.measurement.dto.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    Optional<Weight> findTopByOrderByDateDesc();
    @Query("SELECT w FROM Weight w WHERE date<=:startDate AND date>=:endDate")
    List<Weight> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate stopDate);
    Optional<Weight> findTopByOrderByDateAsc();
    Optional<Weight> findByDate(LocalDate date);
    @Query("SELECT w FROM Weight w WHERE date<:dt ORDER BY date DESC LIMIT 1")
    Optional<Weight> findOneBeforeGiven(@Param("dt") LocalDate date);
    @Query("SELECT w FROM Weight w WHERE date>:dt ORDER BY date LIMIT 1")
    Optional<Weight> findOneAfterGiven(@Param("dt") LocalDate date);

}
