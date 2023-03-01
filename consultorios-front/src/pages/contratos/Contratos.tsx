import "../../comun/estilo/ModuloEstandar.scss";
import { useEffect, useState } from "react";
import {
	borrarContrato,
	Contrato,
	ContratoConNombre,
	crearContrato,
	getContrato,
	getContratosConNombre,
	modificarContrato,
} from "./ContratosAPI";
import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Acciones, AlertaDeConfirmacion } from "../../comun";
import { Add } from "@mui/icons-material";
import ContratosFormulario from "./componentes/ContratosFormulario";

const Contratos = () => {
	const [contratos, setContratos] = useState<ContratoConNombre[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);
	const [contratoParaEditar, setContratoParaEditar] = useState<Contrato>({
		idConsultorio: 0,
		idProfesional: 0,
		tipoDeAlquiler: "",
		inicioDelContratoDeAlquiler: new Date(),
		finDelContrato: new Date(),
		costoTotal: null,
		notas: "",
	});

	useEffect(() => {
		cargarContratos();
	}, []);

	useEffect(() => {
		if (idParaEditar !== -1) cargarContratoAEditar();
	}, [idParaEditar]);

	const cargarContratos = () => {
		getContratosConNombre()
			.then(({ data }) => setContratos(data));
	};

	const handleCerrarFormulario = () => {
		setEditar(false);
		setAbrirFormulario(false);
	};

	const borrar = () => {
		borrarContrato(idParaBorrar)
			.then(() => {
				setContratos(contratos.filter(c => c.id !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			});
	};

	const enviarFormulario = (valores: Contrato) => {
		if (!editar) crearContrato(valores)
			.then(() => cargarContratos());
		else {
			modificarContrato({ id: idParaEditar, ...valores })
				.then(() => cargarContratos())
				.then(() => setEditar(false));
		}
	};

	const cargarContratoAEditar = () => {
		getContrato(idParaEditar)
			.then(r => setContratoParaEditar(r.data));
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table stickyHeader>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>Profesional</TableCell>
							<TableCell className={"table-cell-titulo"}>Consultorio</TableCell>
							<TableCell className={"table-cell-titulo"}>Tipo de Alquiler</TableCell>
							<TableCell className={"table-cell-titulo"}>Comienzo del contrato</TableCell>
							<TableCell className={"table-cell-titulo"}>Fin del contrato</TableCell>
							<TableCell className={"table-cell-titulo"}>Notas</TableCell>
							<TableCell className={"table-cell-titulo"}>Costo Mensual</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							contratos.map((contrato) => {
								return (
									<TableRow key={contrato.id}>
										<TableCell
											className={"table-cell"}>{contrato.sobrenombre}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.numeroDeConsultorio}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.tipoDeAlquiler}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.inicioDelContratoDeAlquiler}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.finDelContrato}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.notas} </TableCell>
										<TableCell
											className={"table-cell"}>{contrato.costoTotal} </TableCell>
										<Acciones
											alBorrar={() => {
												setIdParaBorrar(contrato.id);
												setAbrirAlerta(true);
											}}
											alEditar={() => {
												setEditar(true);
												setIdParaEditar(contrato.id);
												setAbrirFormulario(true);
											}}
										/>
									</TableRow>
								);
							})
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
			<ContratosFormulario
				editar={editar}
				abrirFormulario={abrirFormulario}
				cerrarFormulario={handleCerrarFormulario}
				enviarFormulario={enviarFormulario}
				valoresIniciales={
					editar ? contratoParaEditar :
						{
							idConsultorio: -1,
							idProfesional: -1,
							tipoDeAlquiler: "",
							inicioDelContratoDeAlquiler: new Date(),
							finDelContrato: new Date(),
							costoTotal: null,
							notas: "",
						}}
			/>
			<AlertaDeConfirmacion
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={borrar} />
		</div>
	);
};

export default Contratos;