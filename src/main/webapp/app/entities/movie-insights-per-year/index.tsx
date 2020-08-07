import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieInsightsPerYear from './movie-insights-per-year';
import MovieInsightsPerYearDetail from './movie-insights-per-year-detail';
import MovieInsightsPerYearUpdate from './movie-insights-per-year-update';
import MovieInsightsPerYearDeleteDialog from './movie-insights-per-year-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieInsightsPerYearUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieInsightsPerYearUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieInsightsPerYearDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieInsightsPerYear} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieInsightsPerYearDeleteDialog} />
  </>
);

export default Routes;
