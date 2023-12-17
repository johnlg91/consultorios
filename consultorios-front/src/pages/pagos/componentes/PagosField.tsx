import { Button, IconButton, TableCell, TextField, Tooltip } from "@mui/material";
import { Add } from "@mui/icons-material";
import { ChangeEvent, useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";

interface PagosFieldProps {
	handlePago: (idContrato: number, cantidad: number) => void;
	idContrato: number;
	alBorrar: () => void;
}

const PagosField = ({ handlePago, idContrato, alBorrar }: PagosFieldProps) => {

	const [cantidad, setCantidad] = useState<number>(0);

	const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
		const value = event.target.value;
		const parsedNumber = parseInt(value, 10); // or parseFloat for decimal numbers

		if (!isNaN(parsedNumber)) {
			setCantidad(parsedNumber);
		} else {
			// Handle the error, or reset to default
			setCantidad(0); // or any other default or error handling
		}
	};

	function handleOnClick() {
		handlePago(idContrato, cantidad);
	}

	return (
		<TableCell className={"table-cell"} sx={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
			<TextField
				size={"small"} type={"number"} sx={{ bgcolor: "white" }}
				variant={"filled"} label={"Introduzca el monto"}
				onChange={handleChange} />
			<Button
				variant={"outlined"} size={"small"} startIcon={<Add />}
				sx={{ margin: 1.5, padding: 1, color: "white" }}
				onClick={handleOnClick}>
				Modificar saldo
			</Button>
			<Tooltip title={"Borrar campo"} disableInteractive>
				<IconButton aria-label="delete" color={"primary"} onClick={() => alBorrar()}>
					<DeleteIcon />
				</IconButton>
			</Tooltip>
		</TableCell>
	);
};

export default PagosField;
