import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PayrollChangeHistory from './payroll-change-history';
import PayrollChangeHistoryDetail from './payroll-change-history-detail';
import PayrollChangeHistoryUpdate from './payroll-change-history-update';
import PayrollChangeHistoryDeleteDialog from './payroll-change-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayrollChangeHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayrollChangeHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayrollChangeHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={PayrollChangeHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayrollChangeHistoryDeleteDialog} />
  </>
);

export default Routes;
