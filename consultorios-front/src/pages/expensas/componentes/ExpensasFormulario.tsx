import { Button, Dialog, Grid, MenuItem, Paper, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { ESQUEMAS } from "../../../comun";
import { FormTextField } from "../../../comun/componentes/FormTextField";
import { Expensa } from "../ExpensasAPI";
import DatePickerField from "../../../comun/componentes/DatePickerField";
import SelectField from "../../../comun/componentes/SelectField";


interface FormularioProps {
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales?: Expensa;
	enviarFormulario: (valores: Expensa) => void;
}

const ExpensasFormulario = (props: FormularioProps) => {

	const {
		editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales = {
			descripcion: "",
			fechaDeExpensa: "",
			cantidad: null,
			seRepite: "NUNCA",
			fechaDePago: "",
		},
	} = props;

	return (
		<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
			<Paper sx={{ p: 2 }}>
				<Formik
					initialValues={valoresIniciales}
					validationSchema={ESQUEMAS.EXPENSAS}
					enableReinitialize
					onSubmit={(
						values: Expensa,
						formikHelpers: FormikHelpers<Expensa>,
					) => {
						enviarFormulario(values);
						formikHelpers.setSubmitting(false);
						cerrarFormulario();
					}}
				>
					{(formikProps: FormikProps<Expensa>) => (
						<Form>
							<Grid container spacing={2}>
								<Grid item container xs={12} justifyContent={"center"}>
									<Typography variant={"h4"}>
										{editar ? "Modificar " : "Crear "}
										Expensa
									</Typography>
								</Grid>
								<Field
									name="descripcion"
									label="Descripción de la expensa"
									component={FormTextField}
								/>
								<Field
									name="fechaDeExpensa"
									label="Fecha de Expensa"
									component={DatePickerField}
								/>
								<Field
									name="cantidad"
									label="Monto a pagar"
									component={FormTextField}
								/>
								<Field
									name="seRepite"
									label="Se repite"
									component={SelectField}
								>
									<MenuItem value={"NUNCA"}>Nunca</MenuItem>
									<MenuItem value={"MENSUALMENTE"}>Mensualmente</MenuItem>
									<MenuItem value={"BIMENSUALMENTE"}>Bimensualmente</MenuItem>
								</Field>
								<Field
									name="fechaDePago"
									label="Fecha de Pago"
									component={DatePickerField}
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

export default ExpensasFormulario;