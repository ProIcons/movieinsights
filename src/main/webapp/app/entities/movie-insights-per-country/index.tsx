import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieInsightsPerCountry from './movie-insights-per-country';
import MovieInsightsPerCountryDetail from './movie-insights-per-country-detail';
import MovieInsightsPerCountryUpdate from './movie-insights-per-country-update';
import MovieInsightsPerCountryDeleteDialog from './movie-insights-per-country-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieInsightsPerCountryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieInsightsPerCountryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieInsightsPerCountryDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieInsightsPerCountry} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieInsightsPerCountryDeleteDialog} />
  </>
);

export default Routes;
