package com.body.measurment.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class CircumferenceData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "basic_circumference_id")
    private BasicCircumference basicCircumference;
    @OneToOne
    @JoinColumn(name = "additional_circumference_id")
    private AdditionalCircumference additionalCircumference;
    @Column(name = "measurment_date")
    private LocalDate measurmentDate;

    public CircumferenceData(BasicCircumference basicCircumference, AdditionalCircumference additionalCircumference,
                             LocalDate measurmentDate) {
        this.basicCircumference = basicCircumference;
        this.additionalCircumference = additionalCircumference;
        this.measurmentDate = measurmentDate;
    }

    public CircumferenceData(BasicCircumference basicCircumference, AdditionalCircumference additionalCircumference) {
        this.basicCircumference = basicCircumference;
        this.additionalCircumference = additionalCircumference;

    }

    public CircumferenceData() {
    }

    public BasicCircumference getBasicCircumference() {
        return basicCircumference;
    }

    public void setBasicCircumference(BasicCircumference basicCircumference) {
        this.basicCircumference = basicCircumference;
    }

    public LocalDate getMeasurmentDate() {
        return measurmentDate;
    }

    public void setMeasurmentDate(LocalDate measurmentDate) {
        this.measurmentDate = measurmentDate;
    }

    public AdditionalCircumference getAdditionalCircumference() {
        return additionalCircumference;
    }

    public void setAdditionalCircumference(AdditionalCircumference additionalCircumference) {
        this.additionalCircumference = additionalCircumference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
