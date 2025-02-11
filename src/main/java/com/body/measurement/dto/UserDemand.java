package com.body.measurement.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class UserDemand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double ppm;
    private double cpm;
    private double protein;
    private double fat;
    private double carb;

    public UserDemand() {
    }

    public UserDemand(Long id, double ppm, double cpm, double protein, double fat, double carb) {
        this.id = id;
        this.ppm = ppm;
        this.cpm = cpm;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPpm() {
        return ppm;
    }

    public void setPpm(double ppm) {
        this.ppm = ppm;
    }

    public double getCpm() {
        return cpm;
    }

    public void setCpm(double cpm) {
        this.cpm = cpm;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDemand that = (UserDemand) o;
        return Double.compare(ppm, that.ppm) == 0 && Double.compare(cpm, that.cpm) == 0 && Double.compare(protein, that.protein) == 0 && Double.compare(fat, that.fat) == 0 && Double.compare(carb, that.carb) == 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ppm, cpm, protein, fat, carb);
    }
}
