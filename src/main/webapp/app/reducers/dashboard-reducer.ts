import { MovieInsightsContainerState } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';
import movieInsightsGeneralStateManager, { initialMovieInsightsGeneralState } from 'app/reducers/movie-insights-general-state-manager';
import movieInsightsPerCompanyStateManager from 'app/reducers/movie-insights-per-company-state-manager';
import movieInsightsPerCountryStateManager from 'app/reducers/movie-insights-per-country-state-manager';
import movieInsightsPerGenreStateManager from 'app/reducers/movie-insights-per-genre-state-manager';
import movieInsightsPerPersonStateManager from 'app/reducers/movie-insights-per-person-state-manager';
import BaseMovieInsightsContainerStateManager from 'app/reducers/utils/base-movie-insights-container-state-manager';

import { Service } from 'app/service';
import { ICrudGetAllAction } from 'app/utils/redux-action-types';
import { ICountryData } from 'app/models/ICountryData';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';
import { CreditRole } from 'app/models/enumerations';

export const ACTION_TYPES = {
  FETCH_COUNTRY_DATA: 'App/FETCH_COUNTRY_DATA',
  SET_ACTIVE_VIEW: 'App/SET_ACTIVE_VIEW',
  RESET: 'App/RESET',
};

export interface DashboardState {
  activeView: () => MovieInsightsContainerState<any>;
  reducers?: BaseMovieInsightsContainerStateManager<any, any, any>[];
  activeReducer: BaseMovieInsightsContainerStateManager<any, any, any>;
  initialized: boolean;
  loading: boolean;
  errorMessage: string;
  countryData: ICountryData[];
}

const initialState = {
  activeView: null,
};

export const Views = {
  MOVIEINSIGHTS_GENERAL: movieInsightsGeneralStateManager,
  MOVIEINSIGHTS_PER_COMPANY: movieInsightsPerCompanyStateManager,
  MOVIEINSIGHTS_PER_COUNTRY: movieInsightsPerCountryStateManager,
  MOVIEINSIGHTS_PER_PERSON: movieInsightsPerPersonStateManager,
  MOVIEINSIGHTS_PER_GENRE: movieInsightsPerGenreStateManager,
};

function processCountry(cdata: ICountryData): ICountryData {
  return {
    id: `${cdata[0]}`,
    _id: cdata[0],
    iso31661: cdata[2],
    value: cdata[3],
    name: cdata[1],
  };
}

export class DashboardReducer {
  private declare initialState: DashboardState;
  private declare activeReducer: BaseMovieInsightsContainerStateManager<any, any, any>;

  constructor() {}

  init(reducers: BaseMovieInsightsContainerStateManager<any, any, any>[]) {
    this.activeReducer = movieInsightsGeneralStateManager;
    this.initialState = {
      activeView: () => initialMovieInsightsGeneralState,
      activeReducer: movieInsightsGeneralStateManager,
      reducers,
      initialized: false,
      errorMessage: '',
      loading: false,
      countryData: [],
    };

    return this;
  }

  reducer = (state: DashboardState = this.initialState, action): DashboardState => {
    switch (action.type) {
      case REQUEST(ACTION_TYPES.FETCH_COUNTRY_DATA):
        return {
          ...state,
          errorMessage: null,
          loading: true,
        };
      case FAILURE(ACTION_TYPES.FETCH_COUNTRY_DATA):
        return {
          ...state,
          errorMessage: action.payload,
          loading: false,
        };
      case SUCCESS(ACTION_TYPES.FETCH_COUNTRY_DATA):
        const data: ICountryData[] = action.payload.data;
        return {
          ...state,
          errorMessage: null,
          loading: false,
          countryData: data.map(d => {
            return processCountry(d);
          }),
        };
      case ACTION_TYPES.SET_ACTIVE_VIEW:
        const _reducer = state.reducers.filter(r => r === action.payload)[0];
        this.activeReducer = _reducer;
        return {
          ...state,
          initialized: true,
          activeView: _reducer.getState,
          activeReducer: _reducer,
        };
      case ACTION_TYPES.RESET:
        return {
          ...this.initialState,
        };
      default:
        return state;
    }
  };

  fetch = (id?: number | string, role?: CreditRole) => {
    return this.activeReducer.fetch(id, role);
  };

  fetchYear = (year: number, id?: number | string, role?: CreditRole) => {
    return this.activeReducer.fetchYear(year, id, role);
  };

  setActive = (id?: number | string, role?: CreditRole) => {
    return this.activeReducer.setActive(id, role);
  };

  setActiveYear = (year: number, role?: CreditRole) => {
    return this.activeReducer.setActiveYear(year, role);
  };

  reset = () => {
    return this.activeReducer.reset();
  };
}

const reducer = new DashboardReducer();

export default reducer;

export const fetchYear = reducer.fetchYear;
export const fetch = reducer.fetch;
export const setActive = reducer.setActive;
export const setActiveYear = reducer.setActiveYear;
export const clearView = reducer.reset;

export const setActiveView = (viewState: BaseMovieInsightsContainerStateManager<any, any, any>) => ({
  type: ACTION_TYPES.SET_ACTIVE_VIEW,
  payload: viewState,
});

export const fetchCountryData: ICrudGetAllAction<ICountryData[]> = () => ({
  type: ACTION_TYPES.FETCH_COUNTRY_DATA,
  payload: Service.fetchCountryData(),
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
