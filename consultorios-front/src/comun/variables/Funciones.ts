export const deDateToStringYMD = (d: Date | string): string => {
	if (d instanceof Date) return d.toISOString().substring(0, 10);
	else return d;
};
