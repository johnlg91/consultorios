package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Table("vacancies")
public record Vacancy(
        @Id
        long id,
        long tenantActivityId,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
}
