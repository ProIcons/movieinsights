import { BaseMovieInsightsPerYearContainer } from 'app/models/BaseMovieInsights.Model';
import { IProductionCountry, defaultValue as countryDefaultValue } from 'app/models/IProductionCountry.Model';
import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';

export interface IMovieInsightsPerCountry extends BaseMovieInsightsPerYearContainer<IProductionCountry> {}

export const defaultValue: Readonly<IMovieInsightsPerCountry> = {
  yearData: [],
  id: 0,
  movieInsights: movieInsightsDefaultValue,
  movieInsightsPerYears: [],
  entity: countryDefaultValue,
};
