package com.body.measurement.dto.responses;

import com.body.measurement.dto.BodyDetails;

public class BodySaveResponse {
    private boolean success;
    private String message;
    private BodyDetails bodyDetails;

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

    public BodyDetails getBasicBodyData() {
        return bodyDetails;
    }

    public void setBasicBodyData(BodyDetails bodyDetails) {
        this.bodyDetails = bodyDetails;
    }
}
