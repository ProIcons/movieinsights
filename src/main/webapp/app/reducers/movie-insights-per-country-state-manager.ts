import { MovieInsightsContainerState } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';
import { Service } from 'app/service';
import BaseMovieInsightsContainerStateManager from 'app/reducers/utils/base-movie-insights-container-state-manager';
import { store } from '../index';
import { IRootState } from 'app/shared/reducers';
import { EntityType } from 'app/models/enumerations';
import { IMovieInsightsPerCountry, IProductionCountry } from 'app/models';
import { movieInsightsDefaultValue, movieInsightsPerCountryDefaultValue } from 'app/models/defaultValues';

export type MovieInsightsPerCountryState = Readonly<MovieInsightsContainerState<IMovieInsightsPerCountry>>;

export const initialMovieInsightsPerCountryState: MovieInsightsPerCountryState = {
  activeMovieInsights: movieInsightsDefaultValue,
  activeYearEntity: null,
  yearEntities: [],
  activeEntity: movieInsightsPerCountryDefaultValue,
  entitiesCache: [],
  errorMessage: '',
  loading: false,
  initialized: false,
  isPerYear: false,
  entityType: EntityType.COUNTRY,
};

export class MovieInsightsPerCountryStateManager extends BaseMovieInsightsContainerStateManager<
  MovieInsightsPerCountryState,
  IMovieInsightsPerCountry,
  IProductionCountry
> {
  constructor() {
    super(initialMovieInsightsPerCountryState, 'MovieInsightsPerCountry');
  }

  public getState: () => MovieInsightsPerCountryState = () => {
    return (store.getState() as IRootState).movieInsightsPerCountry;
  };

  fetch = (iso: string) => ({
    type: this.actionTypes.FETCH,
    payload: Service.getMovieInsightsPerCountry(iso),
  });

  fetchYear = (year: number, id?: number | string) => ({
    type: this.actionTypes.FETCH_YEAR,
    payload: Service.getMovieInsightsPerCountryPerYear(id ? id : this.getState().activeEntity.entity.iso31661, year),
  });
}

const stateManager = new MovieInsightsPerCountryStateManager();
export default stateManager;
