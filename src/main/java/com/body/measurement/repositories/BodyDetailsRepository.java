package com.body.measurement.repositories;

import com.body.measurement.dto.BodyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyDetailsRepository extends JpaRepository<BodyDetails, Long> {
}
