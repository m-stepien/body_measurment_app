package com.body.measurment.dto.responses;

import com.body.measurment.dto.CircumferenceData;

public class CircumferenceDataSaveResponse {
    private boolean success;
    private String message;
    private CircumferenceData circumferenceData;

    public CircumferenceDataSaveResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CircumferenceData getCircumferenceData() {
        return circumferenceData;
    }

    public void setCircumferenceData(CircumferenceData circumferenceData) {
        this.circumferenceData = circumferenceData;
    }
}
