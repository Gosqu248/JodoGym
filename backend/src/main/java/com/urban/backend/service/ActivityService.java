package com.urban.backend.service;

import com.urban.backend.dto.response.ActivityResponse;
import com.urban.backend.dto.response.ActivityStatus;
import com.urban.backend.model.Activity;
import com.urban.backend.model.User;
import com.urban.backend.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserService userService;

    public void createActivity(UUID userId)  {
        User user = userService.findById(userId);

        Activity activity = Activity.builder()
                .user(user)
                .build();

        activityRepository.save(activity);
    }

    public ActivityResponse endActivity(UUID activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + activityId));

        Instant now = Instant.now();
        long total = Duration.between(activity.getStartTime(), now).toMinutes();

        activity.setEndTime(now);
        activity.setDurationMinutes(total);
        activityRepository.save(activity);

        return ActivityResponse.fromActivity(activity);
    }

    public ActivityStatus getDailyStatus(UUID userId, LocalDate date) {
        Instant dateInstant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<Activity> activities = activityRepository.findDailyActivities(userId, dateInstant);

        return toActivityStatus(activities);
    }

    public ActivityStatus getWeeklyStatus(UUID userId, LocalDate startOfWeek) {
        Instant start = startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = startOfWeek.plusDays(6)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        List<Activity> activities = activityRepository.findActivities(userId, start, end);

        return toActivityStatus(activities);
    }

    public ActivityStatus getMonthlyStatus(UUID userId, LocalDate startOfWeek) {
        Instant start = startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = startOfWeek.plusMonths(1).plusDays(6)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        List<Activity> activities = activityRepository.findActivities(userId, start, end);

        return toActivityStatus(activities);
    }

    private ActivityStatus toActivityStatus(List<Activity> activities) {
        long totalMinutes = activities.stream()
                .mapToLong(Activity::getDurationMinutes)
                .sum();
        return new ActivityStatus(
                totalMinutes,
                activities.size(),
                activities.stream()
                        .map(ActivityResponse::fromActivity)
                        .toList()
        );
    }
}
