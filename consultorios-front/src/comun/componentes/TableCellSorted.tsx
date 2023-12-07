import TableSortLabel from "@mui/material/TableSortLabel";
import { TableCell } from "@mui/material";

interface TableSortProps {
	label: string,
	field: string,
	sortField: string | null,
	sortDirection: "asc" | "desc",
	handleSort: (property: any) => void,
}

const TableCellSorted = (props: TableSortProps) => {

	const createSortHandler = (property: any) => (event: React.MouseEvent<unknown>) => {
		props.handleSort(property);
	};

	return <TableCell className={"table-cell-titulo"}>
		<TableSortLabel
			className={"table-cell-titulo"}
			active={props.sortField === props.field}
			direction={props.sortField === props.field ? props.sortDirection : "asc"}
			onClick={createSortHandler(props.field)}
		>
			{props.label}
		</TableSortLabel>
	</TableCell>;
};

export default TableCellSorted;