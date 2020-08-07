import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductionCountry from './production-country';
import ProductionCountryDetail from './production-country-detail';
import ProductionCountryUpdate from './production-country-update';
import ProductionCountryDeleteDialog from './production-country-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionCountryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionCountryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionCountryDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductionCountry} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionCountryDeleteDialog} />
  </>
);

export default Routes;
