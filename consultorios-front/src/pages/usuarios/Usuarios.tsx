import { useEffect, useState } from "react";
import { Fab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Acciones, AlertaDeConfirmacion } from "../../comun";
import { Add } from "@mui/icons-material";
import { borrarUsuario, crearUsuario, getUsuarios, modificarUsuario, Usuario } from "./UsuariosAPI";
import UsuariosFormulario from "./componentes/UsuariosFormulario";
import "../../comun/estilo/ModuloEstandar.scss";

const Usuarios = () => {
	const [usuarios, setUsuarios] = useState<Usuario[]>([]);
	const [abrirFormulario, setAbrirFormulario] = useState<boolean>(false);
	const [abrirAlerta, setAbrirAlerta] = useState<boolean>(false);
	const [idParaBorrar, setIdParaBorrar] = useState<number | undefined>(-1);
	const [editar, setEditar] = useState<boolean>(false);
	const [idParaEditar, setIdParaEditar] = useState<number | undefined>(-1);

	useEffect(() => {
		cargarUsuarios();
	}, []);

	const cargarUsuarios = () => {
		getUsuarios()
			.then(({ data }) => setUsuarios(data));
	};

	const handleCerrarFormulario = () => {
		setEditar(false);
		setAbrirFormulario(false);
	};

	const borrar = () => {
		if (usuarios.length === 1) return;
		borrarUsuario(idParaBorrar)
			.then(() => {
				setUsuarios(usuarios.filter(p => p.id !== idParaBorrar));
			})
			.then(() => {
				setAbrirAlerta(false);
				setIdParaBorrar(-1);
			});
	};

	const enviarFormulario = (valores: Usuario) => {
		if (!editar) crearUsuario(valores)
			.then(() => cargarUsuarios());
		else {
			modificarUsuario({ id: idParaEditar, ...valores })
				.then(() => cargarUsuarios())
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
							<TableCell className={"table-cell-titulo"}>Nombre de usuario</TableCell>
							<TableCell className={"table-cell-titulo"}>Email</TableCell>
							<TableCell className={"table-cell-titulo"}>Admin</TableCell>
							{/*<TableCell className={"table-cell-titulo"}>Contraseña</TableCell>*/}
							<TableCell className={"table-cell-titulo"}>Acciones</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{
							usuarios.map((usuario) => {
								return (
									<TableRow key={usuario.id}>
										<TableCell className={"table-cell"}>{usuario.dni}</TableCell>
										<TableCell className={"table-cell"}>{usuario.nombreUsuario}</TableCell>
										<TableCell className={"table-cell"}>{usuario.email}</TableCell>
										<TableCell
											className={"table-cell"}>{usuario.esAdmin ? "admin" : "no"}</TableCell>
										{/*<Contrasenia contrasenia={usuario.contrasennia} />*/}
										<Acciones
											alBorrar={() => {
												setIdParaBorrar(usuario.id);
												setAbrirAlerta(true);
											}}
											alEditar={() => {
												setEditar(true);
												setIdParaEditar(usuario.id);
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
			<UsuariosFormulario
				editar={editar}
				abrirFormulario={abrirFormulario}
				cerrarFormulario={handleCerrarFormulario}
				enviarFormulario={enviarFormulario}
				valoresIniciales={editar ? usuarios.find((p) => p.id === idParaEditar) : undefined}
			/>
			<AlertaDeConfirmacion
				abierto={abrirAlerta} handleCerrar={() => setAbrirAlerta(false)}
				handleConfirmar={borrar} />
		</div>
	);
};

export default Usuarios;