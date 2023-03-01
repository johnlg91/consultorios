import "./Vacancias.scss";
import { useEffect, useState } from "react";
import { crearVacancias, getVacancias, modificarVacancias } from "./VacanciasAPI";
import {
	Fab,
	ListItemIcon,
	ListItemText,
	Menu,
	MenuItem,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TableRow,
} from "@mui/material";
import Filas from "./componentes/Filas";
import { Consultorio, getConsultorios } from "../consultorios/ConsultoriosAPI";
import { Coordenada, DIADESEMANA, diasDeSemana, Matriz, Vacancia, VacanciaDeMatriz } from "./componentes/Variables";
import VacanciasFormulario, { VacanciaDeForm } from "./componentes/VacanciasFormulario";
import { ArrowBack, ArrowForward, Edit } from "@mui/icons-material";
import { deDateToStringYMD } from "../../comun";

const Vacancias = () => {
	const [consultorios, setConsultorios] = useState<Consultorio[]>([]);
	const [matriz, setMatriz] = useState<Matriz>();
	const [anclajeDeMenu, setAnclajeDeMenu] = useState<null | HTMLElement>(null);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [editar, setEditar] = useState<boolean>(false);
	const [coordenadaParaEditar, setCoordenadaParaEditar] = useState<Coordenada>({
		hora: 0,
		dia: DIADESEMANA.DOMINGO,
		numConsultorio: -1,
	});
	const [valoresInicialesDelForm, setValoresInicialesDelForm] = useState<VacanciaDeForm>(
		{
			numeroConsultorio: "",
			idContratoDeAlquiler: "",
			diaDeLaSemana: "",
			empiezaVacancia: "",
			terminaVacancia: "",
		});

	const hoyVariable = new Date();
	const [lunes, setLunes] = useState<Date>(new Date(hoyVariable.setDate(hoyVariable.getDate() - hoyVariable.getDay() + 1)));
	const [domingo, setDomingo] = useState<Date>(new Date(new Date(lunes).setDate(lunes.getDate() + 6)));

	useEffect(() => {
		cargarConsultorios();
	}, [domingo]);

	const cargarConsultorios = () => {
		getConsultorios()
			.then(({ data }) => setConsultorios(data))
			.then(() => cargarVacancias(),
			)
		;
	};

	const inicializarMatriz = (consultorios: Consultorio[]): Matriz => {
		const result = new Map<DIADESEMANA, Map<number, Map<number, VacanciaDeMatriz>>>();
		for (const dia of diasDeSemana) {
			const diaMap = new Map<number, Map<number, VacanciaDeMatriz>>();
			for (let hora = 8; hora <= 20; hora++) {
				const horaMap = new Map<number, VacanciaDeMatriz>();
				for (const consultorio of consultorios)
					horaMap.set(consultorio.numeroDeConsultorio as number, {
						ocupado: false,
					});
				diaMap.set(hora, horaMap);
			}
			result.set(dia, diaMap);
		}
		return result;
	};

	const cargarVacancias = () => {
		getVacancias(lunes, domingo).then(({ data }) => {
			const nuevaMatriz = inicializarMatriz(consultorios);
			data.forEach(({
				idAlquilerVacancia,
				diaDeLaSemana,
				numeroDeConsultorio,
				empiezaVacancia,
				terminaVacancia,
				idContratoDeAlquiler,
				nombre,
				apellido,
			}) => {


				const vacancia: VacanciaDeMatriz = {
					idAlquilerVacancia,
					profesional: `${nombre} ${apellido}`,
					ocupado: true,
					idContratoDeAlquiler,
				};
				const inicio = parseInt(empiezaVacancia);
				const fin = parseInt(terminaVacancia);
				for (let i = inicio; i < fin; i++) {
					nuevaMatriz?.get(diaDeLaSemana)?.get(i)?.set(numeroDeConsultorio, vacancia);
				}

			});
			setMatriz(nuevaMatriz);
		});
	};

	const sumarSemana = () => {
		const hoyVariable = new Date(lunes);
		setLunes(new Date(hoyVariable.setDate(hoyVariable.getDate() - hoyVariable.getDay() + 8)));
		setDomingo(new Date(new Date(hoyVariable).setDate(hoyVariable.getDate() + 6)));
	};

	const restarSemana = () => {
		const hoyVariable = new Date(lunes);
		setLunes(new Date(hoyVariable.setDate(hoyVariable.getDate() - hoyVariable.getDay() - 6)));
		setDomingo(new Date(new Date(hoyVariable).setDate(hoyVariable.getDate() + 6)));
	};


	const enviarFormulario = (valores: Vacancia) => {
		if (!editar) crearVacancias(valores).then(() => cargarVacancias());
		else {
			modificarVacancias({ id: coordenadaParaEditar.idAlquilerVacancia, ...valores })
				.then(() => cargarVacancias());
		}

	};

	const validarVacancia = (vacancia: Vacancia, numConsultorio: number) => {
		const empieza = parseInt(vacancia.empiezaVacancia);
		const termina = parseInt(vacancia.terminaVacancia);
		for (let i = empieza; i < termina; i++) {
			const varMatriz = matriz?.get(vacancia?.diaDeLaSemana)?.get(i)?.get(numConsultorio);
			if (varMatriz?.ocupado) return false;
		}
		return true;
	};

	const manejarAbrirFormulario = (hora: number, editar: boolean, dia: DIADESEMANA, numeroConsultorio?: number, idContratoDeAlquiler?: number) => {
		if (hora >= 8 && hora < 12) {
			setValoresInicialesDelForm({
				idContratoDeAlquiler: idContratoDeAlquiler || "",
				diaDeLaSemana: dia,
				numeroConsultorio: numeroConsultorio || "",
				empiezaVacancia: "8",
				terminaVacancia: "12",
			});
		} else if (hora >= 12 && hora < 16) {
			setValoresInicialesDelForm({
				idContratoDeAlquiler: idContratoDeAlquiler || "",
				diaDeLaSemana: dia,
				numeroConsultorio: numeroConsultorio || "",
				empiezaVacancia: "12",
				terminaVacancia: "16",
			});
		} else if (hora >= 16 && hora < 20) {
			setValoresInicialesDelForm({
				idContratoDeAlquiler: idContratoDeAlquiler || "",
				diaDeLaSemana: dia,
				numeroConsultorio: numeroConsultorio || "",
				empiezaVacancia: "16",
				terminaVacancia: "20",
			});
		}
		setEditar(editar);
		setAbrirFormulario(true);
	};

	const calculareTableHeigh = () => {
		const header = document.getElementById("app-header")?.clientHeight || 0;
		return window.innerHeight - header;
	};

	return (
		<div className={"vacancias"}>
			<TableContainer sx={{ maxHeight: calculareTableHeigh() }} className={"table-container"}>
				<Table>
					<TableHead
						// sx={{ backgroundColor:"#0c3454"}}
						className={"table-head"}
					>
						<>
							<TableRow className={"table-row"}>
								<TableCell className={"table-cell"} colSpan={(consultorios?.length * 7 + 1) / 2}>
									{deDateToStringYMD(lunes)}
								</TableCell>
								<TableCell className={"table-cell"}> al </TableCell>
								<TableCell className={"table-cell"} colSpan={(consultorios?.length * 7 + 1) / 2}>
									{deDateToStringYMD(domingo)}
								</TableCell>
							</TableRow>
							<TableRow className={"table-row"}>
								<TableCell className={"table-cell-titulo"}></TableCell>
								{diasDeSemana.map((dia, index) =>
									<TableCell
										align={"center"}
										colSpan={consultorios?.length}
										className={"table-cell-titulo"}
										key={index}> {dia}
									</TableCell>,
								)}
							</TableRow>
							<TableRow>
								<TableCell className={"table-cell-titulo"}>Consultorio</TableCell>
								{diasDeSemana.map(() =>
									consultorios?.map(c =>
										<TableCell className={"table-cell-titulo"}
												   key={c.numeroDeConsultorio}> {c.numeroDeConsultorio} </TableCell>,
									),
								)}
							</TableRow>
						</>
					</TableHead>
					<TableBody className={"table-body"}>
						<Filas
							consultorios={consultorios}
							matriz={matriz}
							setAnclajeDeMenu={setAnclajeDeMenu}
							manejarAbrirFormulario={manejarAbrirFormulario}
							setCoordenadaParaEditar={setCoordenadaParaEditar}
						/>
					</TableBody>
				</Table>
			</TableContainer>
			<Menu
				anchorEl={anclajeDeMenu}
				open={Boolean(anclajeDeMenu)}
				onClose={() => setAnclajeDeMenu(null)}
			>
				<MenuItem onClick={() =>
					manejarAbrirFormulario(
						coordenadaParaEditar.hora, true, coordenadaParaEditar.dia, coordenadaParaEditar.numConsultorio, coordenadaParaEditar.idContrato)
				}>
					<ListItemIcon>
						<Edit fontSize="small" />
					</ListItemIcon>
					<ListItemText>Editar vacancia</ListItemText>
				</MenuItem>
			</Menu>
			<VacanciasFormulario
				lunes={lunes}
				abrirFormulario={abrirFormulario}
				editar={editar}
				cerrarFormulario={() => {
					setAbrirFormulario(false);
					setEditar(false);
					setAnclajeDeMenu(null);
				}}
				enviarFormulario={enviarFormulario}
				valoresIniciales={valoresInicialesDelForm}
				validarVacancia={validarVacancia}
			/>
			<Fab className={"boton-suma-semana"} variant={"extended"} size={"large"}
				 onClick={sumarSemana}
			>
				Siguiente Semana
				<ArrowForward />
			</Fab>
			<Fab className={"boton-resta-semana"} variant={"extended"} size={"large"}
				 onClick={restarSemana}
			>
				<ArrowBack />
				Anterior Semana
			</Fab>
		</div>
	);
};

export default Vacancias;