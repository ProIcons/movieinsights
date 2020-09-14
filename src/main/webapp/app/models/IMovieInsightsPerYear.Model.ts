import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';
import { BaseMovieInsightsContainer } from 'app/models/BaseMovieInsights.Model';

export interface IMovieInsightsPerYear extends BaseMovieInsightsContainer<number> {}

export const defaultValue: Readonly<IMovieInsightsPerYear> = {
  id: 0,
  movieInsights: movieInsightsDefaultValue,
  entity: 0,
};
