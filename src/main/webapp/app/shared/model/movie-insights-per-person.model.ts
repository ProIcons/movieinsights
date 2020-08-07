import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';
import { CreditRole } from 'app/shared/model/enumerations/credit-role.model';

export interface IMovieInsightsPerPerson {
  id?: number;
  as?: CreditRole;
  movieInsightsId?: number;
  movieInsightsPerYears?: IMovieInsightsPerYear[];
  personId?: number;
}

export const defaultValue: Readonly<IMovieInsightsPerPerson> = {};
