package com.body.measurment;

import jakarta.persistence.*;

@Entity
@Table(name = "additional_circumference")
public class AdditionalCircumference {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    public AdditionalCircumference(Double thighL, Double thighR, Double armL,
                                   Double armR, Double neck, Double forarmL,
                                   Double forarmR, Double calfL, Double calfR) {
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

    public void setCalfL(Double calfL) {
        this.calfL = calfL;
    }

    public Double getCalfR() {
        return calfR;
    }

    public void setCalfR(Double calfR) {
        this.calfR = calfR;
    }
}
