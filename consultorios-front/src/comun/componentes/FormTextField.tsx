import React from "react";
import { FieldProps, getIn } from "formik";
import { Grid, TextField, TextFieldProps } from "@mui/material";

/**
 * Material TextField Component with Formik Support including Errors.
 * Intended to be specified via the `component` prop in a Formik <Field> or <FastField> component.
 * Material-UI specific props are passed through.
 */
export const FormTextField: React.FC<FieldProps & TextFieldProps> = ({ error, helperText, field, form, ...rest }) => {
	const isTouched = getIn(form.touched, field.name);
	const errorMessage = getIn(form.errors, field.name);

	return (
		<Grid item xs={12}>
			<TextField
				size={"small"}
				variant="outlined"
				fullWidth
				error={error ?? Boolean(isTouched && errorMessage)}
				helperText={helperText ?? ((isTouched && errorMessage) ? errorMessage : undefined)}
				{...rest}
				{...field}
			/>
		</Grid>
	);
};
