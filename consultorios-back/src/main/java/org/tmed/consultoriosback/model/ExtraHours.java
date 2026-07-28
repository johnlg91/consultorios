package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Table("extra_hours")
public record ExtraHours(
        @Id
        long id,
        long tenantActivityId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        BigDecimal rateCharged,
        boolean active
) {
}
