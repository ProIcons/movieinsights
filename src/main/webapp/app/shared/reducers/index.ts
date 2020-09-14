import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/admin/administration.reducer';

// prettier-ignore
import movieInsightsPerCountry, {MovieInsightsPerCountryState} from 'app/reducers/movie-insights-per-country-state-manager';
// prettier-ignore
import movieInsightsPerCompany, {MovieInsightsPerCompanyState} from 'app/reducers/movie-insights-per-company-state-manager';
// prettier-ignore
import movieInsightsPerPerson, {MovieInsightsPerPersonState} from 'app/reducers/movie-insights-per-person-state-manager';
// prettier-ignore
import movieInsightsPerGenre, {MovieInsightsPerGenreState} from 'app/reducers/movie-insights-per-genre-state-manager';
// prettier-ignore
import movieInsightsGeneral, {MovieInsightsGeneralState} from 'app/reducers/movie-insights-general-state-manager';
import dashboardState, { DashboardState } from 'app/reducers/dashboard-reducer';

/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly movieInsightsPerCountry: MovieInsightsPerCountryState;
  readonly movieInsightsPerCompany: MovieInsightsPerCompanyState;
  readonly movieInsightsPerPerson: MovieInsightsPerPersonState;
  readonly movieInsightsPerGenre: MovieInsightsPerGenreState;
  readonly movieInsightsGeneral: MovieInsightsGeneralState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
  readonly dashboardState: DashboardState;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  movieInsightsGeneral: movieInsightsGeneral.reducer,
  movieInsightsPerCountry: movieInsightsPerCountry.reducer,
  movieInsightsPerCompany: movieInsightsPerCompany.reducer,
  movieInsightsPerPerson: movieInsightsPerPerson.reducer,
  movieInsightsPerGenre: movieInsightsPerGenre.reducer,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
  dashboardState: dashboardState.init([
    movieInsightsGeneral,
    movieInsightsPerCompany,
    movieInsightsPerCountry,
    movieInsightsPerPerson,
    movieInsightsPerGenre,
  ]).reducer,
});

export default rootReducer;
