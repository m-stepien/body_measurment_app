package com.body.measurment.repositories;

import com.body.measurment.dto.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<Weight, Long> {
}
