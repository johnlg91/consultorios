package org.tmed.consultoriosback.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("CONTRATOS_DE_ALQUILER")
public record ContratoDeAlquiler(
        @Id long id,
        long idConsultorio,
        long idProfesional,
        String tipoDeAlquiler,
        Date inicioDelContratoDeAlquiler,
        Date finDelContrato,
        long costoPorModulo,
        String notas,
        boolean oculto) {
}
