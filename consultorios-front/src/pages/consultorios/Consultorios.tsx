import { useEffect, useState } from "react";
import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Acciones, AlertaDeConfirmacion } from "../../comun";
import { Add } from "@mui/icons-material";
import {
	borrarConsultorio,
	Consultorio,
	crearConsultorio,
	getConsultorios,
	modificarConsultorio,
} from "./ConsultoriosAPI";
import ConsultoriosFormulario from "./componentes/ConsultoriosFormulario";
import "../../comun/estilo/ModuloEstandar.scss";

const Consultorios = () => {
	const [consultorios, setConsultorios] = useState<Consultorio[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	useEffect(() => {
		cargarConsultorios();
	}, []);

	const cargarConsultorios = () => {
		getConsultorios()
			.then(({ data }) => setConsultorios(data));
	};

	const handleCerrarFormulario = () => {
		setEditar(false);
		setAbrirFormulario(false);
	};

	const borrar = () => {
		borrarConsultorio(idParaBorrar)
			.then(() => {
				setConsultorios(consultorios.filter(p => p.id !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			});
	};

	const enviarFormulario = (valores: Consultorio) => {
		if (!editar) crearConsultorio(valores)
			.then(() => cargarConsultorios());
		else {
			modificarConsultorio({ id: idParaEditar, ...valores })
				.then(() => cargarConsultorios())
				.then(() => setEditar(false));
		}
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table stickyHeader>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>Numero de consultorio</TableCell>
							<TableCell className={"table-cell-titulo"}>Costo por modulo</TableCell>
							<TableCell className={"table-cell-titulo"}>Tamaño del area</TableCell>
							<TableCell className={"table-cell-titulo"}>Equipo</TableCell>
							<TableCell className={"table-cell-titulo"}>Especialidades</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							consultorios.map((consultorio) => {
								return (
									<TableRow key={consultorio.id}>
										<TableCell
											className={"table-cell"}>{consultorio.numeroDeConsultorio}</TableCell>
										<TableCell className={"table-cell"}>{consultorio.costoPorModulo}</TableCell>
										<TableCell className={"table-cell"}>{consultorio.tamanioDelArea}</TableCell>
										<TableCell className={"table-cell"}>{consultorio.equipo}</TableCell>
										<TableCell className={"table-cell"}>{consultorio.especialidades}</TableCell>
										<Acciones
											alBorrar={() => {
												setIdParaBorrar(consultorio.id);
												setAbrirAlerta(true);
											}}
											alEditar={() => {
												setEditar(true);
												setIdParaEditar(consultorio.id);
												setAbrirFormulario(true);
											}}
										/>
									</TableRow>
								);
							})
						}
						<TableRow>
							<TableCell sx={{ height: 50 }} className={"table-cell"} />
						</TableRow>
					</TableBody>
				</Table>
			</TableContainer>
			<Fab className={"boton-de-agregado"} variant={"extended"} size={"large"}
				 onClick={() => setAbrirFormulario(true)}
			>
				<Add />
				Agregar
			</Fab>
			<ConsultoriosFormulario
				editar={editar}
				abrirFormulario={abrirFormulario}
				cerrarFormulario={handleCerrarFormulario}
				enviarFormulario={enviarFormulario}
				valoresIniciales={editar ? consultorios.find((p) => p.id === idParaEditar) : undefined}
			/>
			<AlertaDeConfirmacion
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={borrar} />
		</div>
	);
};

export default Consultorios;