import { AxiosPromise } from "axios";
import URL from "../../axiosconfig";
import { deDateToStringYMD } from "../../comun";

export interface Contrato {
	id?: number;
	idConsultorio: number | null;
	idProfesional: number | null;
	tipoDeAlquiler: string;
	inicioDelContratoDeAlquiler: Date;
	finDelContrato: Date;
	costoTotal: number | null;
	notas: string;
	oculto?: boolean;
}

export interface ContratoConNombre {
	id?: number;
	numeroDeConsultorio: number | null;
	sobrenombre: string;
	tipoDeAlquiler: string;
	inicioDelContratoDeAlquiler: string;
	finDelContrato: string;
	costoTotal: number | null;
	notas: string;
	oculto?: boolean;
}

export const getContrato = (id: number | undefined): AxiosPromise<Contrato> => URL.get(`contratos/${id}`);

export const getContratos = (): AxiosPromise<Contrato[]> => URL.get("contratos");

export const getContratosSinPagar = (fecha: Date): AxiosPromise<Contrato[]> =>
	URL.get("contratos/apagar", { params: { fecha: deDateToStringYMD(fecha as Date) } });

export const getContratosConNombre = (): AxiosPromise<ContratoConNombre[]> => URL.get("contratos/nombres");

export const getContratosNormales = (): AxiosPromise<Contrato[]> => URL.get("contratos/normales");

export const getContratosExcepcionales = (): AxiosPromise<Contrato[]> => URL.get("contratos/excepcionales");

export const crearContrato = (datos: Contrato): AxiosPromise => URL.post("contratos", datos);

export const modificarContrato = (datos: Contrato): AxiosPromise => URL.put("contratos", datos);

export const borrarContrato = (id: number | undefined): AxiosPromise => URL.delete(`contratos/${id}`);