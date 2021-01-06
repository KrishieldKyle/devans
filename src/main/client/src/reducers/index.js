import { combineReducers } from 'redux';
import authReducer from './authReducer';
import developersReducer from './developersReducer';
import errorReducer from './errorReducer';

export default combineReducers({
    auth: authReducer,
    developers: developersReducer,
    errors: errorReducer
});
