export interface Vacancia {
	id?: number;
	idContratoDeAlquiler: number;
	diaDeLaSemana: DIADESEMANA;
	empiezaVacancia: string;
	terminaVacancia: string;
	oculto?: boolean;
}

export interface Coordenada {
	idAlquilerVacancia?: number;
	idContrato?: number;
	hora: number;
	dia: DIADESEMANA;
	numConsultorio: number;
}

export interface CoordenadaDeMatriz {
	idAlquilerVacancia?: number;
	diaDeLaSemana: DIADESEMANA;
	empiezaVacancia: string;
	terminaVacancia: string;
	numeroDeConsultorio: number;
	nombre: string;
	apellido: string;
	idContratoDeAlquiler: number;
}

export enum DIADESEMANA {
	LUNES = "LUNES",
	MARTES = "MARTES",
	MIERCOLES = "MIERCOLES",
	JUEVES = "JUEVES",
	VIERNES = "VIERNES",
	SABADO = "SABADO",
	DOMINGO = "DOMINGO"
}

export const diasDeSemana = [
	DIADESEMANA.LUNES,
	DIADESEMANA.MARTES,
	DIADESEMANA.MIERCOLES,
	DIADESEMANA.JUEVES,
	DIADESEMANA.VIERNES,
	DIADESEMANA.SABADO,
	DIADESEMANA.DOMINGO,
];

export const horas = [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22];

export interface VacanciaDeMatriz {
	idAlquilerVacancia?: number;
	ocupado: boolean;
	profesional?: string;
	idContratoDeAlquiler?: number;
}

export type Matriz = Map<DIADESEMANA, Map<number, Map<number, VacanciaDeMatriz>>>
