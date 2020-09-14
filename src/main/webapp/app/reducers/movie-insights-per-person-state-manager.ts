import {
  MovieInsightsContainerState,
  YearDataPerId,
  YearDataPerRole,
  YearDataPerYear,
} from 'app/reducers/utils/base-movie-insights-container-state-manager.models';
import { Service } from 'app/service';
import BaseMovieInsightsContainerStateManager, { OnStateType } from 'app/reducers/utils/base-movie-insights-container-state-manager';
import { store_ } from 'app/index';
import { IRootState } from 'app/shared/reducers';
import { AxiosResponse } from 'axios';
import _ from 'lodash';
import { EntityType } from 'app/models/enumerations/EntityType.enum';
import { CreditRole } from 'app/models/enumerations';
import { IMovieInsightsPerPerson, defaultValue as movieInsightsPerPersonDefaultValue } from 'app/models/IMovieInsightsPerPerson.Model';
import { IPersonMultiView, defaultValue as personMultiViewDefaultValue } from 'app/models/IPersonMultiView';
import { IMovieInsights, defaultValue as movieInsightsDefaultValue } from 'app/models/IMovieInsights.Model';
import { IMovieInsightsPerYear } from 'app/models/IMovieInsightsPerYear.Model';
import { IPerson } from 'app/models/IPerson.Model';

type SetActiveType = {
  num: number;
  role: CreditRole;
};

export interface MovieInsightsPerPersonState extends MovieInsightsContainerState<IMovieInsightsPerPerson> {
  activeRole: CreditRole;
  activeRoles: CreditRole[];
  _activeEntity: IPersonMultiView;
  _entitiesCache: IPersonMultiView[];
  yearRoles: CreditRole[][];
}

export const initialMovieInsightsPerPersonState: MovieInsightsPerPersonState = {
  activeMovieInsights: movieInsightsDefaultValue,
  activeYearEntity: null,
  yearEntities: [],
  activeEntity: movieInsightsPerPersonDefaultValue,
  _activeEntity: personMultiViewDefaultValue,
  entitiesCache: [],
  _entitiesCache: [],
  activeRoles: [],
  errorMessage: '',
  loading: false,
  initialized: false,
  isPerYear: false,
  activeRole: null,
  entityType: EntityType.PERSON,
  yearRoles: [],
};

//  region Utils
const getPreferredRole = (roles: CreditRole[]) => {
  if (roles.includes(CreditRole.ACTOR)) return CreditRole.ACTOR;
  else if (roles.includes(CreditRole.DIRECTOR)) return CreditRole.DIRECTOR;
  else if (roles.includes(CreditRole.PRODUCER)) return CreditRole.PRODUCER;
  else if (roles.includes(CreditRole.WRITER)) return CreditRole.WRITER;

  throw new Error('Credit has no roles');
};

const getMovieInsightsByRole = (entity: IPersonMultiView, role: CreditRole): { mi: IMovieInsights; yearData: number[][] } => {
  let roleData: { mi: IMovieInsights; yearData: number[][] };
  switch (role) {
    case CreditRole.ACTOR:
      roleData = { mi: entity.actor, yearData: entity.actorYearData };
      break;
    case CreditRole.DIRECTOR:
      roleData = { mi: entity.director, yearData: entity.directorYearData };
      break;
    case CreditRole.PRODUCER:
      roleData = { mi: entity.producer, yearData: entity.producerYearData };
      break;
    case CreditRole.WRITER:
      roleData = { mi: entity.writer, yearData: entity.writerYearData };
      break;
    default:
      roleData = null;
  }
  return roleData;
};

const initializeYearArray = (_yearEntities: YearDataPerId[], obj: IPersonMultiView) => {
  const yearEntities = _.cloneDeep(_yearEntities);
  const id = obj.person.id;

  if (yearEntities.filter(yearData => yearData.id === id).length === 0) {
    yearEntities.push({
      id,
      yearData: [],
    });
  }
  return yearEntities;
};

