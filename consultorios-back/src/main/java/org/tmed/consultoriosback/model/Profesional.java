package org.tmed.consultoriosback.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("PROFESIONALES")
public record Profesional(
        @Id
        long id,
        long dni,
        String nombre,
        String apellido,
        String sobrenombre,
        String especialidad,
        java.sql.Date fechaDeSubscripcion,
        String direccion,
        String telefonoCelular,
        String eMail,
        String notas,
        boolean oculto
) {
}
