import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieInsightsPerYear, defaultValue } from 'app/shared/model/movie-insights-per-year.model';

export const ACTION_TYPES = {
  SEARCH_MOVIEINSIGHTSPERYEARS: 'movieInsightsPerYear/SEARCH_MOVIEINSIGHTSPERYEARS',
  FETCH_MOVIEINSIGHTSPERYEAR_LIST: 'movieInsightsPerYear/FETCH_MOVIEINSIGHTSPERYEAR_LIST',
  FETCH_MOVIEINSIGHTSPERYEAR: 'movieInsightsPerYear/FETCH_MOVIEINSIGHTSPERYEAR',
  CREATE_MOVIEINSIGHTSPERYEAR: 'movieInsightsPerYear/CREATE_MOVIEINSIGHTSPERYEAR',
  UPDATE_MOVIEINSIGHTSPERYEAR: 'movieInsightsPerYear/UPDATE_MOVIEINSIGHTSPERYEAR',
  DELETE_MOVIEINSIGHTSPERYEAR: 'movieInsightsPerYear/DELETE_MOVIEINSIGHTSPERYEAR',
  RESET: 'movieInsightsPerYear/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieInsightsPerYear>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieInsightsPerYearState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieInsightsPerYearState = initialState, action): MovieInsightsPerYearState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERYEARS):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERYEAR):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERYEAR):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERYEAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERYEARS):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERYEAR):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERYEAR):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERYEAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERYEARS):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERYEAR):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERYEAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERYEAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/movie-insights-per-years';
const apiSearchUrl = 'api/_search/movie-insights-per-years';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovieInsightsPerYear> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERYEARS,
  payload: axios.get<IMovieInsightsPerYear>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovieInsightsPerYear> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR_LIST,
  payload: axios.get<IMovieInsightsPerYear>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovieInsightsPerYear> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERYEAR,
    payload: axios.get<IMovieInsightsPerYear>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovieInsightsPerYear> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEINSIGHTSPERYEAR,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieInsightsPerYear> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERYEAR,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieInsightsPerYear> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEINSIGHTSPERYEAR,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
