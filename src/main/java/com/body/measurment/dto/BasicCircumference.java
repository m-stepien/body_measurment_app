package com.body.measurment.dto;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "basic_circumference")
public class BasicCircumference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double abdominal;
    private Double chest;
    private Double waist;
    private Double hip;


    public BasicCircumference() {
    }

    public BasicCircumference(Double abdominal, Double chest, Double waist, Double hip) {
        this.abdominal = abdominal;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
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
}
