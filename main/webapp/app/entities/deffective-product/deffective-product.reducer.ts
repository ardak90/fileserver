import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDeffectiveProduct, defaultValue } from 'app/shared/model/deffective-product.model';

export const ACTION_TYPES = {
  FETCH_DEFFECTIVEPRODUCT_LIST: 'deffectiveProduct/FETCH_DEFFECTIVEPRODUCT_LIST',
  FETCH_DEFFECTIVEPRODUCT: 'deffectiveProduct/FETCH_DEFFECTIVEPRODUCT',
  CREATE_DEFFECTIVEPRODUCT: 'deffectiveProduct/CREATE_DEFFECTIVEPRODUCT',
  UPDATE_DEFFECTIVEPRODUCT: 'deffectiveProduct/UPDATE_DEFFECTIVEPRODUCT',
  DELETE_DEFFECTIVEPRODUCT: 'deffectiveProduct/DELETE_DEFFECTIVEPRODUCT',
  RESET: 'deffectiveProduct/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeffectiveProduct>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DeffectiveProductState = Readonly<typeof initialState>;

// Reducer

export default (state: DeffectiveProductState = initialState, action): DeffectiveProductState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DEFFECTIVEPRODUCT):
    case REQUEST(ACTION_TYPES.UPDATE_DEFFECTIVEPRODUCT):
    case REQUEST(ACTION_TYPES.DELETE_DEFFECTIVEPRODUCT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT):
    case FAILURE(ACTION_TYPES.CREATE_DEFFECTIVEPRODUCT):
    case FAILURE(ACTION_TYPES.UPDATE_DEFFECTIVEPRODUCT):
    case FAILURE(ACTION_TYPES.DELETE_DEFFECTIVEPRODUCT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEFFECTIVEPRODUCT):
    case SUCCESS(ACTION_TYPES.UPDATE_DEFFECTIVEPRODUCT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEFFECTIVEPRODUCT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/deffective-products';

// Actions

export const getEntities: ICrudGetAllAction<IDeffectiveProduct> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT_LIST,
  payload: axios.get<IDeffectiveProduct>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDeffectiveProduct> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEFFECTIVEPRODUCT,
    payload: axios.get<IDeffectiveProduct>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDeffectiveProduct> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEFFECTIVEPRODUCT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDeffectiveProduct> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEFFECTIVEPRODUCT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeffectiveProduct> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEFFECTIVEPRODUCT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
