import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPerson, defaultValue } from 'app/shared/model/person.model';

export const ACTION_TYPES = {
  SEARCH_PEOPLE: 'person/SEARCH_PEOPLE',
  FETCH_PERSON_LIST: 'person/FETCH_PERSON_LIST',
  FETCH_PERSON: 'person/FETCH_PERSON',
  CREATE_PERSON: 'person/CREATE_PERSON',
  UPDATE_PERSON: 'person/UPDATE_PERSON',
  DELETE_PERSON: 'person/DELETE_PERSON',
  SET_BLOB: 'person/SET_BLOB',
  RESET: 'person/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPerson>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PersonState = Readonly<typeof initialState>;

// Reducer

export default (state: PersonState = initialState, action): PersonState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PEOPLE):
    case REQUEST(ACTION_TYPES.FETCH_PERSON_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PERSON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PERSON):
    case REQUEST(ACTION_TYPES.UPDATE_PERSON):
    case REQUEST(ACTION_TYPES.DELETE_PERSON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PEOPLE):
    case FAILURE(ACTION_TYPES.FETCH_PERSON_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PERSON):
    case FAILURE(ACTION_TYPES.CREATE_PERSON):
    case FAILURE(ACTION_TYPES.UPDATE_PERSON):
    case FAILURE(ACTION_TYPES.DELETE_PERSON):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PEOPLE):
    case SUCCESS(ACTION_TYPES.FETCH_PERSON_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERSON):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PERSON):
    case SUCCESS(ACTION_TYPES.UPDATE_PERSON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PERSON):
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

const apiUrl = 'api/people';
const apiSearchUrl = 'api/_search/people';

// Actions

export const getSearchEntities: ICrudSearchAction<IPerson> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PEOPLE,
  payload: axios.get<IPerson>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IPerson> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PERSON_LIST,
  payload: axios.get<IPerson>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPerson> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PERSON,
    payload: axios.get<IPerson>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPerson> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PERSON,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPerson> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PERSON,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPerson> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PERSON,
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
