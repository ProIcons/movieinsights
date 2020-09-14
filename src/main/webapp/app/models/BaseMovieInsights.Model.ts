import { BaseEntity, BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { IMovieInsights } from 'app/models/IMovieInsights.Model';
import { IMovieInsightsPerYear } from 'app/models/IMovieInsightsPerYear.Model';

export type MovieInsightsEntityType = BaseEntity | number | undefined;
export type MovieInsightsContainerType<T extends MovieInsightsEntityType> = BaseMovieInsightsPerYearContainer<T>;
export interface BaseMovieInsightsContainer<T extends MovieInsightsEntityType> extends BaseEntity {
  entity?: T;
  movieInsights: IMovieInsights;
}

export interface BaseMovieInsightsPerYearContainer<T extends MovieInsightsEntityType> extends BaseMovieInsightsContainer<T> {
  yearData: number[][];
  movieInsightsPerYears: IMovieInsightsPerYear[];
}
