import { AxiosPromise } from "axios";
import URL from "../../axiosconfig";
import { CoordenadaDeMatriz, Vacancia } from "./componentes/Variables";
import { ContratoConNombre } from "../contratos/ContratosAPI";
import { deDateToStringYMD } from "../../comun";

export const getVacancia = (id: number): AxiosPromise<Vacancia[]> => URL.get(`vacancias/${id}`);


export const getVacancias = (inicio: Date, fin: Date): AxiosPromise<CoordenadaDeMatriz[]> =>
	URL.get("vacancias", { params: { inicio: deDateToStringYMD(inicio), fin: deDateToStringYMD(fin) } });

export const getContratosPorConsultorio = (fecha: Date, numConsultorio: number): AxiosPromise<ContratoConNombre[]> =>
	URL.get("contratos/porconsultorio", {
		params: {
			fecha: deDateToStringYMD(fecha),
			numConsultorio: numConsultorio,
		},
	});

export const crearVacancias = (datos: Vacancia): AxiosPromise => URL.post("vacancias", datos);

export const modificarVacancias = (datos: Vacancia): AxiosPromise => URL.put("vacancias", datos);

export const borrarVacancias = (id: number): AxiosPromise => URL.delete(`vacancias/${id}`);