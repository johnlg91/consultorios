import URL from "../../axiosconfig";
import { deDateToStringYMD } from "../../comun";
import { AxiosPromise } from "axios";


export interface ReporteVacancias {
	idConsultorio: number;
	numConsultorio: number;
	hTotales: number;
	hOcupadas: number;
}

export const getReporteIngresos = (fecha1: Date, fecha2: Date): AxiosPromise<number[]> => URL.get("reportes/ingresos", {
	params: {
		fecha1: deDateToStringYMD(fecha1),
		fecha2: deDateToStringYMD(fecha2),
	},
});

export const getReporteEgresos = (fecha1: Date, fecha2: Date): AxiosPromise<number[]> => URL.get("reportes/egresos", {
	params: {
		fecha1: deDateToStringYMD(fecha1),
		fecha2: deDateToStringYMD(fecha2),
	},
});

export const getReporteVacancias = (fecha1: Date, fecha2: Date): AxiosPromise<ReporteVacancias[]> => URL.get("reportes/vacancias", {
	params: {
		// fecha1: deDateToStringYMD(fecha1),
		// fecha2: deDateToStringYMD(fecha2),
		fecha1: 2022 - 1 - 25,
		fecha2: 2023 - 12 - 25,
	},
});

// export const getReporteVacancias = (fecha1: Date, fecha2: Date): AxiosPromise<number> => URL.get("vacancias/reportes", {
// 	params: {
// 		fecha1: deDateToStringYMD(fecha1),
// 		fecha2: deDateToStringYMD(fecha2),
// 	},
// });
