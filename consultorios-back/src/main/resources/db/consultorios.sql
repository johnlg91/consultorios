
use CONSULTORIOS_SCHEMA_BD;

replace into CONSULTORIOS(ID, NUMERO_DE_CONSULTORIO, COSTO_POR_MODULO, TAMANIO_DEL_AREA, IMAGENES, EQUIPO, ESPECIALIDADES, OCULTO)
values
    (1, 1, 5000, 35, null, 'camilla', 'psiquiatria, estetica, psicopedagogia', 0),
    (2, 2, 6500, 45, null, 'camilla', 'psiquiatria', 0),
    (3, 3, 0, 40, null, 'Solo escritorio', 'Psiquiatria, pediatria', 0),
    (4, 4, 69, 40, null, 'Solo escritorio', 'varias', 0);
