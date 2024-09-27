package com.body.measurement.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private Double weightInKg;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(Double weightInKg) {
        this.weightInKg = weightInKg;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return id == weight.id && Objects.equals(date, weight.date) && Objects.equals(weightInKg, weight.weightInKg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, weightInKg);
    }

    @Override
    public String toString() {
        return "Weight{" +
                "id=" + id +
                ", date=" + date +
                ", weightInKg=" + weightInKg +
                '}';
    }
}
