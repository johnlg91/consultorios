import "../../comun/estilo/ModuloEstandar.scss";
import { AlertaDeConfirmacion } from "../../comun";
import PagosFormulario from "./componentes/PagosFormulario";
import {
	IconButton,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TableRow,
	TextField,
} from "@mui/material";
import { Add } from "@mui/icons-material";
import { useEffect, useState } from "react";
import { borrarPago, Pago } from "./PagosAPI";

const Pagos = () => {
	const [pagos, setPagos] = useState(
		[
			{
				id: 1,
				nombre: "juan",
				apellido: "lopez",
				contratos: 4,
				saldo: -1000,
			},
			{
				id: 2,
				nombre: "Albert",
				apellido: "nuñez",
				contratos: 4,
				saldo: 69420,
			},
		],
	);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	useEffect(() => {
		// cargarPagos();
	}, []);

	// const cargarPagos = () => {
	// 	getPagos()
	// 		.then(({ data }) => setPagos(data));
	// };

	const handleCerrarFormulario = () => {
		setEditar(false);
		setAbrirFormulario(false);
	};

	const borrar = () => {
		borrarPago(idParaBorrar)
			.then(() => {
				setPagos(pagos.filter(p => p.id !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			});
	};

	const enviarFormulario = (valores: Pago) => {
		// if (!editar) crearPago(valores)
		// 	.then(() => cargarPagos());
		// else {
		// 	modificarPago({ id: idParaEditar, ...valores })
		// 		.then(() => cargarPagos())
		// 		.then(() => setEditar(false));
		// }
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>Profesional</TableCell>
							<TableCell className={"table-cell-titulo"}>Cantidad de contratos</TableCell>
							<TableCell className={"table-cell-titulo"}>SAldo</TableCell>
							<TableCell className={"table-cell-titulo"}>Cantidad</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							pagos.map((pago) => {
								return (
									<TableRow key={pago.id}>
										<TableCell
											className={"table-cell"}>{pago.nombre + " " + pago.apellido}</TableCell>
										<TableCell className={"table-cell"}>{pago.contratos}</TableCell>
										<TableCell className={"table-cell"}>{pago.saldo}</TableCell>
										<TableCell className={"table-cell"}>
											<TextField size={"small"} type={"number"} sx={{ bgcolor: "white" }}>

											</TextField>
										</TableCell>
										<TableCell className={"table-cell"} sx={{ bgcolor: "white" }}>
											<IconButton size={"small"}>
												<Add />
												Modificar saldo
											</IconButton>
										</TableCell>


										{/*<Acciones*/}
										{/*	alBorrar={() => {*/}
										{/*		setIdParaBorrar(pago.id);*/}
										{/*		setAbrirAlerta(true);*/}
										{/*	}}*/}
										{/*	alEditar={() => {*/}
										{/*		setEditar(true);*/}
										{/*		setIdParaEditar(pago.id);*/}
										{/*		setAbrirFormulario(true);*/}
										{/*	}}*/}
										{/*/>*/}
									</TableRow>
								);
							})
						}
					</TableBody>
				</Table>
			</TableContainer>
			{/*<Fab className={"boton-de-agregado"} variant={"extended"} size={"large"}*/}
			{/*	 onClick={() => setAbrirFormulario(true)}*/}
			{/*>*/}
			{/*	<Add />*/}
			{/*	Agregar*/}
			{/*</Fab>*/}
			<PagosFormulario
				editar={editar}
				abrirFormulario={abrirFormulario}
				cerrarFormulario={handleCerrarFormulario}
				enviarFormulario={enviarFormulario}
				// valoresIniciales={editar ? pagos.find((p) => p.id === idParaEditar) : undefined}
			/>
			<AlertaDeConfirmacion
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={borrar} />
		</div>
	);
};

export default Pagos;