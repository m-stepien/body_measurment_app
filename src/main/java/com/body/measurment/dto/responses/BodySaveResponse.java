package com.body.measurment.dto.responses;

import com.body.measurment.dto.BasicBodyData;
import com.body.measurment.dto.CircumferenceData;

public class BodySaveResponse {
    private boolean success;
    private String message;
    private BasicBodyData circumferenceData;

    public BodySaveResponse() {
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

    public BasicBodyData getCircumferenceData() {
        return circumferenceData;
    }

    public void setCircumferenceData(BasicBodyData circumferenceData) {
        this.circumferenceData = circumferenceData;
    }
}