const addYear = (yearEntities: YearDataPerId[], obj: MovieInsightsPerPersonState, role: CreditRole, yearEntity: IMovieInsightsPerYear) => {
  const id = obj._activeEntity.person.id;

  const yearDataPerId = yearEntities.filter(yearData => yearData.id === id)[0];
  if (!yearDataPerId) {
    throw new Error('You forgot to initialize the array');
  }

  const yearDataPerRole: YearDataPerRole = {
    role,
    data: yearEntity,
  };

  let yearDataPerYear: YearDataPerYear = yearDataPerId.yearData.filter(data => data.year === yearEntity.entity)[0];
  if (!yearDataPerYear) {
    yearDataPerYear = {
      year: yearEntity.entity,
      roleData: [],
    };
    yearDataPerId.yearData.push(yearDataPerYear);
  }
  yearDataPerYear.roleData.push(yearDataPerRole);
  return yearEntities;
};

const getMovieInsightsPerYearByMultiView = (multiView: IPersonMultiView, activeRole: CreditRole) => {
  const data = getMovieInsightsByRole(multiView, activeRole);
  const mipp: IMovieInsightsPerYear = {
    id: data.mi.id * multiView.person.id,
    movieInsights: data.mi,
    entity: multiView.year,
  };
  return mipp;
};

const getMovieInsightsPerPersonByMultiView = (multiView: IPersonMultiView, activeRole: CreditRole) => {
  const data = getMovieInsightsByRole(multiView, activeRole);
  const mipp: IMovieInsightsPerPerson = {
    id: data.mi.id * multiView.person.id,
    movieInsights: data.mi,
    yearData: data.yearData,
    as: activeRole,
    movieInsightsPerYears: null,
    entity: multiView.person,
  };
  return mipp;
};

const getMovieInsightsPerYear = (yearEntities: YearDataPerId[], state: MovieInsightsPerPersonState, year: number, role?: CreditRole) => {
  const obj = state._activeEntity;

  if (!role) {
    role = getPreferredRole(state.yearRoles[year]);
  }
  const id = obj.person.id;

  if (!role) {
    return undefined;
  }

  if (!state.yearRoles[year].includes(role)) {
    throw new Error("There's no role data for this year");
  }

  if (yearEntities.length > 0) {
    return yearEntities
      .filter(yearData => yearData.id === id)[0]
      .yearData.filter(data => data.year === year)[0]
      .roleData.filter(roleData => roleData.role === role)[0].data;
  }
  return undefined;
};
//  endregion

export class MovieInsightsPerPersonStateManager extends BaseMovieInsightsContainerStateManager<
  MovieInsightsPerPersonState,
  IMovieInsightsPerPerson,
  IPerson
