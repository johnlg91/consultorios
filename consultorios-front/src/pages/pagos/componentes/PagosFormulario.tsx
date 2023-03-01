import { Button, Dialog, Grid, Paper, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { ESQUEMAS } from "../../../comun";
import { FormTextField } from "../../../comun/componentes/FormTextField";
import { Pago } from "../PagosAPI";

interface FormularioProps {
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales?: PagosDeForm;
	enviarFormulario: (valores: Pago) => void;
}

interface PagosDeForm {
	idProfesional: number | string;
	fechaDeTransaccion: Date | string;
	tipo: string;
	metodoDePago: string;
	cantidad: number | string;
}

const PagosFormulario = (props: FormularioProps) => {

	const {
		editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales = {
			idProfesional: "",
			fechaDeTransaccion: "",
			tipo: "",
			metodoDePago: "",
			cantidad: "",
		},
	} = props;

	return (
		<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
			<Paper sx={{ p: 2 }}>
				<Formik
					initialValues={valoresIniciales}
					validationSchema={ESQUEMAS.PAGOS}
					enableReinitialize
					onSubmit={(
						values: PagosDeForm,
						formikHelpers: FormikHelpers<PagosDeForm>,
					) => {
						enviarFormulario({
							idContratoDeAlquiler: values.idProfesional as number,
							fechaDeTransaccion: values.fechaDeTransaccion as Date,
							tipo: values.tipo,
							metodoDePago: values.metodoDePago,
							cantidad: values.cantidad as number,
						});
						formikHelpers.setSubmitting(false);
						cerrarFormulario();
					}}
				>
					{(formikProps: FormikProps<PagosDeForm>) => {
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

export default PagosFormulario;