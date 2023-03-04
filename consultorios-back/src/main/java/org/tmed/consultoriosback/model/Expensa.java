package org.tmed.consultoriosback.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("EXPENSAS")
public record Expensa(
        @Id long id,
        String descripcion,
        Date fechaDeExpensa,
        double cantidad,
        String seRepite,
        Date fechaDePago,
        boolean oculto) {
}
