package com.body.measurement.utils;

public interface MacroDemandCalc {
    public double calcPPM(int age, double weightInKg, double heightInCm, String gender);
    public double calcCPM(double ppm, double pal);
    public double protein(double weight);
    public double fat(double weight);
    public double carbs(double protein, double fat, double target_kcal);
}
