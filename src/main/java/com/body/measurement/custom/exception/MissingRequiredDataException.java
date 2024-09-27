package com.body.measurement.custom.exception;

public class MissingRequiredDataException extends Exception {
    private static final String exceptionMess = "Required data is missing in object %s";

    public MissingRequiredDataException(String className) {
        super(String.format(exceptionMess, className));
    }
}
