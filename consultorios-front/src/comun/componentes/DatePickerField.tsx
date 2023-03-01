import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers-pro";
import { FormControl, FormHelperText, Grid, TextField, TextFieldProps } from "@mui/material";
import React from "react";
import { FieldProps, getIn } from "formik";
import { AdapterDayjs } from "@mui/x-date-pickers-pro/AdapterDayjs";
import "dayjs/locale/es";

const DatePickerField: React.FC<FieldProps & TextFieldProps> = ({ error, helperText, field, form, ...rest }) => {
	const isTouched = getIn(form.touched, field.name);
	const errorMessage = getIn(form.errors, field.name);


	return (
		<Grid item xs={12}>
			<FormControl
				error={error ?? Boolean(isTouched && errorMessage)}
			>
				<LocalizationProvider adapterLocale={"es"} dateAdapter={AdapterDayjs}>
					<DatePicker
						inputFormat={"DD/MM/YYYY"}
						value={field.value}
						onChange={(v: Date | null) => form.setFieldValue(field.name, v)}
						renderInput={(params) => <TextField {...params} />}
						label={rest.label}
					/>
				</LocalizationProvider>

				<FormHelperText>
					{helperText ?? ((isTouched && errorMessage) ? errorMessage : undefined)}
				</FormHelperText>
			</FormControl>
		</Grid>
	);
};

export default DatePickerField;