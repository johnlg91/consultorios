import { TableCell, TableRow, Tooltip, Typography } from "@mui/material";
import React, { FC } from "react";
import { Coordenada, DIADESEMANA, diasDeSemana, horas, Matriz, VacanciaDeMatriz } from "./Variables";
import { Consultorio } from "../../consultorios/ConsultoriosAPI";

interface Props {
	consultorios?: Consultorio[];
	matriz?: Matriz;
	manejarAbrirFormulario: (hora: number, editar: boolean, dia: DIADESEMANA, numeroConsultorio?: number) => void;
	setAnclajeDeMenu: (currentTarget: EventTarget & HTMLTableCellElement) => void;
	setCoordenadaParaEditar: (c: Coordenada) => void;
}

const Filas: FC<Props> = ({
	consultorios,
	matriz,
	setAnclajeDeMenu,
	manejarAbrirFormulario,
	setCoordenadaParaEditar,
}) => {

	const obtenerTextoTooltip = (profesional?: string, idContrato?: number) => {
		if (profesional === undefined || idContrato === undefined) return "desocupado";
		return (<div>{profesional}<br />Contrato numero: {idContrato}</div>);
	};

	const manejarClick = (event: React.MouseEvent<HTMLTableCellElement>, hora: number, dia: DIADESEMANA, vacanciaEnCoordenada?: VacanciaDeMatriz, numConsultorio?: number) => {
		if (vacanciaEnCoordenada?.ocupado) {
			setCoordenadaParaEditar({
				idAlquilerVacancia: vacanciaEnCoordenada.idAlquilerVacancia,
				idContrato: vacanciaEnCoordenada.idContratoDeAlquiler,
				hora: hora,
				dia: dia,
				numConsultorio: numConsultorio || -1,
			});
			setAnclajeDeMenu(event.currentTarget);
		} else {
			manejarAbrirFormulario(hora, false, dia, numConsultorio);
		}
	};

	return (
		<>
			{
				horas.map((hora) =>
					<TableRow key={hora}>
						<TableCell className={"table-cell"}>{hora}:00</TableCell>
						{diasDeSemana.map((dia) =>
							consultorios?.map((consultorio) => {
								const vacanciaEnCoordenada = matriz?.get(dia)?.get(hora)?.get(consultorio?.numeroDeConsultorio as number);
								return (
									<Tooltip
										disableInteractive={!vacanciaEnCoordenada?.ocupado}
										title={obtenerTextoTooltip(vacanciaEnCoordenada?.profesional, vacanciaEnCoordenada?.idContratoDeAlquiler)}
										key={consultorio.id}
									>
										<TableCell
											colSpan={1}
											className={vacanciaEnCoordenada?.ocupado ? "ocupado" : "desocupado"}
											onClick={(event) =>
												manejarClick(event, hora, dia, vacanciaEnCoordenada, consultorio.numeroDeConsultorio)}
										>
											<Typography
												color={vacanciaEnCoordenada?.ocupado ? "red" : "white"}>
												{vacanciaEnCoordenada?.ocupado ? "x" : "o"}
											</Typography>
										</TableCell>
									</Tooltip>
								);
							},
							),
						)
						}
					</TableRow>,
				)}
		</>);
};

export default Filas;