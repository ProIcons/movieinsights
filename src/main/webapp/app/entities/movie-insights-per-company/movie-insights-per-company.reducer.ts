import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieInsightsPerCompany, defaultValue } from 'app/shared/model/movie-insights-per-company.model';

export const ACTION_TYPES = {
  SEARCH_MOVIEINSIGHTSPERCOMPANIES: 'movieInsightsPerCompany/SEARCH_MOVIEINSIGHTSPERCOMPANIES',
  FETCH_MOVIEINSIGHTSPERCOMPANY_LIST: 'movieInsightsPerCompany/FETCH_MOVIEINSIGHTSPERCOMPANY_LIST',
  FETCH_MOVIEINSIGHTSPERCOMPANY: 'movieInsightsPerCompany/FETCH_MOVIEINSIGHTSPERCOMPANY',
  CREATE_MOVIEINSIGHTSPERCOMPANY: 'movieInsightsPerCompany/CREATE_MOVIEINSIGHTSPERCOMPANY',
  UPDATE_MOVIEINSIGHTSPERCOMPANY: 'movieInsightsPerCompany/UPDATE_MOVIEINSIGHTSPERCOMPANY',
  DELETE_MOVIEINSIGHTSPERCOMPANY: 'movieInsightsPerCompany/DELETE_MOVIEINSIGHTSPERCOMPANY',
  RESET: 'movieInsightsPerCompany/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieInsightsPerCompany>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieInsightsPerCompanyState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieInsightsPerCompanyState = initialState, action): MovieInsightsPerCompanyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOMPANIES):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOMPANY):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOMPANY):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOMPANY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOMPANIES):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOMPANY):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOMPANY):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOMPANY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOMPANIES):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOMPANY):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOMPANY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOMPANY):
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

const apiUrl = 'api/movie-insights-per-companies';
const apiSearchUrl = 'api/_search/movie-insights-per-companies';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovieInsightsPerCompany> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOMPANIES,
  payload: axios.get<IMovieInsightsPerCompany>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovieInsightsPerCompany> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY_LIST,
  payload: axios.get<IMovieInsightsPerCompany>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovieInsightsPerCompany> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOMPANY,
    payload: axios.get<IMovieInsightsPerCompany>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovieInsightsPerCompany> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOMPANY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieInsightsPerCompany> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOMPANY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieInsightsPerCompany> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOMPANY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
