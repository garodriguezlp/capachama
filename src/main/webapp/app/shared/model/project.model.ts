import dayjs from 'dayjs';

export interface IProject {
  id?: number;
  name?: string | null;
  description?: string | null;
  startDate?: string | null;
}

export const defaultValue: Readonly<IProject> = {};
