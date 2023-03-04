package org.tmed.consultoriosback.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("TRANSACCIONES_DE_ALQUILERES")
public record TransaccionDeAlquiler(
        @Id long id,
        long idContratoDeAlquiler,
        java.sql.Date fechaDeTransaccion,
        String tipo,
        String metodoDePago,
        double cantidad,
        boolean oculto
) {
}
