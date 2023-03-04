use CONSULTORIOS_SCHEMA_BD;

INSERT INTO PROFESIONALES
    (ID, DNI, NOMBRE, APELLIDO, SOBRENOMBRE, ESPECIALIDAD, FECHA_DE_SUBSCRIPCION, DIRECCION, TELEFONO_CELULAR, E_MAIL, NOTAS, OCULTO)
VALUES
    (1, 1313131313, 'nuevoMobre', 'nnnn', 'aasasas', 'esper', null, 'dir', '666666-121111', 'nuevo@mail.com', 'nota', 0),
    (2, 21111111, 'Cristina', 'Fernandez', 'la ladrona', 'estetica', '2022-08-19', 'Av, Siempre viva 134', '2222222222', 'ladrona@mail.com', 'Aca hay muchas notas', 0),
    (3, 31111111, 'Mariano', 'Algorta', 'Martito', 'depilacion', '2022-08-27', 'Av, Siempre viva 134', '3333333333', 'alma@mail.com', 'Aca hay muchas notas', 0),
    (4, 40133983, 'MANUEL', 'Lopez', 'nombre', 'psiquiatria', '2022-08-02', 'Av, Siempre viva 134', '1111111119', 'juanma@mail.com', 'Aca hay muchas notas', 0),
    (6, 77777777, 'nom', 'app', 'sobre', 'espe', null, 'dir dir', 'tele', 'nuevoMail@gmail.com', 'lindas notas', 1),
    (7, 76555999, 'cliente1', 'apellido1', 'sobrenombre1', 'Psiquiatra', null, 'Av siemore viva 13', '1111111111111', 'lindo@mail.com', 'Lindas notas', 0),
    (8, 57444888, 'pipe', 'herrero', 'herrerito', 'depilacion', null, 'Av siemore viva 13', '12839483984', 'lindo@mail.com', '', 0),
    (9, 23672637, 'nombre', 'ape', 'sobre', 'ninguna', '2023-02-28', 'dir', '1212221212', 'nuevo@mail.com', '', 0);