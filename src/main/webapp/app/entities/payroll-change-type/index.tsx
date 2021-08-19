import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PayrollChangeType from './payroll-change-type';
import PayrollChangeTypeDetail from './payroll-change-type-detail';
import PayrollChangeTypeUpdate from './payroll-change-type-update';
import PayrollChangeTypeDeleteDialog from './payroll-change-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayrollChangeTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayrollChangeTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayrollChangeTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={PayrollChangeType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayrollChangeTypeDeleteDialog} />
  </>
);

export default Routes;
