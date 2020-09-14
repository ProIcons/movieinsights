import { BaseMovieInsightsPerYearContainer } from 'app/models/BaseMovieInsights.Model';
import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';

export type IMovieInsightsGeneral = BaseMovieInsightsPerYearContainer<never>;

export const defaultValue: Readonly<IMovieInsightsGeneral> = {
  movieInsightsPerYears: [],
  yearData: [],
  id: 0,
  movieInsights: movieInsightsDefaultValue,
};
