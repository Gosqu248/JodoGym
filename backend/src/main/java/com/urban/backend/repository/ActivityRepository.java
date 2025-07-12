package com.urban.backend.repository;

import com.urban.backend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND " +
            "DATE(a.startTime) = DATE(:date) AND a.endTime IS NOT NULL " +
            "ORDER BY a.startTime")
    List<Activity> findDailyActivities(@Param("userId") UUID userId, @Param("date") Instant date);

    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND " +
            "a.startTime BETWEEN :start AND :end AND a.endTime IS NOT NULL " +
            "ORDER BY a.startTime")
    List<Activity> findActivities(@Param("userId") UUID userId,
                                  @Param("start") Instant start,
                                  @Param("end") Instant end);

}
