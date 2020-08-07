import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieInsightsPerPerson, defaultValue } from 'app/shared/model/movie-insights-per-person.model';

export const ACTION_TYPES = {
  SEARCH_MOVIEINSIGHTSPERPEOPLE: 'movieInsightsPerPerson/SEARCH_MOVIEINSIGHTSPERPEOPLE',
  FETCH_MOVIEINSIGHTSPERPERSON_LIST: 'movieInsightsPerPerson/FETCH_MOVIEINSIGHTSPERPERSON_LIST',
  FETCH_MOVIEINSIGHTSPERPERSON: 'movieInsightsPerPerson/FETCH_MOVIEINSIGHTSPERPERSON',
  CREATE_MOVIEINSIGHTSPERPERSON: 'movieInsightsPerPerson/CREATE_MOVIEINSIGHTSPERPERSON',
  UPDATE_MOVIEINSIGHTSPERPERSON: 'movieInsightsPerPerson/UPDATE_MOVIEINSIGHTSPERPERSON',
  DELETE_MOVIEINSIGHTSPERPERSON: 'movieInsightsPerPerson/DELETE_MOVIEINSIGHTSPERPERSON',
  RESET: 'movieInsightsPerPerson/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieInsightsPerPerson>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MovieInsightsPerPersonState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieInsightsPerPersonState = initialState, action): MovieInsightsPerPersonState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERPEOPLE):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERPERSON):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERPERSON):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERPERSON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERPEOPLE):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERPERSON):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERPERSON):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERPERSON):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERPEOPLE):
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEINSIGHTSPERPERSON):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERPERSON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEINSIGHTSPERPERSON):
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

const apiUrl = 'api/movie-insights-per-people';
const apiSearchUrl = 'api/_search/movie-insights-per-people';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovieInsightsPerPerson> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MOVIEINSIGHTSPERPEOPLE,
  payload: axios.get<IMovieInsightsPerPerson>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IMovieInsightsPerPerson> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON_LIST,
  payload: axios.get<IMovieInsightsPerPerson>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMovieInsightsPerPerson> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEINSIGHTSPERPERSON,
    payload: axios.get<IMovieInsightsPerPerson>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMovieInsightsPerPerson> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEINSIGHTSPERPERSON,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieInsightsPerPerson> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEINSIGHTSPERPERSON,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieInsightsPerPerson> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEINSIGHTSPERPERSON,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
