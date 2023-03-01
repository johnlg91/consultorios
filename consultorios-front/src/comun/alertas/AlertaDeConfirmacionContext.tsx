import { createContext, FC, ReactNode, useContext, useRef } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText } from "@mui/material";
import useAlertaDeConfirmacion from "./useAlertaDeConfirmacion";

interface AlertaContextInterface {
	mostrarConfirmacion: () => Promise<boolean>,
}

interface AlertaDeConfirmacionContextProviderProps {
	children: ReactNode;
}

const AlertaDeConfirmacionContext = createContext<AlertaContextInterface>({} as AlertaContextInterface);

const AlertaDeCinfirmacionProvider: FC<AlertaDeConfirmacionContextProviderProps> = (props) => {
	const { abrir, setAbrir, alCerrar } = useAlertaDeConfirmacion();
	// eslint-disable-next-line @typescript-eslint/ban-types
	const resolver = useRef<Function>();

	const mostrarAlerta = (): Promise<boolean> => {
		setAbrir(true);
		return new Promise(resolve => {
			resolver.current = resolve;
		});
	};

	const afirmar = () => {
		resolver.current && resolver.current(true);
		alCerrar();
	};

	const cancelar = () => {
		resolver.current && resolver.current(false);
		alCerrar();
	};

	const context: AlertaContextInterface = {
		mostrarConfirmacion: mostrarAlerta,
	};

	return (
		<AlertaDeConfirmacionContext.Provider value={context}>
			{props.children}

			<Dialog open={abrir} onClose={alCerrar}>
				<DialogContent>
					<DialogContentText>
						¿Esta seguro quiere eliminarlo?
					</DialogContentText>
				</DialogContent>
				<DialogActions>
					<Button onClick={cancelar}>No</Button>
					<Button onClick={afirmar} autoFocus>Sí</Button>
				</DialogActions>
			</Dialog>
		</AlertaDeConfirmacionContext.Provider>
	);
};

export const useConfirmationModalContext = (): AlertaContextInterface => useContext(AlertaDeConfirmacionContext);

export default AlertaDeCinfirmacionProvider;