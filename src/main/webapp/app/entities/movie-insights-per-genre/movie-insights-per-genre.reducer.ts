import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieInsightsPerGenre, defaultValue } from 'app/shared/model/movie-insights-per-genre.model';

export const ACTION_TYPES = {
  SEARCH_MOVIEINSIGHTSPERGENRES: 'movieInsightsPerGenre/SEARCH_MOVIEINSIGHTSPERGENRES',
  FETCH_MOVIEINSIGHTSPERGENRE_LIST: 'movieInsightsPerGenre/FETCH_MOVIEINSIGHTSPERGENRE_LIST',
  FETCH_MOVIEINSIGHTSPERGENRE: 'movieInsightsPerGenre/FETCH_MOVIEINSIGHTSPERGENRE',
  CREATE_MOVIEINSIGHTSPERGENRE: 'movieInsightsPerGenre/CREATE_MOVIEINSIGHTSPERGENRE',
  UPDATE_MOVIEINSIGHTSPERGENRE: 'movieInsightsPerGenre/UPDATE_MOVIEINSIGHTSPERGENRE',
  DELETE_MOVIEINSIGHTSPERGENRE: 'movieInsightsPerGenre/DELETE_MOVIEINSIGHTSPERGENRE',
  RESET: 'movieInsightsPerGenre/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieInsightsPerGenre>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieInsightsPerGenreState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieInsightsPerGenreState = initialState, action): MovieInsightsPerGenreState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERGENRES):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERGENRE):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERGENRE):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERGENRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERGENRES):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERGENRE):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERGENRE):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERGENRE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERGENRES):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERGENRE):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERGENRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERGENRE):
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

const apiUrl = 'api/movie-insights-per-genres';
const apiSearchUrl = 'api/_search/movie-insights-per-genres';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovieInsightsPerGenre> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERGENRES,
  payload: axios.get<IMovieInsightsPerGenre>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovieInsightsPerGenre> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE_LIST,
  payload: axios.get<IMovieInsightsPerGenre>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovieInsightsPerGenre> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERGENRE,
    payload: axios.get<IMovieInsightsPerGenre>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovieInsightsPerGenre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEINSIGHTSPERGENRE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieInsightsPerGenre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERGENRE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieInsightsPerGenre> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEINSIGHTSPERGENRE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
