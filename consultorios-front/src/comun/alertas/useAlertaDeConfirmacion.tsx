import { useState } from "react";

interface UseAlertaDeConfirmacionInterface {
	abrir: boolean;
	setAbrir: (value: boolean) => void;
	alCerrar: () => void;
}

const useAlertaDeConfirmacion = (): UseAlertaDeConfirmacionInterface => {
	const [abrir, setAbrir] = useState(false);

	const handleCerrar = () => {
		setAbrir(false);
	};

	return {
		abrir,
		setAbrir,
		alCerrar: handleCerrar,
	};
};

// interface useAlertaDeConfirmacion {
// 	abierto: boolean;
// 	handleCerrar: () => void;
// 	handleAbrir: () => void;
// 	dialogoTexto: string;
// 	cambiarDialogoTexto: (d: string) => void;
// }
//
// const useAlertaDeConfirmacion = (): useAlertaDeConfirmacion => {
//
// 	const [abierto, setAbierto] = useState(false);
// 	const [dialogoTexto, setDialogoTexto] = useState("¿Esta seguro que quiere eliminarlo?");
//
// 	const handleAbrir = () => {
// 		console.log("abierto");
// 		setAbierto(true);
// 	};
//
// 	const handleCerrar = () => {
// 		console.log("cerrado");
// 		setAbierto(false);
// 	};
//
// 	const cambiarDialogoTexto = useCallback((d: string) => {
// 		setDialogoTexto(d);
// 	}, []);
//
//
// 	return { abierto, dialogoTexto, cambiarDialogoTexto, handleCerrar, handleAbrir };
//
// };


export default useAlertaDeConfirmacion;