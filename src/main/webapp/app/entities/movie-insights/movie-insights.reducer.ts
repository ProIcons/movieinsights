import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieInsights, defaultValue } from 'app/shared/model/movie-insights.model';

export const ACTION_TYPES = {
  SEARCH_MOVIEINSIGHTS: 'movieInsights/SEARCH_MOVIEINSIGHTS',
  FETCH_MOVIEINSIGHTS_LIST: 'movieInsights/FETCH_MOVIEINSIGHTS_LIST',
  FETCH_MOVIEINSIGHTS: 'movieInsights/FETCH_MOVIEINSIGHTS',
  CREATE_MOVIEINSIGHTS: 'movieInsights/CREATE_MOVIEINSIGHTS',
  UPDATE_MOVIEINSIGHTS: 'movieInsights/UPDATE_MOVIEINSIGHTS',
  DELETE_MOVIEINSIGHTS: 'movieInsights/DELETE_MOVIEINSIGHTS',
  RESET: 'movieInsights/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieInsights>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieInsightsState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieInsightsState = initialState, action): MovieInsightsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIEINSIGHTS):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEINSIGHTS):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEINSIGHTS):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEINSIGHTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIEINSIGHTS):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTS):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEINSIGHTS):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEINSIGHTS):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEINSIGHTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIEINSIGHTS):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEINSIGHTS):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEINSIGHTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEINSIGHTS):
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

const apiUrl = 'api/movie-insights';
const apiSearchUrl = 'api/_search/movie-insights';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovieInsights> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIEINSIGHTS,
  payload: axios.get<IMovieInsights>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovieInsights> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIEINSIGHTS_LIST,
  payload: axios.get<IMovieInsights>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovieInsights> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEINSIGHTS,
    payload: axios.get<IMovieInsights>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovieInsights> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEINSIGHTS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieInsights> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEINSIGHTS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieInsights> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEINSIGHTS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
