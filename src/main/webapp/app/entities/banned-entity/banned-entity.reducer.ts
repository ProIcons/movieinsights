import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBannedEntity, defaultValue } from 'app/shared/model/banned-entity.model';

export const ACTION_TYPES = {
  SEARCH_BANNEDENTITIES: 'bannedEntity/SEARCH_BANNEDENTITIES',
  FETCH_BANNEDENTITY_LIST: 'bannedEntity/FETCH_BANNEDENTITY_LIST',
  FETCH_BANNEDENTITY: 'bannedEntity/FETCH_BANNEDENTITY',
  CREATE_BANNEDENTITY: 'bannedEntity/CREATE_BANNEDENTITY',
  UPDATE_BANNEDENTITY: 'bannedEntity/UPDATE_BANNEDENTITY',
  DELETE_BANNEDENTITY: 'bannedEntity/DELETE_BANNEDENTITY',
  RESET: 'bannedEntity/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBannedEntity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BannedEntityState = Readonly<typeof initialState>;

// Reducer

export default (state: BannedEntityState = initialState, action): BannedEntityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_BANNEDENTITIES):
    case REQUEST(ACTION_TYPES.FETCH_BANNEDENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANNEDENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BANNEDENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_BANNEDENTITY):
    case REQUEST(ACTION_TYPES.DELETE_BANNEDENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_BANNEDENTITIES):
    case FAILURE(ACTION_TYPES.FETCH_BANNEDENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANNEDENTITY):
    case FAILURE(ACTION_TYPES.CREATE_BANNEDENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_BANNEDENTITY):
    case FAILURE(ACTION_TYPES.DELETE_BANNEDENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_BANNEDENTITIES):
    case SUCCESS(ACTION_TYPES.FETCH_BANNEDENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANNEDENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANNEDENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_BANNEDENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANNEDENTITY):
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

const apiUrl = 'api/banned-entities';
const apiSearchUrl = 'api/_search/banned-entities';

// Actions

export const getSearchEntities: ICrudSearchAction<IBannedEntity> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_BANNEDENTITIES,
  payload: axios.get<IBannedEntity>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IBannedEntity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BANNEDENTITY_LIST,
  payload: axios.get<IBannedEntity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBannedEntity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANNEDENTITY,
    payload: axios.get<IBannedEntity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBannedEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANNEDENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBannedEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANNEDENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBannedEntity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANNEDENTITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
