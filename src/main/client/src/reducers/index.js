import { combineReducers } from 'redux';
import authReducer from './authReducer';
import userReducer from './userReducer';
import developersReducer from './developersReducer';
import titlesReducer from './titlesReducer';
import technologiesReducer from './technologiesReducer';
import errorReducer from './errorReducer';
import messageReducer from './messageReducer';

export default combineReducers({
    auth: authReducer,
    user: userReducer,
    developers: developersReducer,
    titles: titlesReducer,
    technologies: technologiesReducer,
    message: messageReducer,
    errors: errorReducer
});
