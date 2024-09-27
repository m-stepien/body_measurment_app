package com.body.measurement.repositories;

import com.body.measurement.dto.BasicBodyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicBodyDataRepository extends JpaRepository<BasicBodyData, Long> {
}
