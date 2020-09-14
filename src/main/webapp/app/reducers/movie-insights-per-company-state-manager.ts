import { MovieInsightsContainerState } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';

import { Service } from 'app/service';
import BaseMovieInsightsContainerStateManager from 'app/reducers/utils/base-movie-insights-container-state-manager';
import { store_ } from 'app/index';
import { IRootState } from 'app/shared/reducers';
import { EntityType } from 'app/models/enumerations/EntityType.enum';
import { IMovieInsightsPerCompany, defaultValue as movieInsightsPerCompanyDefaultValue } from 'app/models/IMovieInsightsPerCompany.Model';
import { IProductionCompany } from 'app/models/IProductionCompany.Model';
import { defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';
export type MovieInsightsPerCompanyState = Readonly<MovieInsightsContainerState<IMovieInsightsPerCompany>>;

export const initialMovieInsightsPerCompanyState: MovieInsightsPerCompanyState = {
  activeMovieInsights: movieInsightsDefaultValue,
  activeYearEntity: null,
  yearEntities: [],
  activeEntity: movieInsightsPerCompanyDefaultValue,
  entitiesCache: [],
  errorMessage: '',
  loading: false,
  initialized: false,
  isPerYear: false,
  entityType: EntityType.COMPANY,
};

export class MovieInsightsPerCompanyStateManager extends BaseMovieInsightsContainerStateManager<
  MovieInsightsPerCompanyState,
  IMovieInsightsPerCompany,
  IProductionCompany
> {
  constructor() {
    super(initialMovieInsightsPerCompanyState, 'MovieInsightsPerCompany');
  }

  public getState: () => MovieInsightsPerCompanyState = () => {
    return (store_.getState() as IRootState).movieInsightsPerCompany;
  };

  fetch = (id: number) => ({
    type: this.actionTypes.FETCH,
    payload: Service.getMovieInsightsPerCompany(id),
  });

  fetchYear = (year: number) => ({
    type: this.actionTypes.FETCH_YEAR,
    payload: Service.getMovieInsightsPerCompanyPerYear(this.getState().activeEntity.entity.id, year),
  });
}

const stateManager = new MovieInsightsPerCompanyStateManager();
export default stateManager;
