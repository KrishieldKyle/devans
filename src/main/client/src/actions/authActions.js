import { SET_CURRENT_USER, AUTH_LOADING} from './types'
import axios from 'axios';
import setAuthToken from '../utils/setAuthToken';
import jwt_decode from 'jwt-decode';
import { setMessage } from "./messageActions";
import { clearErrors, getErrors } from "./errorActions";
import { setLoading } from "./commonActions";

// Register User
export const registerUser = (userData, history) => dispatch => {
    dispatch(setLoading(AUTH_LOADING, true));
    axios.post('/api/user/register', userData)
        .then(res => {
            dispatch(setLoading(AUTH_LOADING, false));
            // history.push('/login');
            dispatch(setMessage({message: res.data.message, isSuccess: res.data.success, isMessageShowing: true}));
            dispatch(clearErrors())
        })
        .catch(err => {
            dispatch(setLoading(AUTH_LOADING, false));
            dispatch(setMessage({message: err.response.data.message, isSuccess: err.response.data.success, isMessageShowing: true}));
            dispatch(getErrors(err.response.data))
        })
}

// Login - Get User Token
export const loginUser = (userData) => dispatch => {
    dispatch(setLoading(AUTH_LOADING, false));
    axios.post('/api/user/login', userData)
        .then(res => {
            // Save to Local storage
            const { jwt } = res.data;
            // Set token to local storage
            localStorage.setItem('jwtToken', jwt);
            // Set token to Auth header
            setAuthToken("Bearer "+jwt);
            // Decode token to get user data
            const decoded = jwt_decode(jwt);
            // Set current user
            dispatch(setCurrentUser(decoded.user));
            dispatch(setLoading(AUTH_LOADING, false));
            dispatch(setMessage({message: res.data.message, isSuccess: res.data.success, isMessageShowing: true}));
            dispatch(clearErrors())
        })
        .catch(err => {
            dispatch(setLoading(AUTH_LOADING, false));
            dispatch(setMessage({message: err.response.data.message, isSuccess: err.response.data.success, isMessageShowing: true}));
            dispatch(getErrors(err.response.data))
        })
};
// set Logged in user
export const setCurrentUser = (decoded) => {
    return {
        type: SET_CURRENT_USER,
        payload: decoded
    }
}

// Log user out
export const logoutUser = () => dispatch => {
    // Remove the token from the localStorage
    localStorage.removeItem('jwtToken');
    // Remove the auth header for future request
    setAuthToken(false);
    // Set the current to an empty object which will also set isAuthenticated FALSE
    dispatch(setCurrentUser({}))
}