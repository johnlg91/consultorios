import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Tooltip } from "@mui/material";
import { useEffect, useState } from "react";
import {
	borrarProfesional,
	crearProfesional,
	getProfesionales,
	modificarProfesional,
	Profesional,
} from "./ProfesionalesAPI";
import "../../comun/estilo/ModuloEstandar.scss";
import { Acciones, AlertaDeConfirmacion, TableCellSorted } from "../../comun";
import ProfesionalesFormulario from "./componentes/ProfesionalesFormulario";
import { Add } from "@mui/icons-material";

type profKeys = keyof Profesional;
const Profesionales = () => {

	const [profesionales, setProfesionales] = useState<Profesional[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	const [sortField, setSortField] = useState<keyof Profesional | null>(null);
	const [sortDirection, setSortDirection] = useState<"asc" | "desc">("asc");


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

	const handleSort = (field: keyof Profesional) => {
		const isAsc = sortField === field && sortDirection === "asc";
		setSortDirection(isAsc ? "desc" : "asc");
		setSortField(field);
	};

	const sortedProfesionales = [...profesionales].sort((a, b) => {
		if (!sortField) return 0;

		const valueA = a[sortField];
		const valueB = b[sortField];

		// Check if the field is a date field
		if (sortField === "fechaDeSubscripcion") {
			// Check if both dates are valid strings
			if (typeof valueA === "string" && valueA.trim() !== "" && typeof valueB === "string" && valueB.trim() !== "") {
				const dateA = new Date(valueA);
				const dateB = new Date(valueB);
				return sortDirection === "asc" ? dateA.getTime() - dateB.getTime() : dateB.getTime() - dateA.getTime();
			} else {
				// Handle cases where one or both dates are empty or invalid
				// For example, you might decide to sort empty dates to the end
				if (valueA && !valueB) {
					return sortDirection === "asc" ? -1 : 1;
				} else if (!valueA && valueB) {
					return sortDirection === "asc" ? 1 : -1;
				}
				return 0;
			}
		}

		if (typeof valueA === "number" && typeof valueB === "number") {
			return sortDirection === "asc" ? valueA - valueB : valueB - valueA;
		} else if (typeof valueA === "string" && typeof valueB === "string") {
			return sortDirection === "asc" ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
		}

		return 0;
	});

	return (
		<div className={"modulo"}>
			<TableContainer className={"table-container"}>
				<Table stickyHeader>
					<TableHead>
						<TableRow>
							<TableCell className={"table-cell-titulo"}>DNI</TableCell>
							<TableCellSorted label={"Subsripción"} field={"fechaDeSubscripcion"} sortField={sortField}
											 sortDirection={sortDirection} handleSort={handleSort} />
							<TableCell className={"table-cell-titulo"}>E-Mail</TableCell>
							<TableCell className={"table-cell-titulo"}>Teléfono</TableCell>
							<TableCellSorted label={"Sobrenombre"} field={"sobrenombre"} sortField={sortField}
											 sortDirection={sortDirection} handleSort={handleSort} />
							<TableCellSorted label={"Nombre"} field={"nombre"} sortField={sortField}
											 sortDirection={sortDirection} handleSort={handleSort} />
							<TableCellSorted label={"Apellido"} field={"apellido"} sortField={sortField}
											 sortDirection={sortDirection} handleSort={handleSort} />
							<TableCellSorted label={"Especialidad"} field={"especialidad"} sortField={sortField}
											 sortDirection={sortDirection} handleSort={handleSort} />
							<TableCell className={"table-cell-titulo"}>Dirección</TableCell>
							<TableCell className={"table-cell-titulo"}>Notas</TableCell>
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							sortedProfesionales.map((profesional) => (
								<TableRow key={profesional.id}>
									<TableCell className={"table-cell"}>{profesional.dni}</TableCell>
									<TableCell className={"table-cell"}>{profesional.fechaDeSubscripcion}</TableCell>
									<TableCell className={"table-cell"}>{profesional.eMail}</TableCell>
									<TableCell className={"table-cell"}>{profesional.telefonoCelular}</TableCell>
									<TableCell className={"table-cell"}>{profesional.sobrenombre}</TableCell>
									<TableCell className={"table-cell"}>{profesional.nombre}</TableCell>
									<TableCell className={"table-cell"}>{profesional.apellido}</TableCell>
									<TableCell className={"table-cell"}>{profesional.especialidad}</TableCell>
									<TableCell className={"table-cell"}>{profesional.direccion}</TableCell>
									<TableCell className={"table-cell"}>
										<Tooltip title={profesional.notas || ""} placement="bottom-end">
											<div className="truncate-text">{profesional.notas}</div>
										</Tooltip>
									</TableCell> <Acciones
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
