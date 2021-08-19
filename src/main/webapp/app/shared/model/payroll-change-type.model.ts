export interface IPayrollChangeType {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IPayrollChangeType> = {};
