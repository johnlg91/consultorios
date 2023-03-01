import { Button, Checkbox, Dialog, Grid, Paper, Typography } from "@mui/material";
import { Field, Form, Formik, FormikHelpers, FormikProps } from "formik";
import { ESQUEMAS } from "../../../comun";
import { FormTextField } from "../../../comun/componentes/FormTextField";
import { Usuario } from "../UsuariosAPI";

interface FormularioProps {
	abrirFormulario: boolean;
	editar: boolean;
	cerrarFormulario: () => void;
	valoresIniciales?: UsuarioDeForm;
	enviarFormulario: (valores: Usuario) => void;
}

interface UsuarioDeForm {
	dni: number | string;
	nombreUsuario: number | string;
	email: string;
	contrasennia: string;
	esAdmin: boolean | string;
}

const UsuariosFormulario = (props: FormularioProps) => {

	const {
		editar, enviarFormulario, abrirFormulario, cerrarFormulario, valoresIniciales = {
			dni: "",
			nombreUsuario: "",
			email: "",
			contrasennia: "",
			confirmarContrasennia: "",
			esAdmin: "",
		},
	} = props;

	return (
		<Dialog open={abrirFormulario} onClose={cerrarFormulario}>
			<Paper sx={{ p: 2 }}>
				<Formik
					initialValues={{ ...valoresIniciales, contrasennia: "" }}
					validationSchema={ESQUEMAS.USUARIOS}
					enableReinitialize
					onSubmit={(
						values: UsuarioDeForm,
						formikHelpers: FormikHelpers<UsuarioDeForm>,
					) => {
						enviarFormulario({
							dni: values.dni as number,
							nombreUsuario: values.nombreUsuario as number,
							email: values.email,
							contrasennia: values.contrasennia,
							esAdmin: values.esAdmin as boolean,
						});
						formikHelpers.setSubmitting(false);
						cerrarFormulario();
					}}
				>
					{(formikProps: FormikProps<UsuarioDeForm>) => {
						return (
							<Form>
								<Grid container spacing={2}>
									<Grid item container xs={12} justifyContent={"center"}>
										<Typography variant={"h4"}>
											{editar ? "Modificar " : "Crear "}
											Usuario
										</Typography>
									</Grid>
									<Field
										name="dni"
										label="DNI"
										component={FormTextField}
									/>
									<Field
										name="nombreUsuario"
										label="Nombre de usuario"
										component={FormTextField}
									/>
									<Field
										name="email"
										label="Email"
										component={FormTextField}
									/>
									<Field
										name="contrasennia"
										label="Contraseña"
										component={FormTextField}
										type="password"
									/>
									<Field
										name="confirmarContrasennia"
										label="Confirmar contraseña"
										component={FormTextField}
										type="password"
									/>
									<Grid item container xs={12} justifyContent={"flex-start"} alignItems={"center"}>
										<Checkbox name="esAdmin"
												  onClick={() => formikProps.setFieldValue("esAdmin", !formikProps.values.esAdmin)}
												  value={formikProps.values.esAdmin} />
										<Typography>
											Permisos de administrador
										</Typography>
									</Grid>
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

export default UsuariosFormulario;