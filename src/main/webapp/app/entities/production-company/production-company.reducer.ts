import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductionCompany, defaultValue } from 'app/shared/model/production-company.model';

export const ACTION_TYPES = {
  SEARCH_PRODUCTIONCOMPANIES: 'productionCompany/SEARCH_PRODUCTIONCOMPANIES',
  FETCH_PRODUCTIONCOMPANY_LIST: 'productionCompany/FETCH_PRODUCTIONCOMPANY_LIST',
  FETCH_PRODUCTIONCOMPANY: 'productionCompany/FETCH_PRODUCTIONCOMPANY',
  CREATE_PRODUCTIONCOMPANY: 'productionCompany/CREATE_PRODUCTIONCOMPANY',
  UPDATE_PRODUCTIONCOMPANY: 'productionCompany/UPDATE_PRODUCTIONCOMPANY',
  DELETE_PRODUCTIONCOMPANY: 'productionCompany/DELETE_PRODUCTIONCOMPANY',
  RESET: 'productionCompany/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductionCompany>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProductionCompanyState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductionCompanyState = initialState, action): ProductionCompanyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PRODUCTIONCOMPANIES):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONCOMPANY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIONCOMPANY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTIONCOMPANY):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTIONCOMPANY):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTIONCOMPANY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PRODUCTIONCOMPANIES):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONCOMPANY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIONCOMPANY):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTIONCOMPANY):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTIONCOMPANY):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTIONCOMPANY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PRODUCTIONCOMPANIES):
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONCOMPANY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIONCOMPANY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTIONCOMPANY):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTIONCOMPANY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTIONCOMPANY):
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

const apiUrl = 'api/production-companies';
const apiSearchUrl = 'api/_search/production-companies';

// Actions

export const getSearchEntities: ICrudSearchAction<IProductionCompany> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PRODUCTIONCOMPANIES,
  payload: axios.get<IProductionCompany>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IProductionCompany> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONCOMPANY_LIST,
    payload: axios.get<IProductionCompany>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProductionCompany> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIONCOMPANY,
    payload: axios.get<IProductionCompany>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductionCompany> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTIONCOMPANY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductionCompany> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTIONCOMPANY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductionCompany> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTIONCOMPANY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
