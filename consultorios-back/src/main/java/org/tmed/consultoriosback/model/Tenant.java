package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("tenants")
public record Tenant(
        @Id
        long id,
        long nationalId,
        String firstName,
        String lastName,
        String nickname,
        String specialty,
        LocalDate subscriptionDate,
        String address,
        String mobilePhone,
        String email,
        String notes,
        boolean active
) {
}
