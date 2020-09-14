import { BaseMovieInsightsPerYearContainer } from 'app/models/BaseMovieInsights.Model';
import { CreditRole } from 'app/models/enumerations';
import { IPerson, defaultValue as personDefaultValue } from 'app/models/IPerson.Model';
import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';

export interface IMovieInsightsPerPerson extends BaseMovieInsightsPerYearContainer<IPerson> {
  as: CreditRole;
}

export const defaultValue: Readonly<IMovieInsightsPerPerson> = {
  yearData: [],
  id: 0,
  movieInsights: movieInsightsDefaultValue,
  movieInsightsPerYears: [],
  entity: personDefaultValue,
  as: null,
};
