import { useEffect, useState } from "react";
import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Acciones, AlertaDeConfirmacion } from "../../comun";
import { Add } from "@mui/icons-material";
import ExpensasFormulario from "./componentes/ExpensasFormulario";
import "../../comun/estilo/ModuloEstandar.scss";
import { borrarExpensa, crearExpensa, Expensa, getExpensas, modificarExpensa } from "./ExpensasAPI";

const Expensas = () => {
	const [expensas, setExpensas] = useState<Expensa[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	useEffect(() => {
		cargarExpensas();
	}, []);

	const cargarExpensas = () => {
		getExpensas()
			.then(({ data }) => setExpensas(data));
	};

	const handleCerrarFormulario = () => {
		setEditar(false);
		setAbrirFormulario(false);
	};

	const borrar = () => {
		borrarExpensa(idParaBorrar)
			.then(() => {
				setExpensas(expensas.filter(p => p.id !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			});
	};

	const enviarFormulario = (valores: Expensa) => {
		if (!editar) crearExpensa(valores)
			.then(() => cargarExpensas());
		else {
			modificarExpensa({ id: idParaEditar, ...valores })
				.then(() => cargarExpensas())
				.then(() => setEditar(false));
		}
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table stickyHeader>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>Descripción</TableCell>
							<TableCell className={"table-cell-titulo"}>Fecha de expensa</TableCell>
							<TableCell className={"table-cell-titulo"}>Cantidad</TableCell>
							<TableCell className={"table-cell-titulo"}>Tipo</TableCell>
							<TableCell className={"table-cell-titulo"}>Fecha de pago</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							expensas.map((expensa) => {
								return (
									<TableRow key={expensa.id}>
										<TableCell
											className={"table-cell"}>{expensa.descripcion}</TableCell>
										<TableCell
											className={"table-cell"}>{expensa.fechaDeExpensa}</TableCell>
										<TableCell className={"table-cell"}>{expensa.cantidad}</TableCell>
										<TableCell className={"table-cell"}>{expensa.seRepite}</TableCell>
										<TableCell
											className={"table-cell"}>{expensa.fechaDePago}</TableCell>
										<Acciones
											alBorrar={() => {
												setIdParaBorrar(expensa.id);
												setAbrirAlerta(true);
											}}
											alEditar={() => {
												setEditar(true);
												setIdParaEditar(expensa.id);
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
			<ExpensasFormulario
				editar={editar}
				abrirFormulario={abrirFormulario}
				cerrarFormulario={handleCerrarFormulario}
				enviarFormulario={enviarFormulario}
				valoresIniciales={editar ? expensas.find((p) => p.id === idParaEditar) : undefined}
			/>
			<AlertaDeConfirmacion
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={borrar} />
		</div>
	);
};

export default Expensas;