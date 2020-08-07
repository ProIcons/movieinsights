import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';

export interface IMovieInsightsPerGenre {
  id?: number;
  movieInsightsId?: number;
  movieInsightsPerYears?: IMovieInsightsPerYear[];
  genreId?: number;
}

export const defaultValue: Readonly<IMovieInsightsPerGenre> = {};
