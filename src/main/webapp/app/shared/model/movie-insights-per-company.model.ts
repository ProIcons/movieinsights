import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';

export interface IMovieInsightsPerCompany {
  id?: number;
  movieInsightsId?: number;
  movieInsightsPerYears?: IMovieInsightsPerYear[];
  companyId?: number;
}

export const defaultValue: Readonly<IMovieInsightsPerCompany> = {};
