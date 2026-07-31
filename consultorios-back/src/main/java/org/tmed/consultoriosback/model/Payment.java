package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("payments")
public record Payment(
        @Id
        long id,
        long tenantActivityId,
        LocalDate transactionDate,
        PaymentType type,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        byte[] evidenceImage,
        boolean active
) {
}
