import { Button, Grid } from "@mui/material";

interface TabProps {
	titulo: string;
	onClick: () => void;
	selected?: boolean;
}

const Tab = ({ titulo, onClick, selected }: TabProps) => {
	return (
		<Grid item className={"Tab"} bgcolor={selected ? "#0c3454" : "#C7D9FD"}>
			<Button onClick={onClick}>
				{titulo}
			</Button>
		</Grid>
	);
};

export default Tab;
