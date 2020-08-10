import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductionCountry, defaultValue } from 'app/shared/model/production-country.model';

export const ACTION_TYPES = {
  SEARCH_PRODUCTIONCOUNTRIES: 'productionCountry/SEARCH_PRODUCTIONCOUNTRIES',
  FETCH_PRODUCTIONCOUNTRY_LIST: 'productionCountry/FETCH_PRODUCTIONCOUNTRY_LIST',
  FETCH_PRODUCTIONCOUNTRY: 'productionCountry/FETCH_PRODUCTIONCOUNTRY',
  CREATE_PRODUCTIONCOUNTRY: 'productionCountry/CREATE_PRODUCTIONCOUNTRY',
  UPDATE_PRODUCTIONCOUNTRY: 'productionCountry/UPDATE_PRODUCTIONCOUNTRY',
  DELETE_PRODUCTIONCOUNTRY: 'productionCountry/DELETE_PRODUCTIONCOUNTRY',
  RESET: 'productionCountry/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductionCountry>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProductionCountryState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductionCountryState = initialState, action): ProductionCountryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PRODUCTIONCOUNTRIES):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTIONCOUNTRY):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTIONCOUNTRY):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTIONCOUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PRODUCTIONCOUNTRIES):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTIONCOUNTRY):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTIONCOUNTRY):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTIONCOUNTRY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PRODUCTIONCOUNTRIES):
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTIONCOUNTRY):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTIONCOUNTRY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTIONCOUNTRY):
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

const apiUrl = 'api/production-countries';
const apiSearchUrl = 'api/_search/production-countries';

// Actions

export const getSearchEntities: ICrudSearchAction<IProductionCountry> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PRODUCTIONCOUNTRIES,
  payload: axios.get<IProductionCountry>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IProductionCountry> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY_LIST,
    payload: axios.get<IProductionCountry>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProductionCountry> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONCOUNTRY,
    payload: axios.get<IProductionCountry>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductionCountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTIONCOUNTRY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductionCountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTIONCOUNTRY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductionCountry> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTIONCOUNTRY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
