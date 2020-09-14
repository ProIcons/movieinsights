import { BaseMovieInsightsPerYearContainer } from 'app/models/BaseMovieInsights.Model';
import { IProductionCompany, defaultValue as companyDefaultValue } from 'app/models/IProductionCompany.Model';
import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';

export interface IMovieInsightsPerCompany extends BaseMovieInsightsPerYearContainer<IProductionCompany> {}

export const defaultValue: Readonly<IMovieInsightsPerCompany> = {
  id: 0,
  movieInsights: movieInsightsDefaultValue,
  movieInsightsPerYears: [],
  yearData: [],
  entity: companyDefaultValue,
};
