import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieInsightsPerCompany from './movie-insights-per-company';
import MovieInsightsPerCompanyDetail from './movie-insights-per-company-detail';
import MovieInsightsPerCompanyUpdate from './movie-insights-per-company-update';
import MovieInsightsPerCompanyDeleteDialog from './movie-insights-per-company-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieInsightsPerCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieInsightsPerCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieInsightsPerCompanyDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieInsightsPerCompany} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieInsightsPerCompanyDeleteDialog} />
  </>
);

export default Routes;
