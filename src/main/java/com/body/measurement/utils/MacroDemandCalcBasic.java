package com.body.measurement.utils;

public class MacroDemandCalcBasic implements MacroDemandCalc{

    @Override
    public double calcPPM(int age, double weightInKg, double heightInCm, String gender) {
        double ppm=(10*weightInKg)+(6.25*heightInCm)-(5*age);
        if(gender.equals("F")){
            ppm = ppm-161;
        }
        else{
            ppm = ppm + 5;
        }
        return ppm;
    }

    @Override
    public double calcCPM(double ppm, double pal) {
        return ppm*pal;
    }

    @Override
    public double protein(double weight) {
        return 2*weight;
    }

    @Override
    public double fat(double weight) {
        return 1.2*weight;
    }

    @Override
    public double carbs(double protein, double fat, double target_kcal) {
        return (target_kcal - (protein*4) - (fat*9))/4;
    }
}
