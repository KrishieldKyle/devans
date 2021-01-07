import { combineReducers } from 'redux';
import authReducer from './authReducer';
import developersReducer from './developersReducer';
import errorReducer from './errorReducer';
import messageReducer from './messageReducer';

export default combineReducers({
    auth: authReducer,
    developers: developersReducer,
    message: messageReducer,
    errors: errorReducer
});
