package com.urban.backend.controller;

import com.urban.backend.dto.response.ActivityResponse;
import com.urban.backend.dto.response.ActivityStatus;
import com.urban.backend.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping("/start/{userId}")
    public ResponseEntity<Void> startActivity(@PathVariable UUID userId) {
        activityService.createActivity(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/end/{activityId}")
    public ResponseEntity<ActivityResponse> endActivity(@PathVariable UUID activityId) {
        ActivityResponse response = activityService.endActivity(activityId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily/{userId}")
    public ResponseEntity<ActivityStatus> getDailyStats(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ActivityStatus stats = activityService.getDailyStatus(userId, date);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/weekly/{userId}")
    public ResponseEntity<ActivityStatus> getWeeklyStats(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        ActivityStatus stats = activityService.getWeeklyStatus(userId, startDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/monthly/{userId}")
    public ResponseEntity<ActivityStatus> getMonthlyStats(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        ActivityStatus stats = activityService.getMonthlyStatus(userId, startDate);
        return ResponseEntity.ok(stats);
    }
}
