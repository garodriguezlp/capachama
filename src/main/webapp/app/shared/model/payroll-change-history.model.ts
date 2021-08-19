import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';
import { IProject } from 'app/shared/model/project.model';
import { IPayrollChangeType } from 'app/shared/model/payroll-change-type.model';

export interface IPayrollChangeHistory {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  comments?: string | null;
  employee?: IEmployee | null;
  manager?: IEmployee | null;
  project?: IProject | null;
  changeType?: IPayrollChangeType | null;
}

export const defaultValue: Readonly<IPayrollChangeHistory> = {};
