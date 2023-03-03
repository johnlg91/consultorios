import { Button, Dialog, Grid, MenuItem, Paper, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { ESQUEMAS } from "../../../comun";
import { FormTextField } from "../../../comun/componentes/FormTextField";
import SelectField from "../../../comun/componentes/SelectField";
import React, { useEffect, useState } from "react";
import { Consultorio, getConsultorios } from "../../consultorios/ConsultoriosAPI";
import { getProfesionales, Profesional } from "../../profesionales/ProfesionalesAPI";
import DatePickerField from "../../../comun/componentes/DatePickerField";
import { Contrato } from "../ContratosAPI";

interface FormularioProps {
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales: Contrato;
	enviarFormulario: (valores: Contrato) => void;
}

const ContratosFormulario = (props: FormularioProps) => {

	const {
		editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales,
	} = props;

	const [consultorios, setConsultorios] = useState<Consultorio[]>([]);
	const [profesionales, setProfesionales] = useState<Profesional[]>([]);

	useEffect(() => {
		getConsultorios().then(({ data }) => setConsultorios(data.sort((c1, c2) => c1.numeroDeConsultorio - c2.numeroDeConsultorio)));
		//TDOD: sortear estos
		getProfesionales().then(({ data }) => setProfesionales(data));
	}, []);

	return (
		<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
			<Paper sx={{ p: 2 }}>
				<Formik<Contrato>
					initialValues={valoresIniciales}
					validationSchema={ESQUEMAS.CONTRATOS}
					enableReinitialize
					onSubmit={(
						values: Contrato,
						formikHelpers: FormikHelpers<Contrato>,
					) => {
						enviarFormulario(values);
						formikHelpers.setSubmitting(false);
						cerrarFormulario();
					}}
				>
					{(formikProps: FormikProps<Contrato>) => (
						<Form>
							<Grid container spacing={2}>
								<Grid item container xs={12} justifyContent={"center"}>
									<Typography variant={"h4"}>
										{editar ? "Modificar " : "Crear "}
										Contrato
									</Typography>
								</Grid>
								<Field
									name="idConsultorio"
									label="Numero De Consultorio"
									component={SelectField}
								>
									{!consultorios ? <Typography>Cargando...</Typography> :
										consultorios?.map((consultorio) =>
											<MenuItem key={consultorio.id} value={consultorio.id}>
												{consultorio.numeroDeConsultorio}
											</MenuItem>)}
									<MenuItem value={-1}>-</MenuItem>
								</Field>
								<Field
									name="idProfesional"
									label="Profesional"
									component={SelectField}
								>
									{!profesionales ? <Typography>Cargando...</Typography> :
										profesionales?.map((profesional) =>
											<MenuItem key={profesional.id} value={profesional.id}>
												{profesional.nombre} {profesional.apellido}
											</MenuItem>)}
									<MenuItem value={-1}>-</MenuItem>
								</Field>
								<Field
									name="tipoDeAlquiler"
									label={"Tipo de alquiler"}
									values
									component={SelectField}
								>
									<MenuItem value={"NORMAL"}>Normal</MenuItem>
									<MenuItem value={"EXCEPCION"}>Excepcion</MenuItem>
								</Field>
								<Field
									component={DatePickerField}
									name="inicioDelContratoDeAlquiler"
									label={"Inicio Del Contrato"}
									textField={{ helperText: "Helper text", variant: "filled" }}
								/>
								<Field
									component={DatePickerField}
									name="finDelContrato"
									label={"Fin del contrato"}
									textField={{ helperText: "Helper text", variant: "filled" }}
								/>
								<Field
									name="costoTotal"
									label="Costo"
									component={FormTextField}
								/>
								<Field
									name="notas"
									label="Notas"
									component={FormTextField}
								/>
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
					)}
				</Formik>
			</Paper>
		</Dialog>
	);
};

export default ContratosFormulario;