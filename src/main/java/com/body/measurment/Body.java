package com.body.measurment;

import java.util.Optional;

public record Body(Double height, Double weight, Optional<Double> bodyFat){}
