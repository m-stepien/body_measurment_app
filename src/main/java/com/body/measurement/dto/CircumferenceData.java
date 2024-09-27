package com.body.measurement.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

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
    @Column(name = "measurement_date")
    private LocalDate measurementDate;

    public CircumferenceData() {
    }

    public BasicCircumference getBasicCircumference() {
        return basicCircumference;
    }

    public void setBasicCircumference(BasicCircumference basicCircumference) {
        this.basicCircumference = basicCircumference;
    }

    public LocalDate getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDate measurementDate) {
        this.measurementDate = measurementDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircumferenceData that = (CircumferenceData) o;
        return Objects.equals(id, that.id) && Objects.equals(basicCircumference, that.basicCircumference) && Objects.equals(additionalCircumference, that.additionalCircumference) && Objects.equals(measurementDate, that.measurementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basicCircumference, additionalCircumference, measurementDate);
    }

    @Override
    public String toString() {
        return "CircumferenceData{" +
                "id=" + id +
                ", basicCircumference=" + basicCircumference +
                ", additionalCircumference=" + additionalCircumference +
                ", measurementDate=" + measurementDate +
                '}';
    }
}
