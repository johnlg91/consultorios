import { IconButton, TableCell, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { Edit } from "@mui/icons-material";

interface AccionesProps {
	alBorrar: () => void;
	alEditar: () => void;
}

const Acciones = ({ alBorrar, alEditar }: AccionesProps) => {
	return (
		<TableCell className={"table-cell"}>
			<Tooltip title={"Editar campo"} disableInteractive>
				<IconButton aria-label="edit" color={"primary"} onClick={() => alEditar()}>
					<Edit />
				</IconButton>
			</Tooltip>
			<Tooltip title={"Borrar campo"} disableInteractive>
				<IconButton aria-label="delete" color={"primary"} onClick={() => alBorrar()}>
					<DeleteIcon />
				</IconButton>
			</Tooltip>
		</TableCell>
	);
};

export default Acciones;