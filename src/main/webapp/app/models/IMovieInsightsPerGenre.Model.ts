import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';
import { BaseMovieInsightsPerYearContainer } from 'app/models/BaseMovieInsights.Model';
import { IGenre, defaultValue as genreDefaultValue } from 'app/models/IGenre.Model';

export interface IMovieInsightsPerGenre extends BaseMovieInsightsPerYearContainer<IGenre> {}

export const defaultValue: Readonly<IMovieInsightsPerGenre> = {
  yearData: [],
  id: 0,
  movieInsights: movieInsightsDefaultValue,
  movieInsightsPerYears: [],
  entity: genreDefaultValue,
};
