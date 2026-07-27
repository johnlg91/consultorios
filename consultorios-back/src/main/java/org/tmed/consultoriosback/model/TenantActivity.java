package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("tenant_activities")
public record TenantActivity(
        @Id
        long id,
        long tenantId,
        long officeId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal monthlyRate,
        String notes
) {
}
