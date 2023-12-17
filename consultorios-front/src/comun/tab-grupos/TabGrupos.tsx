import { Grid, IconButton, Tooltip, Typography } from "@mui/material";
import Tab from "./Tab";
import "./TabGrupos.scss";
import { useState } from "react";
import Profesionales from "../../pages/profesionales/Profesionales";
import Consultorios from "../../pages/consultorios/Consultorios";
import Contratos from "../../pages/contratos/Contratos";
import Vacancias from "../../pages/vacancias/Vacancias";
import Expensas from "../../pages/expensas/Expensas";
import Reportes from "../../pages/reportes/Reportes";
import Usuarios from "../../pages/usuarios/Usuarios";
import URL from "../../axiosconfig";
import { Logout } from "@mui/icons-material";
import Pagos from "../../pages/pagos/Pagos";

const PROFESIONALES = "PROFESIONALES";
const CONSULTORIOS = "CONSULTORIOS";
const PLANILLA = "PLANILLA";
const CONTRATOS = "CONTRATOS";
const EXPENSAS = "EXPENSAS";
const PAGOS = "PAGOS";
const REPORTES = "REPORTES";
const USUARIOS = "USUARIOS";

const TabGrupos = () => {

	const [moduloSeleccionado, setModuloSeleccionado] = useState<string>(PAGOS);
	const [esAdmin, setEsAdmin] = useState<boolean>(false);

	// useEffect(() => {
	// 	getUsuario;
	// }, []);

	const tabSwitch = () => {
		switch (moduloSeleccionado) {
		case PROFESIONALES:
			return <Profesionales />;
		case CONSULTORIOS:
			return <Consultorios />;
		case PLANILLA:
			return <Vacancias />;
		case CONTRATOS:
			return <Contratos />;
		case PAGOS:
			return <Pagos />;
		case EXPENSAS:
			return <Expensas />;
		case REPORTES:
			return <Reportes />;
		case USUARIOS:
			return <Usuarios />;
		default:
			return <Vacancias />;
		}
	};

	const logout = () => {
		URL.post("/logout");
		location.href = "login";
	};

	return (
		<>
			<Grid container direction={"row"} className={"Tab-Grupos"} wrap={"nowrap"} id="app-header">
				<Grid item className={"titulo"}>
					<Typography color={"black"} variant={"h6"}>T-Med</Typography>
				</Grid>
				<Tab titulo={"Planilla"} selected={moduloSeleccionado === PLANILLA}
					 onClick={() => setModuloSeleccionado(PLANILLA)} />
				<Tab titulo={"Contratos"} selected={moduloSeleccionado === CONTRATOS}
					 onClick={() => setModuloSeleccionado(CONTRATOS)} />
				<Tab titulo={"Pagos"} selected={moduloSeleccionado === PAGOS}
					 onClick={() => setModuloSeleccionado(PAGOS)} />
				<Tab titulo={"Profesionales"} selected={moduloSeleccionado === PROFESIONALES}
					 onClick={() => setModuloSeleccionado(PROFESIONALES)} />
				<Tab titulo={"Consultorios"} selected={moduloSeleccionado === CONSULTORIOS}
					 onClick={() => setModuloSeleccionado(CONSULTORIOS)} />
				<Tab titulo={"Expensas"} selected={moduloSeleccionado === EXPENSAS}
					 onClick={() => setModuloSeleccionado(EXPENSAS)} />
				<Tab titulo={"Reportes"} selected={moduloSeleccionado === REPORTES}
					 onClick={() => setModuloSeleccionado(REPORTES)} />
				<Tab titulo={"Usuarios"} selected={moduloSeleccionado === USUARIOS}
					 onClick={() => setModuloSeleccionado(USUARIOS)} />
				<Grid item>
					<Tooltip title={"Logout"}>
						<IconButton onClick={() => logout()}>
							<Logout />
						</IconButton>
					</Tooltip>
				</Grid>
			</Grid>
			{tabSwitch()}
		</>
	);
};

export default TabGrupos;
