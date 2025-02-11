package com.body.measurement.services;

import com.body.measurement.dto.BodyData;

public interface BodyDataProvider {
    public BodyData getBodyDataByUserId(Long userId);
}
