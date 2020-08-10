import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Movie from './movie';
import Person from './person';
import Credit from './credit';
import BannedEntity from './banned-entity';
import Genre from './genre';
import Vote from './vote';
import ProductionCountry from './production-country';
import ProductionCompany from './production-company';
import MovieInsights from './movie-insights';
import MovieInsightsPerCountry from './movie-insights-per-country';
import MovieInsightsPerCompany from './movie-insights-per-company';
import MovieInsightsPerPerson from './movie-insights-per-person';
import MovieInsightsPerGenre from './movie-insights-per-genre';
import MovieInsightsPerYear from './movie-insights-per-year';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}movie`} component={Movie} />
      <ErrorBoundaryRoute path={`${match.url}person`} component={Person} />
      <ErrorBoundaryRoute path={`${match.url}credit`} component={Credit} />
      <ErrorBoundaryRoute path={`${match.url}banned-entity`} component={BannedEntity} />
      <ErrorBoundaryRoute path={`${match.url}genre`} component={Genre} />
      <ErrorBoundaryRoute path={`${match.url}vote`} component={Vote} />
      <ErrorBoundaryRoute path={`${match.url}production-country`} component={ProductionCountry} />
      <ErrorBoundaryRoute path={`${match.url}production-company`} component={ProductionCompany} />
      <ErrorBoundaryRoute path={`${match.url}movie-insights`} component={MovieInsights} />
      <ErrorBoundaryRoute path={`${match.url}movie-insights-per-country`} component={MovieInsightsPerCountry} />
      <ErrorBoundaryRoute path={`${match.url}movie-insights-per-company`} component={MovieInsightsPerCompany} />
      <ErrorBoundaryRoute path={`${match.url}movie-insights-per-person`} component={MovieInsightsPerPerson} />
      <ErrorBoundaryRoute path={`${match.url}movie-insights-per-genre`} component={MovieInsightsPerGenre} />
      <ErrorBoundaryRoute path={`${match.url}movie-insights-per-year`} component={MovieInsightsPerYear} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
