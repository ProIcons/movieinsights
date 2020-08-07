import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieInsights from './movie-insights';
import MovieInsightsDetail from './movie-insights-detail';
import MovieInsightsUpdate from './movie-insights-update';
import MovieInsightsDeleteDialog from './movie-insights-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieInsightsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieInsightsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieInsightsDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieInsights} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieInsightsDeleteDialog} />
  </>
);

export default Routes;
