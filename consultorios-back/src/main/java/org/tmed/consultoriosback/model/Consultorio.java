package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("CONSULTORIOS")
public record Consultorio(
        @Id
        long id,
        long numeroDeConsultorio,
        long costoPorModulo,
        long tamanioDelArea,
        String equipo,
        String especialidades,
        boolean oculto
) {
}
