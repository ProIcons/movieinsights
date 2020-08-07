import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';

export interface IMovieInsightsPerCountry {
  id?: number;
  movieInsightsId?: number;
  movieInsightsPerYears?: IMovieInsightsPerYear[];
  countryId?: number;
}

export const defaultValue: Readonly<IMovieInsightsPerCountry> = {};
