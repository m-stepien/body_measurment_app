package com.body.measurment.dto.responses;

import com.body.measurment.dto.BasicBodyData;

public class BodySaveResponse {
    private boolean success;
    private String message;
    private BasicBodyData basicBodyData;

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

    public BasicBodyData getBasicBodyData() {
        return basicBodyData;
    }

    public void setBasicBodyData(BasicBodyData basicBodyData) {
        this.basicBodyData = basicBodyData;
    }
}
