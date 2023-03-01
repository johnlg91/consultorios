import URL from "../../axiosconfig";
import { AxiosPromise } from "axios";

export interface Consultorio {
	id?: number;
	numeroDeConsultorio: number;
	imagen?: Blob;
	costoPorModulo: number;
	tamanioDelArea: number;
	equipo: string;
	especialidades: string;
	oculto?: boolean;
}

export const getConsultorios = (): AxiosPromise<Consultorio[]> => URL.get("consultorios");

export const getConsultorio = (id: number): AxiosPromise<Consultorio[]> => URL.get(`consultorios/${id}`);

export const crearConsultorio = (datos: Consultorio): AxiosPromise => URL.post("consultorios", datos);

export const modificarConsultorio = (datos: Consultorio): AxiosPromise => URL.put("consultorios", datos);

export const borrarConsultorio = (id: number | undefined): AxiosPromise => URL.delete(`consultorios/${id}`);