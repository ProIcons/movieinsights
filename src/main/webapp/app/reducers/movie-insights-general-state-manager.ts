import { MovieInsightsContainerState } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';
import { Service } from 'app/service';
import BaseMovieInsightsContainerStateManager from 'app/reducers/utils/base-movie-insights-container-state-manager';
import { IRootState } from 'app/shared/reducers';
import { store } from '../index';
import { EntityType } from 'app/models/enumerations';
import { IMovieInsightsGeneral } from 'app/models';
import { movieInsightsDefaultValue, movieInsightsGeneralDefaultValue } from 'app/models/defaultValues';

export type MovieInsightsGeneralState = Readonly<MovieInsightsContainerState<IMovieInsightsGeneral>>;

export const initialMovieInsightsGeneralState: MovieInsightsContainerState<IMovieInsightsGeneral> = {
  activeMovieInsights: movieInsightsDefaultValue,
  activeYearEntity: null,
  yearEntities: [],
  activeEntity: movieInsightsGeneralDefaultValue,
  entitiesCache: [],
  errorMessage: '',
  loading: false,
  initialized: false,
  isPerYear: false,
  entityType: EntityType.GENERAL,
};

export class MovieInsightsGeneralStateManager extends BaseMovieInsightsContainerStateManager<
  MovieInsightsGeneralState,
  IMovieInsightsGeneral,
  undefined
> {
  constructor() {
    super(initialMovieInsightsGeneralState, 'MovieInsightsGeneral');
  }

  fetch = () => ({
    type: this.actionTypes.FETCH,
    payload: Service.getMovieInsightsGeneral(),
  });

  fetchYear = (year: number) => ({
    type: this.actionTypes.FETCH_YEAR,
    payload: Service.getMovieInsightsGeneralPerYear(year),
  });

  public getState: () => MovieInsightsGeneralState = () => {
    return (store.getState() as IRootState).movieInsightsGeneral;
  };
}

const stateManager = new MovieInsightsGeneralStateManager();
export default stateManager;
