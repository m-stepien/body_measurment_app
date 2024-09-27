package com.body.measurement.repositories;

import com.body.measurement.dto.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<Weight, Long> {
}
