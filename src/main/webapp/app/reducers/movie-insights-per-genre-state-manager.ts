import { MovieInsightsContainerState } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';
import { Service } from 'app/service';
import BaseMovieInsightsContainerStateManager from 'app/reducers/utils/base-movie-insights-container-state-manager';
import { store } from 'app/index';
import { IRootState } from 'app/shared/reducers';
import { EntityType } from 'app/models/enumerations';
import { IGenre, IMovieInsightsPerGenre } from 'app/models';
import { movieInsightsDefaultValue, movieInsightsPerGenreDefaultValue } from 'app/models/defaultValues';

export type MovieInsightsPerGenreState = Readonly<MovieInsightsContainerState<IMovieInsightsPerGenre>>;

export const initialMovieInsightsPerGenreState: MovieInsightsPerGenreState = {
  activeMovieInsights: movieInsightsDefaultValue,
  activeYearEntity: null,
  yearEntities: [],
  activeEntity: movieInsightsPerGenreDefaultValue,
  entitiesCache: [],
  errorMessage: '',
  loading: false,
  initialized: false,
  isPerYear: false,
  entityType: EntityType.GENRE,
};

export class MovieInsightsPerGenreStateManager extends BaseMovieInsightsContainerStateManager<
  MovieInsightsPerGenreState,
  IMovieInsightsPerGenre,
  IGenre
> {
  constructor() {
    super(initialMovieInsightsPerGenreState, 'MovieInsightsPerGenre');
  }

  public getState: () => MovieInsightsPerGenreState = () => {
    return (store.getState() as IRootState).movieInsightsPerGenre;
  };

  fetch = (id: number) => ({
    type: this.actionTypes.FETCH,
    payload: Service.getMovieInsightsPerGenre(id),
  });

  fetchYear = (year: number, id?: number) => ({
    type: this.actionTypes.FETCH_YEAR,
    payload: Service.getMovieInsightsPerGenrePerYear(id ? id : this.getState().activeEntity.entity.id, year),
  });
}

const stateManager = new MovieInsightsPerGenreStateManager();
export default stateManager;
