import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBannedPersistentEntity, defaultValue } from 'app/shared/model/banned-persistent-entity.model';

export const ACTION_TYPES = {
  SEARCH_BANNEDPERSISTENTENTITIES: 'bannedPersistentEntity/SEARCH_BANNEDPERSISTENTENTITIES',
  FETCH_BANNEDPERSISTENTENTITY_LIST: 'bannedPersistentEntity/FETCH_BANNEDPERSISTENTENTITY_LIST',
  FETCH_BANNEDPERSISTENTENTITY: 'bannedPersistentEntity/FETCH_BANNEDPERSISTENTENTITY',
  CREATE_BANNEDPERSISTENTENTITY: 'bannedPersistentEntity/CREATE_BANNEDPERSISTENTENTITY',
  UPDATE_BANNEDPERSISTENTENTITY: 'bannedPersistentEntity/UPDATE_BANNEDPERSISTENTENTITY',
  DELETE_BANNEDPERSISTENTENTITY: 'bannedPersistentEntity/DELETE_BANNEDPERSISTENTENTITY',
  RESET: 'bannedPersistentEntity/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBannedPersistentEntity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BannedPersistentEntityState = Readonly<typeof initialState>;

// Reducer

export default (state: BannedPersistentEntityState = initialState, action): BannedPersistentEntityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_BANNEDPERSISTENTENTITIES):
    case REQUEST(ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BANNEDPERSISTENTENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_BANNEDPERSISTENTENTITY):
    case REQUEST(ACTION_TYPES.DELETE_BANNEDPERSISTENTENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_BANNEDPERSISTENTENTITIES):
    case FAILURE(ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY):
    case FAILURE(ACTION_TYPES.CREATE_BANNEDPERSISTENTENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_BANNEDPERSISTENTENTITY):
    case FAILURE(ACTION_TYPES.DELETE_BANNEDPERSISTENTENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_BANNEDPERSISTENTENTITIES):
    case SUCCESS(ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANNEDPERSISTENTENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_BANNEDPERSISTENTENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANNEDPERSISTENTENTITY):
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

const apiUrl = 'api/banned-persistent-entities';
const apiSearchUrl = 'api/_search/banned-persistent-entities';

// Actions

export const getSearchEntities: ICrudSearchAction<IBannedPersistentEntity> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_BANNEDPERSISTENTENTITIES,
  payload: axios.get<IBannedPersistentEntity>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IBannedPersistentEntity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY_LIST,
  payload: axios.get<IBannedPersistentEntity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBannedPersistentEntity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANNEDPERSISTENTENTITY,
    payload: axios.get<IBannedPersistentEntity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBannedPersistentEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANNEDPERSISTENTENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBannedPersistentEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANNEDPERSISTENTENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBannedPersistentEntity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANNEDPERSISTENTENTITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