> {
  constructor() {
    super(initialMovieInsightsPerPersonState, 'MovieInsightsPerPerson');
  }

  public getState: () => MovieInsightsPerPersonState = () => {
    return (store_.getState() as IRootState).movieInsightsPerPerson;
  };

  public hasYear = (year: number) => {
    const id = this.getState()._activeEntity ? this.getState()._activeEntity.person.id : 0;

    const yearEntities = this.getState().yearEntities;

    const yearDataPerId = yearEntities.filter(yearData => yearData.id === id)[0];
    if (!yearDataPerId) return false;

    const yearDataPerYear = yearDataPerId.yearData.filter(data => data.year === year)[0];
    return !!yearDataPerYear;
  };

  protected onFetchYearState: OnStateType<MovieInsightsPerPersonState, AxiosResponse<IPersonMultiView>> = (state, payload) => {
    const yearEntities = initializeYearArray(state.yearEntities, state._activeEntity);
    const preferredRole = getPreferredRole(payload.data.roles);

    // const movieInsightsPerYears: IMovieInsightsPerYear[] = [];
    let movieInsightsPerYear: IMovieInsightsPerYear;
    for (const role of payload.data.roles) {
      const _movieInsightsPerYear = getMovieInsightsPerYearByMultiView(payload.data, role);
      if (role === preferredRole) {
        movieInsightsPerYear = _movieInsightsPerYear;
      }
      // movieInsightsPerYears.push(_movieInsightsPerYear);
      addYear(yearEntities, state, role, _movieInsightsPerYear);
    }

    const yearRoles = _.cloneDeep(state.yearRoles);
    yearRoles[payload.data.year] = payload.data.roles;

    return {
      ...state,
      loading: false,
      activeRole: preferredRole,
      activeRoles: payload.data.roles,
      isPerYear: true,
      activeYearEntity: movieInsightsPerYear,
      activeMovieInsights: movieInsightsPerYear?.movieInsights,
      yearRoles,
      yearEntities,
    };
  };

  protected onFetchState: OnStateType<MovieInsightsPerPersonState, AxiosResponse<IPersonMultiView>> = (state, payload) => {
    const movieInsightsPerPeople: IMovieInsightsPerPerson[] = [];
    const preferredRole = getPreferredRole(payload.data.roles);
    let movieInsightsPerPerson: IMovieInsightsPerPerson;
    for (const role of payload.data.roles) {
      const _movieInsightsPerPerson = getMovieInsightsPerPersonByMultiView(payload.data, role);
      if (role === preferredRole) {
        movieInsightsPerPerson = _movieInsightsPerPerson;
      }
      movieInsightsPerPeople.push(_movieInsightsPerPerson);
    }
    return {
      ...state,
      loading: false,
      isPerYear: false,
      activeRole: preferredRole,
      activeRoles: payload.data.roles,
      activeEntity: movieInsightsPerPerson,
      _activeEntity: payload.data,
      activeYearEntity: null,
      activeMovieInsights: movieInsightsPerPerson.movieInsights,
      _entitiesCache: [...state._entitiesCache, payload.data],
      entitiesCache: [...state.entitiesCache, ...movieInsightsPerPeople],
    };
  };

  protected onSetActiveState: OnStateType<MovieInsightsPerPersonState, SetActiveType> = (state, payload) => {
    let entity: IPersonMultiView;

    if ((!payload || !payload.num) && state.isPerYear) {
      entity = state._activeEntity;
    } else {
      entity = state._entitiesCache.filter(k => k.person.id === payload.num)[0];
    }

    const role = payload.role ? payload.role : getPreferredRole(entity.roles);
    const activeEntity = getMovieInsightsPerPersonByMultiView(entity, role);

    return {
      ...state,
      isPerYear: false,
      activeYearEntity: null,
      _activeEntity: entity,
      activeEntity,
      activeRole: role,
      activeRoles: entity.roles,
      activeMovieInsights: activeEntity.movieInsights,
    };
  };

  protected onSetActiveYearState: OnStateType<MovieInsightsPerPersonState, SetActiveType> = (state, payload) => {
    let year = payload.num;
    let movieInsightsPerYear: IMovieInsightsPerYear = null;
    if (!payload.num && state.isPerYear) {
      movieInsightsPerYear = state.activeYearEntity;
      year = state.activeYearEntity.entity;
    }

    const role: CreditRole =
      payload.role && state.yearRoles[year].includes(payload.role) ? payload.role : getPreferredRole(state.yearRoles[year]);
    if (!movieInsightsPerYear) movieInsightsPerYear = getMovieInsightsPerYear(state.yearEntities, state, payload.num, role);

    return {
      ...state,
      isPerYear: true,
      activeRole: role,
      activeRoles: state.yearRoles[year],
      activeYearEntity: movieInsightsPerYear,
      activeMovieInsights: movieInsightsPerYear?.movieInsights,
    };
  };

  setActive = (id: number, role?: CreditRole) => ({
    type: this.actionTypes.SET_ACTIVE,
    payload: { num: id, role },
  });

  setActiveYear = (year: number, role?: CreditRole) => ({
    type: this.actionTypes.SET_ACTIVE_YEAR,
    payload: { num: year, role },
  });

  fetch = (id: number) => ({
    type: this.actionTypes.FETCH,
    payload: Service.getMovieInsightsPerPerson(id),
  });

  fetchYear = (year: number) => ({
    type: this.actionTypes.FETCH_YEAR,
    payload: Service.getMovieInsightsPerPersonPerYear(this.getState()._activeEntity.person.id, year),
  });
}

const stateManager = new MovieInsightsPerPersonStateManager();
export default stateManager;
