import { combineReducers } from 'redux';
import authReducer from './authReducer';
import userReducer from './userReducer';
import developersReducer from './developersReducer';
import errorReducer from './errorReducer';
import messageReducer from './messageReducer';

export default combineReducers({
    auth: authReducer,
    user: userReducer,
    developers: developersReducer,
    message: messageReducer,
    errors: errorReducer
});
