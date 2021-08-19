import dayjs from 'dayjs';

export interface IEmployee {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  hireDate?: string | null;
}

export const defaultValue: Readonly<IEmployee> = {};
