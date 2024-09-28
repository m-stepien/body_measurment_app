package com.body.measurement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BodyMeasurementApp {
    //TODO rebuild bazy danych na dockerze po zmianie nazwy w parametrach!!!
    //TODO 6. dodanie śledzenia wagi oraz pozostałych parametrów ciała
    //TODO 5. podstawowy widok frontend bez wykresów
    //TODO 4. refaktoring całość
    //TODO 8 wykres wagi
    //TODO 7. podział na użytkowników

    public static void main(String[] args) {
        SpringApplication.run(BodyMeasurementApp.class, args);
    }
}