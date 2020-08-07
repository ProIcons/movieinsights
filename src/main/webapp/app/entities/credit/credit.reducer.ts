import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICredit, defaultValue } from 'app/shared/model/credit.model';

export const ACTION_TYPES = {
  SEARCH_CREDITS: 'credit/SEARCH_CREDITS',
  FETCH_CREDIT_LIST: 'credit/FETCH_CREDIT_LIST',
  FETCH_CREDIT: 'credit/FETCH_CREDIT',
  CREATE_CREDIT: 'credit/CREATE_CREDIT',
  UPDATE_CREDIT: 'credit/UPDATE_CREDIT',
  DELETE_CREDIT: 'credit/DELETE_CREDIT',
  RESET: 'credit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICredit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CreditState = Readonly<typeof initialState>;

// Reducer

export default (state: CreditState = initialState, action): CreditState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CREDITS):
    case REQUEST(ACTION_TYPES.FETCH_CREDIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CREDIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CREDIT):
    case REQUEST(ACTION_TYPES.UPDATE_CREDIT):
    case REQUEST(ACTION_TYPES.DELETE_CREDIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_CREDITS):
    case FAILURE(ACTION_TYPES.FETCH_CREDIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CREDIT):
    case FAILURE(ACTION_TYPES.CREATE_CREDIT):
    case FAILURE(ACTION_TYPES.UPDATE_CREDIT):
    case FAILURE(ACTION_TYPES.DELETE_CREDIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CREDITS):
    case SUCCESS(ACTION_TYPES.FETCH_CREDIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CREDIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CREDIT):
    case SUCCESS(ACTION_TYPES.UPDATE_CREDIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CREDIT):
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

const apiUrl = 'api/credits';
const apiSearchUrl = 'api/_search/credits';

// Actions

export const getSearchEntities: ICrudSearchAction<ICredit> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CREDITS,
  payload: axios.get<ICredit>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ICredit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CREDIT_LIST,
  payload: axios.get<ICredit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICredit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CREDIT,
    payload: axios.get<ICredit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICredit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CREDIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICredit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CREDIT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICredit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CREDIT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
