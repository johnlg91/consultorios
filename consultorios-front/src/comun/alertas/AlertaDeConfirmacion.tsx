import { Button, Dialog, DialogActions, DialogTitle } from "@mui/material";

interface AlertaDeConfirmacionProps {
	abierto: boolean;
	handleCerrar: () => void;
	handleConfirmar: () => void;
	texto?: string;
}

const AlertaDeConfirmacion = ({ abierto, handleCerrar, handleConfirmar, texto }: AlertaDeConfirmacionProps) => {

	return (
		<Dialog
			open={abierto}
			onClose={handleCerrar}
		>
			<DialogTitle>
				{texto ? texto : "¿Esta seguro quiere eliminarlo?"}
			</DialogTitle>
			{/*<DialogContent>*/}
			{/*	<DialogContentText>*/}
			{/*		¿Esta seguro quiere eliminarlo?*/}
			{/*	</DialogContentText>*/}
			{/*</DialogContent>*/}
			<DialogActions>
				<Button onClick={handleCerrar}>No</Button>
				<Button onClick={handleConfirmar} autoFocus>Sí</Button>
			</DialogActions>
		</Dialog>
	);
};

export default AlertaDeConfirmacion;