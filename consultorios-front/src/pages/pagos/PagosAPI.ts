import URL from "../../axiosconfig";
import { AxiosPromise } from "axios";

export interface Pago {
	id?: number;
	idContratoDeAlquiler: number;
	fechaDeTransaccion: Date | string | null;
	tipo: string;
	metodoDePago: string;
	cantidad: number;
	oculto?: boolean;
}

export const getPagos = (): AxiosPromise<Pago[]> => URL.get("pagos");

export const getPago = (id: number): AxiosPromise<Pago[]> => URL.get(`pagos/${id}`);

export const crearPago = (datos: Pago): AxiosPromise => URL.post("pagos", datos);

export const agregarPagos = (datos: Pago[]): AxiosPromise => URL.post("pagos/varios", datos);

export const modificarPago = (datos: Pago): AxiosPromise => URL.put("pagos", datos);

export const borrarPago = (id: number | undefined): AxiosPromise => URL.delete(`pagos/${id}`);