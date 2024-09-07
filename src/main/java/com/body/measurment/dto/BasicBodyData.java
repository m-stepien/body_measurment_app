package com.body.measurment.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BasicBodyData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Double heightInCm;
    private Integer age;
    private String gander; //will be enum

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(Double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }
}

