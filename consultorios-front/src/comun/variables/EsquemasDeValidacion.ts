import * as yup from "yup";
import es from "yup-es";

yup.setLocale(es);

const ESQUEMAS = {
	PROFESIONALES: yup.object({
		dni: yup.number().min(8, "Minimo 8 caracteres").required(),
		eMail: yup.string().email().required(),
		nombre: yup.string().required(),
		apellido: yup.string().required(),
		sobrenombre: yup.string(),
		telefonoCelular: yup.string().required(),
		especialidad: yup.string().required(),
		direccion: yup.string().required(),
		notas: yup.string(),
	}),
	CONSULTORIOS: yup.object({
		numeroDeConsultorio: yup.number().required().positive(),
		costoPorModulo: yup.number().required().min(0),
		tamanioDelArea: yup.number().required().min(0),
		equipo: yup.string().required(),
		especialidades: yup.string().required(),
	}),
	CONTRATOS: yup.object({
		idConsultorio: yup.number().required().min(0, "Seleccione un Consultorio"),
		idProfesional: yup.number().required().min(0, "Seleccione un Profesional"),
		tipoDeAlquiler: yup.string().required(),
		inicioDelContratoDeAlquiler: yup.date().required(),
		finDelContrato: yup.date().min(
			yup.ref("inicioDelContratoDeAlquiler"),
			({ min }) => `La fecha debe ser mayor que ${new Date(min).toLocaleDateString()}!`,
		).when("tipoDeAlquiler", {
			is: (v: string) => v === "NORMAL",
			then: yup.date().required(),
		}),
		costoTotal: yup.number().required().min(0),
		notas: yup.string(),
	}),
	EXPENSAS: yup.object({
		descripcion: yup.string().required(),
		fechaDeExpensa: yup.date(),
		cantidad: yup.number().required().min(0),
		seRepite: yup.string().required(),
		fechaDePago: yup.date(),
	}),
	VACANCIAS: yup.object({
		idContratoDeAlquiler: yup.number().required(),
		diaDeLaSemana: yup.string().required(),
		empiezaVacancia: yup.number().required().min(8).max(21),
		terminaVacancia: yup.number().required().moreThan(yup.ref("empiezaVacancia")).max(22),
	}),
	PAGOS: yup.object({}),
	USUARIOS: yup.object({
		dni: yup.number().required().positive()
			.test(
				"len", "Debe tener mas de 7 caracteres",
				val => !val || (Boolean(val) && val.toString().length > 7)),
		nombreUsuario: yup.string().required(),
		email: yup.string().email().required(),
		contrasennia: yup.string().matches(
			new RegExp("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"),
			"Debe contener 8 caracteres, una letra y un numero",
		).required(),
		confirmarContrasennia: yup.string().required()
			.oneOf([yup.ref("contrasennia"), null], "Passwords must match"),
		esAdmin: yup.boolean().default(false),
	}),
};

export default ESQUEMAS;