import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieInsightsPerCountry, defaultValue } from 'app/shared/model/movie-insights-per-country.model';

export const ACTION_TYPES = {
  SEARCH_MOVIEINSIGHTSPERCOUNTRIES: 'movieInsightsPerCountry/SEARCH_MOVIEINSIGHTSPERCOUNTRIES',
  FETCH_MOVIEINSIGHTSPERCOUNTRY_LIST: 'movieInsightsPerCountry/FETCH_MOVIEINSIGHTSPERCOUNTRY_LIST',
  FETCH_MOVIEINSIGHTSPERCOUNTRY: 'movieInsightsPerCountry/FETCH_MOVIEINSIGHTSPERCOUNTRY',
  CREATE_MOVIEINSIGHTSPERCOUNTRY: 'movieInsightsPerCountry/CREATE_MOVIEINSIGHTSPERCOUNTRY',
  UPDATE_MOVIEINSIGHTSPERCOUNTRY: 'movieInsightsPerCountry/UPDATE_MOVIEINSIGHTSPERCOUNTRY',
  DELETE_MOVIEINSIGHTSPERCOUNTRY: 'movieInsightsPerCountry/DELETE_MOVIEINSIGHTSPERCOUNTRY',
  RESET: 'movieInsightsPerCountry/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieInsightsPerCountry>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieInsightsPerCountryState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieInsightsPerCountryState = initialState, action): MovieInsightsPerCountryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOUNTRIES):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOUNTRY):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOUNTRY):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOUNTRIES):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOUNTRY):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOUNTRY):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOUNTRY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOUNTRIES):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOUNTRY):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOUNTRY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOUNTRY):
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

const apiUrl = 'api/movie-insights-per-countries';
const apiSearchUrl = 'api/_search/movie-insights-per-countries';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovieInsightsPerCountry> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERCOUNTRIES,
  payload: axios.get<IMovieInsightsPerCountry>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovieInsightsPerCountry> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY_LIST,
  payload: axios.get<IMovieInsightsPerCountry>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovieInsightsPerCountry> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERCOUNTRY,
    payload: axios.get<IMovieInsightsPerCountry>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovieInsightsPerCountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEINSIGHTSPERCOUNTRY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieInsightsPerCountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERCOUNTRY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieInsightsPerCountry> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEINSIGHTSPERCOUNTRY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
