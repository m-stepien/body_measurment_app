package com.body.measurment;

import com.body.measurment.dto.BasicCircumference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BasicCircumferenceRepository extends JpaRepository<BasicCircumference, Long> {
}
