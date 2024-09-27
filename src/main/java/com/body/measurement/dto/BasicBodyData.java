package com.body.measurement.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class BasicBodyData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double heightInCm;
    private Integer age;
    private String gander; //will be enum

    public Long getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicBodyData that = (BasicBodyData) o;
        return Objects.equals(id, that.id) && Objects.equals(heightInCm, that.heightInCm) && Objects.equals(age, that.age) && Objects.equals(gander, that.gander);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heightInCm, age, gander);
    }

    @Override
    public String toString() {
        return "BasicBodyData{" +
                "id=" + id +
                ", heightInCm=" + heightInCm +
                ", age=" + age +
                ", gander='" + gander + '\'' +
                '}';
    }
}

