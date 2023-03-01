import { useState } from "react";
import { IconButton, TableCell } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";

interface ContraseniaProps {
	contrasenia: string;
}

const Contrasenia = ({ contrasenia }: ContraseniaProps) => {
	const [esconder, setEsconder] = useState(true);

	return (
		<TableCell className={"table-cell"}>
			{esconder ? "********" : contrasenia}
			<IconButton aria-label="delete" color={"primary"} onClick={() => setEsconder(!esconder)}>
				{esconder ? <Visibility /> : <VisibilityOff />}
			</IconButton>
		</TableCell>
	);
};

export default Contrasenia;