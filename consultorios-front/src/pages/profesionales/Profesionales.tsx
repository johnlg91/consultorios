import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { useEffect, useState } from "react";
import {
	borrarProfesional,
	crearProfesional,
	getProfesionales,
	modificarProfesional,
	Profesional,
} from "./ProfesionalesAPI";
import "../../comun/estilo/ModuloEstandar.scss";
import { Acciones, AlertaDeConfirmacion } from "../../comun";
import ProfesionalesFormulario from "./componentes/ProfesionalesFormulario";
import { Add } from "@mui/icons-material";

const Profesionales = () => {

	const [profesionales, setProfesionales] = useState<Profesional[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	useEffect(() => {
		cargarProfesionales();
	}, []);

	const cargarProfesionales = () => {
		getProfesionales()
			.then(({ data }) => setProfesionales(data));
	};

	const borrar = () => {
		borrarProfesional(idParaBorrar)
			.then(() => {
				setProfesionales(profesionales.filter(p => p.id !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			});
	};

	const enviarFormulario = (valores: Profesional) => {
		if (!editar) crearProfesional(valores)
			.then(() => cargarProfesionales());
		else {
			modificarProfesional({ id: idParaEditar, ...valores })
				.then(() => cargarProfesionales())
				.then(() => setEditar(false));
		}
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table stickyHeader>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>DNI</TableCell>
							<TableCell className={"table-cell-titulo"}>E-Mail</TableCell>
							<TableCell className={"table-cell-titulo"}>Teléfono</TableCell>
							<TableCell className={"table-cell-titulo"}>Sobrenombre</TableCell>
							<TableCell className={"table-cell-titulo"}>Nombre</TableCell>
							<TableCell className={"table-cell-titulo"}>Apellido</TableCell>
							<TableCell className={"table-cell-titulo"}>Especialidad</TableCell>
							<TableCell className={"table-cell-titulo"}>Dirección</TableCell>
							<TableCell className={"table-cell-titulo"}>Notas</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							profesionales.map((profesional) => (
								<TableRow key={profesional.id}>
									<TableCell className={"table-cell"}>{profesional.dni}</TableCell>
									<TableCell className={"table-cell"}>{profesional.eMail}</TableCell>
									<TableCell className={"table-cell"}>{profesional.telefonoCelular}</TableCell>
									<TableCell className={"table-cell"}>{profesional.sobrenombre}</TableCell>
									<TableCell className={"table-cell"}>{profesional.nombre}</TableCell>
									<TableCell className={"table-cell"}>{profesional.apellido}</TableCell>
									<TableCell className={"table-cell"}>{profesional.especialidad}</TableCell>
									<TableCell className={"table-cell"}>{profesional.direccion}</TableCell>
									<TableCell className={"table-cell"}>{profesional.notas}</TableCell>
									<Acciones
										alBorrar={() => {
											setIdParaBorrar(profesional.id);
											setAbrirAlerta(true);
										}}
										alEditar={() => {
											setEditar(true);
											setIdParaEditar(profesional.id);
											setAbrirFormulario(true);
										}}
									/>
								</TableRow>
							))
						}
					</TableBody>
				</Table>
			</TableContainer>
			<Fab className={"boton-de-agregado"} variant={"extended"} size={"large"}
				 onClick={() => setAbrirFormulario(true)}
			>
				<Add />
				Agregar
			</Fab>
			<ProfesionalesFormulario
				editar={editar}
				abrirFormulario={abrirFormulario}
				cerrarFormulario={() => setAbrirFormulario(false)}
				enviarFormulario={enviarFormulario}
				valoresIniciales={editar ? profesionales.find((p) => p.id === idParaEditar) : undefined}
			/>
			<AlertaDeConfirmacion
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={borrar} />
		</div>
	);
};

export default Profesionales;
