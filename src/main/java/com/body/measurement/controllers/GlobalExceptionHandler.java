package com.body.measurement.controllers;

import com.body.measurement.custom.exception.DatabaseException;
import com.body.measurement.custom.exception.InvalidDataException;
import com.body.measurement.custom.exception.MissingRequiredDataException;
import com.body.measurement.custom.exception.NoSuchObjectInDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> invalidData(InvalidDataException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid data");
        errorResponse.put("details", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MissingRequiredDataException.class)
    public ResponseEntity<Map<String, String>> missingData(MissingRequiredDataException exception){
     Map<String, String> errorResponse = new HashMap<>();
     errorResponse.put("error", "Missing data");
     errorResponse.put("details", exception.getMessage());
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoSuchObjectInDatabaseException.class)
    public ResponseEntity<Map<String, String>> objectNotInDatabase(NoSuchObjectInDatabaseException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Object not in database");
        errorResponse.put("details", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, String>> databaseException(DatabaseException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Database exception");
        errorResponse.put("details", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
