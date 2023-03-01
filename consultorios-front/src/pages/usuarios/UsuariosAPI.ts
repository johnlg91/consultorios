import URL from "../../axiosconfig";
import { AxiosPromise } from "axios";

export interface Usuario {
	id?: number;
	dni: number;
	nombreUsuario: number;
	email: string;
	contrasennia: string;
	esAdmin: boolean;
	oculto?: boolean;
}

export const getUsuarios = (): AxiosPromise<Usuario[]> => URL.get("usuarios");

export const getUsuario = (id: number): AxiosPromise<Usuario[]> => URL.get(`usuarios/${id}`);

export const crearUsuario = (datos: Usuario): AxiosPromise => URL.post("usuarios", datos);

export const modificarUsuario = (datos: Usuario): AxiosPromise => URL.put("usuarios", datos);

export const borrarUsuario = (id: number | undefined): AxiosPromise => URL.delete(`usuarios/${id}`);