import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovie, defaultValue } from 'app/shared/model/movie.model';

export const ACTION_TYPES = {
  SEARCH_MOVIES: 'movie/SEARCH_MOVIES',
  FETCH_MOVIE_LIST: 'movie/FETCH_MOVIE_LIST',
  FETCH_MOVIE: 'movie/FETCH_MOVIE',
  CREATE_MOVIE: 'movie/CREATE_MOVIE',
  UPDATE_MOVIE: 'movie/UPDATE_MOVIE',
  DELETE_MOVIE: 'movie/DELETE_MOVIE',
  SET_BLOB: 'movie/SET_BLOB',
  RESET: 'movie/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovie>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieState = initialState, action): MovieState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIES):
    case REQUEST(ACTION_TYPES.FETCH_MOVIE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIE):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIE):
    case REQUEST(ACTION_TYPES.DELETE_MOVIE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIES):
    case FAILURE(ACTION_TYPES.FETCH_MOVIE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIE):
    case FAILURE(ACTION_TYPES.CREATE_MOVIE):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIE):
    case FAILURE(ACTION_TYPES.DELETE_MOVIE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIES):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIE):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/movies';
const apiSearchUrl = 'api/_search/movies';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovie> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIES,
  payload: axios.get<IMovie>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovie> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIE_LIST,
  payload: axios.get<IMovie>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovie> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIE,
    payload: axios.get<IMovie>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovie> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovie> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovie> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
