package com.urban.backend.dto.response;

import com.urban.backend.model.Activity;

import java.time.Instant;
import java.util.UUID;

public record ActivityResponse(
        UUID id,
        Instant startTime,
        Instant endTime,
        Long durationMinutes
) {
    public static ActivityResponse fromActivity(Activity activity) {
        return new ActivityResponse(
                activity.getId(),
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getDurationMinutes()
        );
    }
}
