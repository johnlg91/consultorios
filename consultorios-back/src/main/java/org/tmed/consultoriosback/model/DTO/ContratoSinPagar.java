package org.tmed.consultoriosback.model.DTO;

import org.springframework.data.annotation.Id;

public record ContratoSinPagar(
        @Id long id,
        long idConsultorio,
        long idProfesional,
        long costoPorModulo,
        long valorContrato,
        long montoRestante,
        java.sql.Date inicioDelContratoDeAlquiler,
        java.sql.Date finDelContrato
) {
}
