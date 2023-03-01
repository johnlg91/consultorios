import "../../comun/estilo/ModuloEstandar.scss";

const Pagos = () => {
	// const [pagos, setPagos] = useState<Pago[]>([]);
	// const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	// const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	// const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	// const [editar, setEditar] = useState<boolean>(false);
	// const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);
	//
	// useEffect(() => {
	// 	cargarPagos();
	// }, []);
	//
	// const cargarPagos = () => {
	// 	getPagos()
	// 		.then(({ data }) => setPagos(data));
	// };
	//
	// const handleCerrarFormulario = () => {
	// 	setEditar(false);
	// 	setAbrirFormulario(false);
	// };
	//
	// const borrar = () => {
	// 	borrarPago(idParaBorrar)
	// 		.then(() => {
	// 			setPagos(pagos.filter(p => p.id !== idParaBorrar));
	// 		})
	// 		.then(() => {
	// 			setAbrirAlerta(false);
	// 			setIdParaBorrar(-1);
	// 		});
	// };
	//
	// const enviarFormulario = (valores: Pago) => {
	// 	if (!editar) crearPago(valores)
	// 		.then(() => cargarPagos());
	// 	else {
	// 		modificarPago({ id: idParaEditar, ...valores })
	// 			.then(() => cargarPagos())
	// 			.then(() => setEditar(false));
	// 	}
	// };
	//
	// return (
	// 	<div className={"modulo"}>
	// 		<TableContainer className={"table-container"}>
	// 			<Table>
	// 				<TableHead>
	// 					<TableRow>
	// 						<TableCell className={"table-cell-titulo"}>Profesional</TableCell>
	// 						<TableCell className={"table-cell-titulo"}>Tipo de pago</TableCell>
	// 						<TableCell className={"table-cell-titulo"}>Metodo de pago</TableCell>
	// 						<TableCell className={"table-cell-titulo"}>Fecha de pago</TableCell>
	// 						<TableCell className={"table-cell-titulo"}>cantidad</TableCell>
	// 						<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
	// 					</TableRow>
	// 				</TableHead>
	// 				<TableBody>
	// 					{
	// 						pagos.map((pago) => {
	// 							return (
	// 								<TableRow key={pago.id}>
	// 									<TableCell className={"table-cell"}>{pago.idContratoDeAlquiler}</TableCell>
	// 									<TableCell className={"table-cell"}>{pago.tipo}</TableCell>
	// 									<TableCell className={"table-cell"}>{pago.metodoDePago}</TableCell>
	// 									{/*<TableCell className={"table-cell"}>{pago.fechaDeTransaccion?.toISOString()}</TableCell>*/}
	// 									<TableCell className={"table-cell"}>{pago.cantidad}</TableCell>
	// 									<Acciones
	// 										alBorrar={() => {
	// 											setIdParaBorrar(pago.id);
	// 											setAbrirAlerta(true);
	// 										}}
	// 										alEditar={() => {
	// 											setEditar(true);
	// 											setIdParaEditar(pago.id);
	// 											setAbrirFormulario(true);
	// 										}}
	// 									/>
	// 								</TableRow>
	// 							);
	// 						})
	// 					}
	// 				</TableBody>
	// 			</Table>
	// 		</TableContainer>
	// 		<Fab className={"boton-de-agregado"} variant={"extended"} size={"large"}
	// 			 onClick={() => setAbrirFormulario(true)}
	// 		>
	// 			<Add />
	// 			Agregar
	// 		</Fab>
	// 		<PagosFormulario
	// 			editar={editar}
	// 			abrirFormulario={abrirFormulario}
	// 			cerrarFormulario={handleCerrarFormulario}
	// 			enviarFormulario={enviarFormulario}
	// 			// valoresIniciales={editar ? pagos.find((p) => p.id === idParaEditar) : undefined}
	// 		/>
	// 		<AlertaDeConfirmacion
	// 			abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
	// 			handleConfirmar={borrar} />
	// 	</div>
	// );
	return null;
};

export default Pagos;