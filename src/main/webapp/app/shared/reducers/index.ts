import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import movie, {
  MovieState
} from 'app/entities/movie/movie.reducer';
// prettier-ignore
import person, {
  PersonState
} from 'app/entities/person/person.reducer';
// prettier-ignore
import credit, {
  CreditState
} from 'app/entities/credit/credit.reducer';
// prettier-ignore
import bannedEntity, {
  BannedEntityState
} from 'app/entities/banned-entity/banned-entity.reducer';
// prettier-ignore
import genre, {
  GenreState
} from 'app/entities/genre/genre.reducer';
// prettier-ignore
import vote, {
  VoteState
} from 'app/entities/vote/vote.reducer';
// prettier-ignore
import productionCountry, {
  ProductionCountryState
} from 'app/entities/production-country/production-country.reducer';
// prettier-ignore
import productionCompany, {
  ProductionCompanyState
} from 'app/entities/production-company/production-company.reducer';
// prettier-ignore
import movieInsights, {
  MovieInsightsState
} from 'app/entities/movie-insights/movie-insights.reducer';
// prettier-ignore
import movieInsightsPerCountry, {
  MovieInsightsPerCountryState
} from 'app/entities/movie-insights-per-country/movie-insights-per-country.reducer';
// prettier-ignore
import movieInsightsPerCompany, {
  MovieInsightsPerCompanyState
} from 'app/entities/movie-insights-per-company/movie-insights-per-company.reducer';
// prettier-ignore
import movieInsightsPerPerson, {
  MovieInsightsPerPersonState
} from 'app/entities/movie-insights-per-person/movie-insights-per-person.reducer';
// prettier-ignore
import movieInsightsPerGenre, {
  MovieInsightsPerGenreState
} from 'app/entities/movie-insights-per-genre/movie-insights-per-genre.reducer';
// prettier-ignore
import movieInsightsPerYear, {
  MovieInsightsPerYearState
} from 'app/entities/movie-insights-per-year/movie-insights-per-year.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly movie: MovieState;
  readonly person: PersonState;
  readonly credit: CreditState;
  readonly bannedEntity: BannedEntityState;
  readonly genre: GenreState;
  readonly vote: VoteState;
  readonly productionCountry: ProductionCountryState;
  readonly productionCompany: ProductionCompanyState;
  readonly movieInsights: MovieInsightsState;
  readonly movieInsightsPerCountry: MovieInsightsPerCountryState;
  readonly movieInsightsPerCompany: MovieInsightsPerCompanyState;
  readonly movieInsightsPerPerson: MovieInsightsPerPersonState;
  readonly movieInsightsPerGenre: MovieInsightsPerGenreState;
  readonly movieInsightsPerYear: MovieInsightsPerYearState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  movie,
  person,
  credit,
  bannedEntity,
  genre,
  vote,
  productionCountry,
  productionCompany,
  movieInsights,
  movieInsightsPerCountry,
  movieInsightsPerCompany,
  movieInsightsPerPerson,
  movieInsightsPerGenre,
  movieInsightsPerYear,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
