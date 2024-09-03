package com.body.measurment;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "basic_circumference")
public class BasicCircumference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "measurment_date")
    private LocalDate measurmentDate;
    private Double abdominal;
    private Double chest;
    private Double waist;
    private Double hip;
    @OneToOne
    @JoinColumn(name = "additional_measurement_id")
    private AdditionalCircumference additionalCircumference;

    public BasicCircumference() {
    }

    public BasicCircumference(LocalDate measurmentDate, Double abdominal, Double chest, Double waist, Double hip, AdditionalCircumference additionalCircumference) {
        this.measurmentDate = measurmentDate;
        this.abdominal = abdominal;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.additionalCircumference = additionalCircumference;
    }

    public BasicCircumference(LocalDate measurmentDate, Double abdominal, Double chest, Double waist, Double hip) {
        this.measurmentDate = measurmentDate;
        this.abdominal = abdominal;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
    }

    public BasicCircumference(Double abdominal, Double chest, Double waist, Double hip) {
        this.measurmentDate = LocalDate.now();
        this.abdominal = abdominal;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
    }

    public LocalDate getMeasurmentDate() {
        return measurmentDate;
    }

    public void setMeasurmentDate(LocalDate measurmentDate) {
        this.measurmentDate = measurmentDate;
    }

    public Double getAbdominal() {
        return abdominal;
    }

    public void setAbdominal(Double abdominal) {
        this.abdominal = abdominal;
    }

    public Double getChest() {
        return chest;
    }

    public void setChest(Double chest) {
        this.chest = chest;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getHip() {
        return hip;
    }

    public void setHip(Double hip) {
        this.hip = hip;
    }

    public Long getId() {
        return id;
    }

    public AdditionalCircumference getAdditionalCircumference() {
        return additionalCircumference;
    }

    public void setAdditionalCircumference(AdditionalCircumference additionalCircumference) {
        this.additionalCircumference = additionalCircumference;
    }
}
