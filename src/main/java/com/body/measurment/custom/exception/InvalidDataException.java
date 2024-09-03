package com.body.measurment.custom.exception;

public class InvalidDataException extends Exception {
    public InvalidDataException(String className) {
        super("There is invalid data value in " + className);
    }
}
