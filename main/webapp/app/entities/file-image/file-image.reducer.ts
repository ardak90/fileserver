import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFileImage, defaultValue } from 'app/shared/model/file-image.model';

export const ACTION_TYPES = {
  FETCH_FILEIMAGE_LIST: 'fileImage/FETCH_FILEIMAGE_LIST',
  FETCH_FILEIMAGE: 'fileImage/FETCH_FILEIMAGE',
  CREATE_FILEIMAGE: 'fileImage/CREATE_FILEIMAGE',
  UPDATE_FILEIMAGE: 'fileImage/UPDATE_FILEIMAGE',
  DELETE_FILEIMAGE: 'fileImage/DELETE_FILEIMAGE',
  RESET: 'fileImage/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFileImage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FileImageState = Readonly<typeof initialState>;

// Reducer

export default (state: FileImageState = initialState, action): FileImageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FILEIMAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILEIMAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FILEIMAGE):
    case REQUEST(ACTION_TYPES.UPDATE_FILEIMAGE):
    case REQUEST(ACTION_TYPES.DELETE_FILEIMAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FILEIMAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILEIMAGE):
    case FAILURE(ACTION_TYPES.CREATE_FILEIMAGE):
    case FAILURE(ACTION_TYPES.UPDATE_FILEIMAGE):
    case FAILURE(ACTION_TYPES.DELETE_FILEIMAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEIMAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEIMAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FILEIMAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_FILEIMAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FILEIMAGE):
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

const apiUrl = 'api/file-images';

// Actions

export const getEntities: ICrudGetAllAction<IFileImage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FILEIMAGE_LIST,
  payload: axios.get<IFileImage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFileImage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FILEIMAGE,
    payload: axios.get<IFileImage>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFileImage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FILEIMAGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFileImage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FILEIMAGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFileImage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FILEIMAGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
