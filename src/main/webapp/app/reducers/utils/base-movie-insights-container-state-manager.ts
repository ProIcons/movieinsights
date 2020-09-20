import { MovieInsightsContainerState, YearDataPerId } from 'app/reducers/utils/base-movie-insights-container-state-manager.models';

import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';
import { IMovieInsightsContainerGetAction, IMovieInsightsContainerGetYearAction } from 'app/utils/redux-action-types';
import _ from 'lodash';
import { AxiosResponse } from 'axios';
import {
  BaseEntity,
  BaseMovieInsightsContainer,
  BaseMovieInsightsPerYearContainer,
  IMovieInsightsPerYear,
  IPersonMultiView,
  MovieInsightsContainerType,
  MovieInsightsEntityType,
} from 'app/models';
import { CreditRole } from 'app/models/enumerations';

export type ActionTypes = {
  FETCH: string;
  FETCH_YEAR: string;
  SET_ACTIVE_YEAR: string;
  SET_ACTIVE: string;
  RESET: string;
};

export type OnStateType<TStateType, TEntity> = (state: TStateType, payload: TEntity) => TStateType;

const getMovieInsightsPerYear = (yearEntities: YearDataPerId[], obj: MovieInsightsContainerType<any>, year: number) => {
  const id = obj.entity ? (obj.entity as BaseEntity).id : 0;
  if (yearEntities.length > 0) {
    return yearEntities.filter(yearData => yearData.id === id)[0].yearData.filter(data => data.year === year)[0].data;
  }
  return undefined;
};
const initializeYearArray = (_yearEntities: YearDataPerId[], obj: MovieInsightsContainerType<any>) => {
  const yearEntities = _.cloneDeep(_yearEntities);
  const id = obj.entity ? (obj.entity as BaseEntity).id : 0;

  if (yearEntities.filter(yearData => yearData.id === id).length === 0) {
    yearEntities.push({
      id,
      yearData: [],
    });
  }
  return yearEntities;
};

const addYear = (yearEntities: YearDataPerId[], obj, yearEntity: IMovieInsightsPerYear) => {
  const id = obj.entity ? obj.entity.id : 0;
  yearEntities
    .filter(yearData => yearData.id === id)[0]
    .yearData.push({
      year: yearEntity.entity,
      data: yearEntity,
    });
  return yearEntities;
};

export default abstract class BaseMovieInsightsContainerStateManager<
  TStateType extends MovieInsightsContainerState<TContainer>,
  TContainer extends MovieInsightsContainerType<TEntity>,
  TEntity extends MovieInsightsEntityType
