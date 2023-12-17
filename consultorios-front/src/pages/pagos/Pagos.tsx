import "../../comun/estilo/ModuloEstandar.scss";
import { AlertaDeConfirmacion } from "../../comun";
import PagosFormulario from "./componentes/PagosFormulario";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { useEffect, useState } from "react";
import { ContratoConCosto, crearPago, getContratosConCosto, Pago } from "./PagosAPI";
import PagosField from "./componentes/PagosField";
import { borrarContrato } from "../contratos/ContratosAPI";


const Pagos = () => {
	const [contratosConCosto, setContratosConCosto] = useState<ContratoConCosto[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	useEffect(() => {
		cargarContratosConCosto();
	}, []);

	const cargarContratosConCosto = () => {
		getContratosConCosto()
			.then(({ data }) => setContratosConCosto(data));
	};

	const handleCerrarFormulario = () => {
		setEditar(false);
		setAbrirFormulario(false);
	};

	const borrar = () => {
		borrarContrato(idParaBorrar)
			.then(() => {
				setContratosConCosto(contratosConCosto.filter(ccc => ccc.idContrato !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			}).catch(e => console.error("Ereor al borrar: " + e));
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

	const handlePago = (idContrato: number, cantidad: number) => {
		crearPago({
			idContratoDeAlquiler: idContrato,
			fechaDeTransaccion: new Date(),
			cantidad: cantidad,
		}).then(() => {
			// Una vez que el pago se ha realizado con éxito, actualiza el estado
			// de contratosConCosto para reflejar el nuevo monto restante
			const nuevosContratos = contratosConCosto.map(contrato => {
				if (contrato.idContrato === idContrato) {
					// Reduce la deuda del contrato especificado
					return { ...contrato, montoRestante: contrato.montoRestante - cantidad };
				}
				return contrato;
			});

			setContratosConCosto(nuevosContratos);
		}).catch(error => {
			// Maneja cualquier error que ocurra durante el proceso de pago
			console.error("Error al realizar el pago:", error);
		});
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>Num del Contrato</TableCell>
							<TableCell className={"table-cell-titulo"}>Profesional</TableCell>
							<TableCell className={"table-cell-titulo"}>Num del consultorio</TableCell>
							<TableCell className={"table-cell-titulo"}>Costo total</TableCell>
							<TableCell className={"table-cell-titulo"}>Deuda</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							contratosConCosto.map((ccc) => {
								return (
									<TableRow key={ccc.idContrato}>
										<TableCell className={"table-cell"}>{ccc.idContrato}</TableCell>
										<TableCell
											className={"table-cell"}>{ccc.nombreProfesional + " " + ccc.apellido}</TableCell>
										<TableCell className={"table-cell"}>{ccc.numeroDeConsultorio}</TableCell>
										<TableCell className={"table-cell"}>{ccc.valorContrato}</TableCell>
										<TableCell className={"table-cell"}>{ccc.montoRestante}</TableCell>
										<PagosField
											handlePago={handlePago}
											idContrato={ccc.idContrato}
											alBorrar={() => {
												setIdParaBorrar(ccc.idContrato);
												setAbrirAlerta(true);
											}}
										/>
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
						<TableRow>
							<TableCell sx={{ height: 50 }} className={"table-cell"} />
						</TableRow>
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