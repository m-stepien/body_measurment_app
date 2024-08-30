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

}
