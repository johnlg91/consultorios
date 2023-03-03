import * as React from "react";
import { Button, Dialog, Grid, Paper, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { FormTextField } from "../../../comun/componentes/FormTextField";
import { deDateToStringYMD, ESQUEMAS } from "../../../comun";
import { Profesional } from "../ProfesionalesAPI";

interface FormularioProps {
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales?: Profesional;
	enviarFormulario: (valores: Profesional) => void;
}

const ProfesionalesFormulario = (props: FormularioProps) => {

	const {
		editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales = {
			dni: null,
			fechaDeSubscripcion: "",
			nombre: "",
			apellido: "",
			eMail: "",
			sobrenombre: "",
			especialidad: "",
			direccion: "",
			telefonoCelular: "",
			notas: "",
		},
	} = props;

	return (
		<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
			<Paper sx={{ p: 2 }}>
				<Formik
					initialValues={valoresIniciales}
					validationSchema={ESQUEMAS.PROFESIONALES}
					enableReinitialize
					onSubmit={(
						values: Profesional,
						formikHelpers: FormikHelpers<Profesional>,
					) => {
						enviarFormulario({...values, fechaDeSubscripcion: deDateToStringYMD(new Date())});
						formikHelpers.setSubmitting(false);
						cerrarFormulario();
					}}
				>
					{(formikProps: FormikProps<Profesional>) => (
						<Form>
							<Grid container spacing={2}>
								<Grid item container xs={12} justifyContent={"center"}>
									<Typography variant={"h4"}>
										{editar ? "Modificar " : "Crear "}
										Profesional
									</Typography>
								</Grid>
								<Field
									name="dni"
									label="DNI"
									component={FormTextField}
								/>
								<Field
									name="eMail"
									label="Email"
									component={FormTextField}
								/>
								<Field
									name="nombre"
									label="Nombre"
									component={FormTextField}
								/>
								<Field
									name="apellido"
									label="Apellido"
									component={FormTextField}
								/>
								<Field
									name="sobrenombre"
									label="Sobrenombre"
									component={FormTextField}
								/>
								<Field
									name="telefonoCelular"
									label="Teléfono celular"
									component={FormTextField}
								/>
								<Field
									name="especialidad"
									label="Especialidad"
									component={FormTextField}
								/>
								<Field
									name="direccion"
									label="Dirección"
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
export default ProfesionalesFormulario;