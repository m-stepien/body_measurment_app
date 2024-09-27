package com.body.measurement.dto;

import jakarta.persistence.*;

import java.util.Objects;

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

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicCircumference that = (BasicCircumference) o;
        return Objects.equals(id, that.id) && Objects.equals(abdominal, that.abdominal) && Objects.equals(chest, that.chest) && Objects.equals(waist, that.waist) && Objects.equals(hip, that.hip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, abdominal, chest, waist, hip);
    }

    @Override
    public String toString() {
        return "BasicCircumference{" +
                "id=" + id +
                ", abdominal=" + abdominal +
                ", chest=" + chest +
                ", waist=" + waist +
                ", hip=" + hip +
                '}';
    }
}
