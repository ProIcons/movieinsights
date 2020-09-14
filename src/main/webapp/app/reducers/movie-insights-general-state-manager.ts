import { MovieInsightsContainerState } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';
import { defaultValue as movieInsightsGeneralDefaultValue, IMovieInsightsGeneral } from 'app/models/IMovieInsightsGeneral.Model';
import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';
import { Service } from 'app/service';
import BaseMovieInsightsContainerStateManager from 'app/reducers/utils/base-movie-insights-container-state-manager';
import { IRootState } from 'app/shared/reducers';
import { store_ } from '../index';
import { EntityType } from 'app/models/enumerations/EntityType.enum';

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
    return (store_.getState() as IRootState).movieInsightsGeneral;
  };
}

const stateManager = new MovieInsightsGeneralStateManager();
export default stateManager;
