import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductionCompany from './production-company';
import ProductionCompanyDetail from './production-company-detail';
import ProductionCompanyUpdate from './production-company-update';
import ProductionCompanyDeleteDialog from './production-company-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionCompanyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductionCompany} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionCompanyDeleteDialog} />
  </>
);

export default Routes;
