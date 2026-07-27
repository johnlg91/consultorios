package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("offices")
public record Office(
        @Id
        long id,
        long number,
        BigDecimal monthlyModulePrice,
        String equipment,
        boolean active
) {
}
