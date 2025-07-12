package com.urban.backend.dto.response;

import java.util.List;

public record ActivityStatus(
        Long totalMinutes,
        Integer sessionsCount,
        List<ActivityResponse> activities
) {
}
