package com.body.measurement.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "weight")
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    @Column(name = "weight_kg")
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
        return id.equals(weight.id) && Objects.equals(date, weight.date) && Objects.equals(weightInKg, weight.weightInKg);
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
