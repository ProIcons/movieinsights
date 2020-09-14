import { BaseMovieInsightsPerYearContainer, MovieInsightsEntityType } from 'app/models/BaseMovieInsights.Model';
import { EntityType } from 'app/models/enumerations/EntityType.enum';
import { IPersonMultiView } from 'app/models/IPersonMultiView';
import { IMovieInsights } from 'app/models/IMovieInsights.Model';
import { IMovieInsightsPerYear } from 'app/models/IMovieInsightsPerYear.Model';
import { CreditRole } from 'app/models/enumerations';

export type MovieInsightsContainerType<T extends MovieInsightsEntityType> = BaseMovieInsightsPerYearContainer<T> | IPersonMultiView;

export interface MovieInsightsState<T extends MovieInsightsContainerType<any>> {
  initialized: boolean;
  loading: boolean;
  errorMessage: string;
  activeEntity: T;
  activeMovieInsights: IMovieInsights;
  entitiesCache: T[];
  entityType: EntityType;
}

export interface MovieInsightsContainerState<E extends MovieInsightsContainerType<any>> extends MovieInsightsState<E> {
  activeYearEntity: IMovieInsightsPerYear;
  yearEntities: YearDataPerId[];
  isPerYear: boolean;
}

export interface YearDataPerRole {
  role: CreditRole;
  data: IMovieInsightsPerYear;
}
export interface YearDataPerYear {
  year: number;
  roleData?: YearDataPerRole[];
  data?: IMovieInsightsPerYear;
}

export interface YearDataPerId {
  id: number;
  yearData: YearDataPerYear[];
}
export default interface ActionType {
  type: string;
  payload?: any;
}
