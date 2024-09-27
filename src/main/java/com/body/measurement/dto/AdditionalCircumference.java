package com.body.measurement.dto;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "additional_circumference")
public class AdditionalCircumference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double thighL;
    private Double thighR;
    private Double armL;
    private Double armR;
    private Double neck;
    private Double forarmL;
    private Double forarmR;
    private Double calfL;
    private Double calfR;


    public AdditionalCircumference(Double thighL, Double thighR, Double armL, Double armR, Double neck, Double forarmL, Double forarmR, Double calfL, Double calfR) {
        this.thighL = thighL;
        this.thighR = thighR;
        this.armL = armL;
        this.armR = armR;
        this.neck = neck;
        this.forarmL = forarmL;
        this.forarmR = forarmR;
        this.calfL = calfL;
        this.calfR = calfR;
    }

    public AdditionalCircumference() {
    }

    public Long getId() {
        return id;
    }

    public Double getThighL() {
        return thighL;
    }

    public void setThighL(Double thighL) {
        this.thighL = thighL;
    }

    public Double getThighR() {
        return thighR;
    }

    public void setThighR(Double thighR) {
        this.thighR = thighR;
    }

    public Double getArmL() {
        return armL;
    }

    public void setArmL(Double armL) {
        this.armL = armL;
    }

    public Double getArmR() {
        return armR;
    }

    public void setArmR(Double armR) {
        this.armR = armR;
    }

    public Double getNeck() {
        return neck;
    }

    public void setNeck(Double neck) {
        this.neck = neck;
    }

    public Double getForarmL() {
        return forarmL;
    }

    public void setForarmL(Double forarmL) {
        this.forarmL = forarmL;
    }

    public Double getForarmR() {
        return forarmR;
    }

    public void setForarmR(Double forarmR) {
        this.forarmR = forarmR;
    }

    public Double getCalfL() {
        return calfL;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCalfL(Double calfL) {
        this.calfL = calfL;
    }

    public Double getCalfR() {
        return calfR;
    }

    public void setCalfR(Double calfR) {
        this.calfR = calfR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalCircumference that = (AdditionalCircumference) o;
        return Objects.equals(id, that.id) && Objects.equals(thighL, that.thighL) && Objects.equals(thighR, that.thighR) && Objects.equals(armL, that.armL) && Objects.equals(armR, that.armR) && Objects.equals(neck, that.neck) && Objects.equals(forarmL, that.forarmL) && Objects.equals(forarmR, that.forarmR) && Objects.equals(calfL, that.calfL) && Objects.equals(calfR, that.calfR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thighL, thighR, armL, armR, neck, forarmL, forarmR, calfL, calfR);
    }

    @Override
    public String toString() {
        return "AdditionalCircumference{" +
                "id=" + id +
                ", thighL=" + thighL +
                ", thighR=" + thighR +
                ", armL=" + armL +
                ", armR=" + armR +
                ", neck=" + neck +
                ", forarmL=" + forarmL +
                ", forarmR=" + forarmR +
                ", calfL=" + calfL +
                ", calfR=" + calfR +
                '}';
    }
}
