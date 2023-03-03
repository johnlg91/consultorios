create schema if not exists CONSULTORIOS_SCHEMA_BD;

use CONSULTORIOS_SCHEMA_BD;

create table if not exists CONSULTORIOS
(
    ID                    int auto_increment
        primary key,
    NUMERO_DE_CONSULTORIO int                  not null,
    COSTO_POR_MODULO      int                  not null,
    TAMANIO_DEL_AREA      int                  not null,
    IMAGENES              blob                 null,
    EQUIPO                varchar(150)         not null,
    ESPECIALIDADES        varchar(150)         not null,
    OCULTO                tinyint(1) default 0 not null
)
    comment 'Tabla de consultorios' engine = InnoDB;

create table if not exists EXPENSAS
(
    ID               int auto_increment
        primary key,
    DESCRIPCION      varchar(30)                                                      not null,
    FECHA_DE_EXPENSA date                                                             not null,
    CANTIDAD         decimal(12, 2)                                                   not null,
    SE_REPITE        enum ('NUNCA', 'MENSUALMENTE', 'BIMENSUALMENTE') default 'NUNCA' not null comment 'NUNCA: Gasto extraordinario
MENSUALMENTE: Gasto que se repite mensualmente
BIMENSUAL: Se repite cada dos meses',
    FECHA_DE_PAGO    date                                                             null,
    OCULTO           tinyint(1)                                       default 0       not null
)
    engine = InnoDB;

create table if not exists PROFESIONALES
(
    ID                    int auto_increment
        primary key,
    DNI                   int                  not null,
    NOMBRE                varchar(30)          not null,
    APELLIDO              varchar(30)          not null,
    SOBRENOMBRE           varchar(50)          null,
    ESPECIALIDAD          varchar(20)          null,
    FECHA_DE_SUBSCRIPCION date                 null,
    DIRECCION             varchar(50)          null,
    TELEFONO_CELULAR      varchar(30)          null,
    E_MAIL                varchar(30)          not null,
    NOTAS                 varchar(300)         null,
    OCULTO                tinyint(1) default 0 not null,
    constraint PROFESSIONALS_DNI_uindex
        unique (DNI)
)
    engine = InnoDB;

create table if not exists CONTRATOS_DE_ALQUILER
(
    ID                              int auto_increment
        primary key,
    ID_CONSULTORIO                  int                  not null,
    ID_PROFESIONAL                  int                  not null,
    TIPO_DE_ALQUILER                varchar(30)          not null,
    INICIO_DEL_CONTRATO_DE_ALQUILER date                 not null,
    FIN_DEL_CONTRATO                date                 not null,
    COSTO_TOTAL                     int                  not null,
    NOTAS                           varchar(300)         null,
    OCULTO                          tinyint(1) default 0 not null,
    constraint RENT_CONTRACT_OFFICES_ID_fk
        foreign key (ID_CONSULTORIO) references CONSULTORIOS (ID),
    constraint RENT_CONTRACT_PROFESSIONALS_ID_fk
        foreign key (ID_PROFESIONAL) references PROFESIONALES (ID)
)
    engine = InnoDB;

create table if not exists ALQUILERES_VACANCIA
(
    ID                      int auto_increment
        primary key,
    ID_CONTRATO_DE_ALQUILER int                                                                             not null,
    DIA_DE_LA_SEMANA        enum ('DOMINGO', 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO') not null,
    EMPIEZA_VACANCIA        time                                                                            not null,
    TERMINA_VACANCIA        time                                                                            not null,
    OCULTO                  tinyint(1) default 0                                                            not null,
    constraint RENT_ITEM_RENT_CONTRACT_ID_fk
        foreign key (ID_CONTRATO_DE_ALQUILER) references CONTRATOS_DE_ALQUILER (ID)
)
    engine = InnoDB;

create table if not exists TRANSACCIONES_DE_ALQUILERES
(
    ID                      int auto_increment
        primary key,
    ID_CONTRATO_DE_ALQUILER int                                                   not null,
    FECHA_DE_TRANSACCION    date                                                  null,
    TIPO                    enum ('CREDITO', 'DEBITO')                            null comment 'Tipo de transaccion:
DEBITO: Liquidacion Alquiler
CREDITO: Pago',
    METODO_DE_PAGO          enum ('EFECTIVO', 'TRANSFERENCIA') default 'EFECTIVO' null comment 'Medio de Pago:
CASH: Efectivo
WIRE: Transferencia Bancaria',
    CANTIDAD                decimal(12, 2)                                        not null,
    OCULTO                  tinyint(1)                         default 0          not null,
    constraint TRANSACCIONES_DE_ALQUILERES_CONTRATOS_DE_ALQUILER_null_fk
        foreign key (ID_CONTRATO_DE_ALQUILER) references CONTRATOS_DE_ALQUILER (ID)
)
    comment 'Transacciones por profesional. Debitos y Creditos' engine = InnoDB;

create table if not exists USUARIOS
(
    ID             int auto_increment
        primary key,
    DNI            int                  not null,
    NOMBRE_USUARIO varchar(64)          not null,
    EMAIL          varchar(30)          not null,
    CONTRASENNIA   varchar(88)          not null,
    ES_ADMIN       tinyint(1)           null,
    OCULTO         tinyint(1) default 0 not null
)
    comment 'Tablde usuarioa' engine = InnoDB;


