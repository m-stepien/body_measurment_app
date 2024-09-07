package com.body.measurment.repositories;

import com.body.measurment.dto.BasicBodyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicBodyDataRepository extends JpaRepository<BasicBodyData, Long> {
}
