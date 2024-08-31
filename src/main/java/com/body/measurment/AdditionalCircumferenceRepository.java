package com.body.measurment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalCircumferenceRepository  extends JpaRepository<AdditionalCircumference   , Long> {
    public void updateAdditionalCircumference(AdditionalCircumference additionalCircumference);

}