> {
  public readonly initialState: TStateType;
  protected readonly entityName: string;
  protected readonly actionTypes: ActionTypes;

  protected constructor(initialState: TStateType, entityName: string) {
    this.initialState = initialState;
    this.entityName = entityName;
    this.actionTypes = {
      FETCH: `${entityName}/FETCH`,
      FETCH_YEAR: `${entityName}/FETCH_YEAR`,
      SET_ACTIVE_YEAR: `${entityName}/SET_ACTIVE_YEAR`,
      SET_ACTIVE: `${entityName}/SET_ACTIVE`,
      RESET: `${entityName}/RESET`,
    };
  }

  abstract getState: () => TStateType;

  public readonly reducer = (state: TStateType = this.initialState, action): TStateType => {
    let _state = state;

    _state = this.preProcess(_state, action);
    _state = this._reducer(_state, action);
    _state = this.postProcess(_state, action);

    return _state;
  };
  protected preProcess: (s: TStateType, a: any) => TStateType = (state: TStateType = this.initialState): TStateType => {
    return state;
  };
  protected postProcess: (s: TStateType, a: any) => TStateType = (state: TStateType = this.initialState): TStateType => {
    return state;
  };

  public hasYear = (year: number) => {
    const entity = (this.getState().activeEntity as BaseMovieInsightsPerYearContainer<TEntity>).entity as BaseEntity;
    const id = entity ? entity.id : 0;

    const yearEntities = this.getState().yearEntities;
    const yearDataPerId = yearEntities.filter(yearData => yearData.id === id)[0];
    if (!yearDataPerId) return false;

    const yearDataPerYear = yearDataPerId.yearData.filter(data => data.year === year)[0];
    return !!yearDataPerYear;
  };

  public readonly _reducer = (state: TStateType = this.initialState, action): TStateType => {
    switch (action.type) {
      case REQUEST(this.actionTypes.FETCH_YEAR):
      case REQUEST(this.actionTypes.FETCH):
        return {
          ...state,
          errorMessage: null,
          loading: true,
          activeMovieInsights: this.initialState.activeMovieInsights,
          isPerYear: false,
        };
      case FAILURE(this.actionTypes.FETCH_YEAR):
      case FAILURE(this.actionTypes.FETCH):
        return {
          ...state,
          errorMessage: action.payload,
          loading: false,
        };
      case SUCCESS(this.actionTypes.FETCH):
        return this.onFetchState(state, action.payload);
      case SUCCESS(this.actionTypes.FETCH_YEAR):
        return this.onFetchYearState(state, action.payload);
      case this.actionTypes.SET_ACTIVE_YEAR:
        return this.onSetActiveYearState(state, action.payload);
      case this.actionTypes.SET_ACTIVE:
        return this.onSetActiveState(state, action.payload);
      case this.actionTypes.RESET:
        return this.onResetState(state, action.payload);
      default:
        return state;
    }
  };
  protected onFetchYearState: OnStateType<TStateType, AxiosResponse> = (state, payload) => {
    const yearEntities = initializeYearArray(state.yearEntities, state.activeEntity);
    const data = payload.data as IMovieInsightsPerYear;
    addYear(yearEntities, state.activeEntity, data);

    return {
      ...state,
      loading: false,
      isPerYear: true,
      activeYearEntity: data,
      activeMovieInsights: data.movieInsights,
      yearEntities,
    };
  };
  protected onFetchState: OnStateType<TStateType, AxiosResponse> = (state, payload) => {
    const entity = payload.data as BaseMovieInsightsPerYearContainer<TEntity>;
    return {
      ...state,
      initialized: true,
      loading: false,
      isPerYear: false,
      activeEntity: entity,
      activeYearEntity: null,
      activeMovieInsights: entity.movieInsights,
      entitiesCache: [...state.entitiesCache, entity],
    };
  };

  protected onResetState: OnStateType<TStateType, any> = () => {
    return this.initialState;
  };
  protected onSetActiveState: OnStateType<TStateType, any> = (state, payload) => {
    let entity;
    if (!payload && state.isPerYear) {
      entity = state.activeEntity;
    } else {
      if (typeof state.activeEntity.entity === 'object') {
        entity = payload ? state.entitiesCache.filter(k => (k.entity as BaseEntity).id === payload)[0] : state.entitiesCache[0];
      } else {
        entity = state.activeEntity;
      }
    }
    return {
      ...state,
      isPerYear: false,
      activeYearEntity: null,
      activeEntity: entity,
      activeMovieInsights: (entity as BaseMovieInsightsContainer<any>).movieInsights,
    };
  };

  protected onSetActiveYearState: OnStateType<TStateType, any> = (state, payload) => {
    const ye = getMovieInsightsPerYear(state.yearEntities, state.activeEntity, payload);
    return {
      ...state,
      isPerYear: true,
      activeYearEntity: ye,
      activeMovieInsights: ye?.movieInsights,
    };
  };

  public abstract readonly fetch: IMovieInsightsContainerGetAction<TContainer | IPersonMultiView>;

  public abstract readonly fetchYear: IMovieInsightsContainerGetYearAction<IMovieInsightsPerYear | IPersonMultiView>;
  public readonly setActive: (id?: number | string, r?: CreditRole) => any = (id?) => ({
    type: this.actionTypes.SET_ACTIVE,
    payload: id,
  });

  public readonly setActiveYear: (y: number, r?: CreditRole) => any = year => ({
    type: this.actionTypes.SET_ACTIVE_YEAR,
    payload: year,
  });

  public readonly reset = () => ({
    type: this.actionTypes.RESET,
  });
}

export const fetch = (stateManager: BaseMovieInsightsContainerStateManager<any, any, any>) => {
  return stateManager.fetch;
};
export const fetchYear = (stateManager: BaseMovieInsightsContainerStateManager<any, any, any>) => {
  return stateManager.fetchYear;
};

export const setActive = (stateManager: BaseMovieInsightsContainerStateManager<any, any, any>) => {
  return stateManager.setActive;
};

export const setActiveYear = (stateManager: BaseMovieInsightsContainerStateManager<any, any, any>) => {
  return stateManager.setActiveYear;
};
export const reset = (stateManager: BaseMovieInsightsContainerStateManager<any, any, any>) => {
  return stateManager.reset;
};

export const hasYear = (stateManager: BaseMovieInsightsContainerStateManager<any, any, any>) => {
  return stateManager.hasYear;
};
