import URL from "../../axiosconfig";
import { AxiosPromise } from "axios";

export interface Profesional {
	id?: number;
	dni: number | null;
	nombre: string;
	apellido: string;
	sobrenombre: string;
	especialidad: string;
	fechaDeSubscripcion?: string;
	direccion: string;
	telefonoCelular: string;
	eMail: string;
	notas: string;
	oculto?: boolean;
}

export const getProfesionales = (): AxiosPromise<Profesional[]> => URL.get("profesionales");

export const getProfesional = (id: number): AxiosPromise<Profesional> => URL.get(`profesionales/${id}`);

export const crearProfesional = (datos: Profesional): AxiosPromise => URL.post("profesionales", datos);

export const modificarProfesional = (datos: Profesional): AxiosPromise => URL.put("profesionales", datos);

export const borrarProfesional = (id: number | undefined): AxiosPromise => URL.delete(`profesionales/${id}`);