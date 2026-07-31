package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("expenses")
public record Expense(
        @Id
        long id,
        String description,
        LocalDate expenseDate,
        BigDecimal amount,
        ExpenseRecurrence recurrence,
        LocalDate paymentDate,
        boolean active
) {
}
