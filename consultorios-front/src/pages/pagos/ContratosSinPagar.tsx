import "../../comun/estilo/ModuloEstandar.scss";
import { useEffect, useState } from "react";
import { Checkbox, Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { AlertaDeConfirmacion, deDateToStringYMD } from "../../comun";
import { Add } from "@mui/icons-material";
import { Contrato, getContratosSinPagar } from "../contratos/ContratosAPI";
import { agregarPagos, Pago } from "./PagosAPI";

const Contratos = () => {
	const [contratos, setContratos] = useState<Contrato[]>([]);
	const [contratosPagados, setContratosPagados] = useState<Contrato[]>([]);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
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

	const cargarContratos = () => {
		getContratosSinPagar(new Date() as Date)
			.then(({ data }) => setContratos(data));
	};

	const cargarPagos = () => {
		const nuevosPagos: Pago[] = [];
		contratosPagados.forEach((contrato) => {
			nuevosPagos.push({
				idContratoDeAlquiler: contrato.id as number,
				fechaDeTransaccion: new Date(),
				tipo: "DEBITO",
				metodoDePago: "EFECTIVO",
				cantidad: contrato.costoTotal as number,
			});
		});
		agregarPagos(nuevosPagos).then(() => cargarContratos()).then(() => setAbrirAlerta(false));
	};

	const manejarCheck = (contrato: Contrato) => {
		if (contratosPagados.includes(contrato)) setContratosPagados(contratosPagados.filter(c => c !== contrato));
		else setContratosPagados([contrato, ...contratosPagados]);
	};

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table stickyHeader>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>ID Profesional</TableCell>
							<TableCell className={"table-cell-titulo"}>ID Consultorio</TableCell>
							<TableCell className={"table-cell-titulo"}>Tipo de Alquiler</TableCell>
							<TableCell className={"table-cell-titulo"}>Fecha de vencimientor</TableCell>
							<TableCell className={"table-cell-titulo"}>Notas</TableCell>
							<TableCell className={"table-cell-titulo"}>Costo Mensual</TableCell>
							<TableCell className={"table-cell-titulo"}>Pagado</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							contratos.map((contrato) => {
								return (
									<TableRow key={contrato.id}>
										<TableCell
											className={"table-cell"}>{contrato.idProfesional}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.idProfesional}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.tipoDeAlquiler}</TableCell>
										<TableCell
											className={"table-cell"}>{deDateToStringYMD(contrato.finDelContrato)}</TableCell>
										<TableCell
											className={"table-cell"}>{contrato.notas} </TableCell>
										<TableCell
											className={"table-cell"}>{contrato.costoTotal} </TableCell>
										<TableCell className={"table-cell"}>
											<Checkbox sx={{ color: "white" }} onClick={() => manejarCheck(contrato)}
													  value={contratosPagados.includes(contrato)} />
										</TableCell>
									</TableRow>
								);
							})
						}
					</TableBody>
				</Table>
			</TableContainer>
			<Fab className={"boton-de-agregado"} variant={"extended"} size={"large"}
				 onClick={() => setAbrirAlerta(true)}
			>
				<Add />
				Agregar
			</Fab>
			<AlertaDeConfirmacion
				texto={"¿Confirma que estan pagados?"}
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={cargarPagos} />
		</div>
	);
};

export default Contratos;