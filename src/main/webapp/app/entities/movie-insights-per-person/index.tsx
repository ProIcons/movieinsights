import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieInsightsPerPerson from './movie-insights-per-person';
import MovieInsightsPerPersonDetail from './movie-insights-per-person-detail';
import MovieInsightsPerPersonUpdate from './movie-insights-per-person-update';
import MovieInsightsPerPersonDeleteDialog from './movie-insights-per-person-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieInsightsPerPersonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieInsightsPerPersonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieInsightsPerPersonDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieInsightsPerPerson} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieInsightsPerPersonDeleteDialog} />
  </>
);

export default Routes;
