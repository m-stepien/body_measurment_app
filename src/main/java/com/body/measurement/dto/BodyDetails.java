package com.body.measurement.dto;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "body_details")
public class BodyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "height_in_cm")
    private Double heightInCm;
    private Integer age;
    private String gender; //will be enum

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyDetails that = (BodyDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(heightInCm, that.heightInCm) && Objects.equals(age, that.age) && Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heightInCm, age, gender);
    }

    @Override
    public String toString() {
        return "BasicBodyData{" +
                "id=" + id +
                ", heightInCm=" + heightInCm +
                ", age=" + age +
                ", gander='" + gender + '\'' +
                '}';
    }
}

