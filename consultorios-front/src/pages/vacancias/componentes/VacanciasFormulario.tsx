import { Alert, Button, Dialog, Grid, MenuItem, Paper, Snackbar, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { ESQUEMAS } from "../../../comun";
import { DIADESEMANA, diasDeSemana, horas, Vacancia } from "./Variables";
import SelectField from "../../../comun/componentes/SelectField";
import selectField from "../../../comun/componentes/SelectField";
import { ContratoConNombre } from "../../contratos/ContratosAPI";
import { getContratosPorConsultorio } from "../VacanciasAPI";
import { useEffect, useState } from "react";

interface FormularioProps {
	lunes: Date,
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales: VacanciaDeForm;
	enviarFormulario: (valores: Vacancia, numConsultorio?: number) => void;
	validarVacancia: (vacancia: Vacancia, numConsultorio: number) => boolean;
}

export interface VacanciaDeForm {
	numeroConsultorio: string | number;
	idContratoDeAlquiler: string | number;
	diaDeLaSemana: string | DIADESEMANA;
	empiezaVacancia: string;
	terminaVacancia: string;
}

const VacanciasFormulario = (props: FormularioProps) => {

	const {
		lunes, editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales = {
			numeroConsultorio: "",
			idContratoDeAlquiler: "",
			diaDeLaSemana: "",
			empiezaVacancia: "",
			terminaVacancia: "",
		},
		validarVacancia,
	} = props;

	const [contratos, setContratos] = useState<ContratoConNombre[]>([]);
	const [mostrarError, setMostrarError] = useState(false);

	useEffect(() => {
		valoresIniciales.numeroConsultorio !== "" && cargarListaDeContratos(valoresIniciales.diaDeLaSemana as DIADESEMANA, valoresIniciales.numeroConsultorio as number);
	}, [valoresIniciales.numeroConsultorio, valoresIniciales.diaDeLaSemana]);

	const cargarListaDeContratos = (dia: DIADESEMANA, numConsultorio: number) => {
		const index = diasDeSemana.findIndex((e) => e === dia);
		const fecha = new Date(new Date(lunes).setDate(lunes.getDate() + index));
		getContratosPorConsultorio(fecha, numConsultorio)
			.then(({ data }) => setContratos(data));
	};

	return (
		<>
			<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
				<Paper sx={{ p: 2 }}>
					<Formik
						initialValues={valoresIniciales}
						validationSchema={ESQUEMAS.VACANCIAS}
						enableReinitialize
						onSubmit={(
							values: VacanciaDeForm,
							formikHelpers: FormikHelpers<VacanciaDeForm>,
						) => {
							const vacancia: Vacancia = {
								idContratoDeAlquiler: values.idContratoDeAlquiler as number,
								diaDeLaSemana: values.diaDeLaSemana as DIADESEMANA,
								empiezaVacancia: `${values.empiezaVacancia}:00:00`,
								terminaVacancia: `${values.terminaVacancia}:00:00`,
							};
							if (validarVacancia(vacancia, values.numeroConsultorio as number)) {
								enviarFormulario(vacancia, values.numeroConsultorio as number);
								formikHelpers.setSubmitting(false);
								cerrarFormulario();
							} else {
								setMostrarError(true);
								formikHelpers.setSubmitting(false);
							}
						}}
					>
						{(formikProps: FormikProps<VacanciaDeForm>) => {
							return (
								<Form>
									<Grid container spacing={2}>
										<Grid item container xs={12} justifyContent={"center"}>
											<Typography variant={"h4"}>
												{editar ? "Modificar " : "Agregar "}
												Vacancia
											</Typography>
										</Grid>
										<Field
											name="idContratoDeAlquiler"
											label="Contrato de alquiler"
											component={SelectField}
										>
											{contratos.length <= 0 ?
												<MenuItem>
													No se encuentras contratos para esta fecha o consultorio
												</MenuItem> :
												contratos.map(({ id, numeroDeConsultorio, sobrenombre, costoPorModulo }) =>
													<MenuItem key={id} value={id}>
														Contrato: {id}, NºConsultorio: {numeroDeConsultorio},
														Profesional: {sobrenombre}, Costo: {costoPorModulo}
													</MenuItem>)}
										</Field>
										<Field
											name="diaDeLaSemana"
											label="Dia de la semana"
											component={SelectField}
										>
											{diasDeSemana.map((dia, index) =>
												<MenuItem key={index} value={dia}>{dia}</MenuItem>)}
										</Field>
										<Field
											name="empiezaVacancia"
											label="Hora de entrada"
											component={selectField}
										>
											{horas.map((hora, index) => {
												return (<MenuItem key={index} value={hora}>
													{`${hora}:00:00`}
												</MenuItem>);
											})}
										</Field>
										<Field
											name="terminaVacancia"
											label="Hora de Salida"
											component={selectField}
										>
											{horas.map((hora, index) => {
												return (<MenuItem key={index} value={hora}>
													{`${hora}:00:00`}
												</MenuItem>);
											})}
										</Field>
										<Grid item container xs={12} justifyContent={"flex-end"}>
											<Button
												type="submit"
												variant="outlined"
												size="large"
												disabled={formikProps.isSubmitting}
											>
												Introducir
											</Button>
										</Grid>
									</Grid>
								</Form>
							);
						}}
					</Formik>
				</Paper>
			</Dialog>
			<Snackbar
				open={mostrarError}
				onClose={() => setMostrarError(false)}
				autoHideDuration={3000}
			>
				<Alert severity={"error"}>
					Horario ocupado, revise sus datos e intente de nuevo
				</Alert>
			</Snackbar>
		</>
	);
};

export default VacanciasFormulario;