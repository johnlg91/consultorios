import URL from "../../axiosconfig";
import { AxiosPromise } from "axios";

export interface Expensa {
	id?: number;
	descripcion: string;
	fechaDeExpensa: string;
	cantidad: number | null;
	seRepite: string;
	fechaDePago: string;
	oculto?: boolean;
}

export interface ExpensaFormulario {
	id?: number;
	descripcion: string;
	fechaDeExpensa: Date;
	cantidad: Date;
	seRepite: string;
	fechaDePago: string;
	oculto?: boolean;
}

export const getExpensas = (): AxiosPromise<Expensa[]> => URL.get("expensas");

export const getExpensa = (id: number): AxiosPromise<Expensa[]> => URL.get(`expensas/${id}`);

export const crearExpensa = (datos: Expensa): AxiosPromise => URL.post("expensas", datos);

export const modificarExpensa = (datos: Expensa): AxiosPromise => URL.put("expensas", datos);

export const borrarExpensa = (id: number | undefined): AxiosPromise => URL.delete(`expensas/${id}`);