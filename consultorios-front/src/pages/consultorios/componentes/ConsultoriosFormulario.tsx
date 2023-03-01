import { Button, Dialog, Grid, Paper, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { ESQUEMAS } from "../../../comun";
import { FormTextField } from "../../../comun/componentes/FormTextField";
import { Consultorio } from "../ConsultoriosAPI";

interface FormularioProps {
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales?: ConsultorioDeForm;
	enviarFormulario: (valores: Consultorio) => void;
}

interface ConsultorioDeForm {
	numeroDeConsultorio: string | number;
	imagen?: Blob;
	costoPorModulo: string | number;
	tamanioDelArea: string | number;
	equipo: string;
	especialidades: string;
}

const ConsultoriosFormulario = (props: FormularioProps) => {

	const {
		editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales = {
			numeroDeConsultorio: "",
			costoPorModulo: "",
			tamanioDelArea: "",
			equipo: "",
			especialidades: "",
		},
	} = props;

	return (
		<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
			<Paper sx={{ p: 2 }}>
				<Formik
					initialValues={valoresIniciales}
					validationSchema={ESQUEMAS.CONSULTORIOS}
					enableReinitialize
					onSubmit={(
						values: ConsultorioDeForm,
						formikHelpers: FormikHelpers<ConsultorioDeForm>,
					) => {
						enviarFormulario({
							numeroDeConsultorio: values.numeroDeConsultorio as number,
							costoPorModulo: values.costoPorModulo as number,
							tamanioDelArea: values.tamanioDelArea as number,
							equipo: values.equipo,
							especialidades: values.especialidades,
						});
						formikHelpers.setSubmitting(false);
						cerrarFormulario();
					}}
				>
					{(formikProps: FormikProps<ConsultorioDeForm>) => {
						return (
							<Form>
								<Grid container spacing={2}>
									<Grid item container xs={12} justifyContent={"center"}>
										<Typography variant={"h4"}>
											{editar ? "Modificar " : "Crear "}
											Consultorio
										</Typography>
									</Grid>
									<Field
										name="numeroDeConsultorio"
										label="Numero De Consultorio"
										component={FormTextField}
									/>
									<Field
										name="costoPorModulo"
										label="Costo Por Modulo"
										component={FormTextField}
									/>
									<Field
										name="tamanioDelArea"
										label="Tamaño en metros cuadrados"
										component={FormTextField}
									/>
									<Field
										name="equipo"
										label="Equipo"
										component={FormTextField}
									/>
									<Field
										name="especialidades"
										label="Especialidades"
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
						);
					}}
				</Formik>
			</Paper>
		</Dialog>
	);
};

export default ConsultoriosFormulario;