import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieInsightsPerGenre from './movie-insights-per-genre';
import MovieInsightsPerGenreDetail from './movie-insights-per-genre-detail';
import MovieInsightsPerGenreUpdate from './movie-insights-per-genre-update';
import MovieInsightsPerGenreDeleteDialog from './movie-insights-per-genre-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieInsightsPerGenreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieInsightsPerGenreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieInsightsPerGenreDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieInsightsPerGenre} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieInsightsPerGenreDeleteDialog} />
  </>
);

export default Routes;
