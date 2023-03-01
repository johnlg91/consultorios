import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { useEffect, useState } from "react";
import { getReporteEgresos, getReporteIngresos, getReporteVacancias, ReporteVacancias } from "./ReportesAPI";
import "../../comun/estilo/ModuloEstandar.scss";
import { ArrowBack, ArrowForward } from "@mui/icons-material";


const getMesAnterior = (date: Date): Date => {
	const h: Date = new Date(date);
	h.setMonth(h.getMonth() - 1);
	return h;
};

const getMesProximo = (date: Date): Date => {
	const h: Date = new Date(date);
	h.setMonth(h.getMonth() + 1);
	return h;
};
;
const getDiferencia = (values: number[]) => {
	if (values[1] === 0) return 0;
	return (((values[0] / values[1]) * 100).toFixed(2));
};

const Reportes = () => {
	const hoy = new Date();
	const [mesActual, setMesActual] = useState<Date>(hoy);
	const [mesAnterior, setMesAnterior] = useState<Date>(getMesAnterior(hoy));
	const [ingresos, setIngresos] = useState<number[]>([]);
	const [egresos, setEgresos] = useState<number[]>([]);
	const [vacancias, setVacancias] = useState<ReporteVacancias[]>();

	useEffect(() => {
		getReporteIngresos(mesAnterior, mesActual)
			.then(({ data }) => setIngresos(data));
		getReporteEgresos(mesAnterior, mesActual)
			.then(({ data }) => setEgresos(data));
		getReporteVacancias(mesAnterior, mesActual)
			.then(({ data }) => setVacancias(data));
	}, [mesAnterior, mesActual]);

	console.log(vacancias);

	const restarMes = () => {
		setMesAnterior(getMesAnterior(mesAnterior));
		setMesActual(getMesAnterior(mesActual));
	};

	const sumarMes = () => {
		setMesAnterior(getMesProximo(mesAnterior));
		setMesActual(getMesProximo(mesActual));
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}
									   colSpan={2}>{mesAnterior.getMonth() + 1}/{mesAnterior.getFullYear()}</TableCell>
							<TableCell className={"table-cell-titulo"}
									   colSpan={2}>{mesActual.getMonth() + 1}/{mesActual.getFullYear()}</TableCell>
						</TableRow>
						<TableRow>
							<TableCell className={"table-cell"} align={"center"} colSpan={2}>INGRESOS</TableCell>
							<TableCell className={"table-cell"} align={"center"} colSpan={2}>EGRESOS</TableCell>
						</TableRow>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>Ingresos mes anterios</TableCell>
							<TableCell className={"table-cell-titulo"}>Ingresos mes actual</TableCell>
							<TableCell className={"table-cell-titulo"}>Egresos Mes anterios</TableCell>
							<TableCell className={"table-cell-titulo"}>Egresos mes actual</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						<TableRow>
							{ingresos.map((i, index) =>
								<TableCell key={index} className={"table-cell"}>{i}</TableCell>,
							)}
							{egresos.map((i, index) =>
								<TableCell key={index} className={"table-cell"}>{i}</TableCell>,
							)}
						</TableRow>
						<TableRow>
							<TableCell className={"table-cell"} align={"center"} colSpan={2}>Diferencia
								del: {getDiferencia(ingresos)}%</TableCell>
							<TableCell className={"table-cell"} align={"center"} colSpan={2}>Diferencia
								del: {getDiferencia(egresos)}%</TableCell>
						</TableRow>
						{/*<TableRow>*/}
						{/*	<TableCell className={"table-cell"}>*/}
						{/*		*/}
						{/*	</TableCell>*/}
						{/*</TableRow>*/}
					</TableBody>
				</Table>
			</TableContainer>
			<Fab className={"boton-suma-semana"} variant={"extended"} size={"large"}
				 onClick={sumarMes}
			>
				Siguiente Mes
				<ArrowForward />
			</Fab>
			<Fab className={"boton-resta-semana"} variant={"extended"} size={"large"}
				 onClick={restarMes}
			>
				<ArrowBack />
				Anterior Mes
			</Fab>
		</div>
	);
};
export default Reportes;