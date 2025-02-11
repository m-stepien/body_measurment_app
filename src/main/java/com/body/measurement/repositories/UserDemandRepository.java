package com.body.measurement.repositories;

import com.body.measurement.dto.UserDemand;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDemandRepository  extends JpaRepository<UserDemand, Long> {

}
