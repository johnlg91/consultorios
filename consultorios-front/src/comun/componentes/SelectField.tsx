import { FieldProps, getIn } from "formik";
import { FormControl, FormHelperText, FormLabel, Grid, Select, TextFieldProps } from "@mui/material";
import React from "react";

const SelectField: React.FC<FieldProps & TextFieldProps> = ({ error, helperText, field, form, ...rest }) => {
	const isTouched = getIn(form.touched, field.name);
	const errorMessage = getIn(form.errors, field.name);

	return (
		<Grid item xs={12}>
			<FormControl fullWidth>
				<FormLabel>
					{rest.label}
				</FormLabel>
				<Select
					size={"small"}
					variant={"outlined"}
					fullWidth
					error={error ?? Boolean(isTouched && errorMessage)}
					label={rest.label}
					defaultValue={""}
					{...field}
				>
					{rest.children}
				</Select>
				<FormHelperText>
					{helperText ?? ((isTouched && errorMessage) ? errorMessage : undefined)}
				</FormHelperText>
			</FormControl>
		</Grid>
	);
};

export default SelectField;