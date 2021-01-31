import { GET_USER, 
    SET_USER_PROFILE, 
    SAVE_PROFILE_LOADING, 
    GET_USER_LOADING,
    UPDATE_USER_TITLES,
    UPDATE_USER_TITLES_LOADING,
    UPDATE_USER_TECHNOLOGIES,
    UPDATE_USER_TECHNOLOGIES_LOADING
} from './types';

import { setMessage } from "./messageActions";
import { clearErrors, getErrors } from "./errorActions";
import { setLoading } from "./commonActions";

import axios from "axios";

// get user
export const getUser = (userId, callback) => dispatch => {
    dispatch(setLoading(GET_USER_LOADING, true))
    axios.get(`/api/user/${userId}`)
        .then(res => {
            dispatch({
                type: GET_USER,
                payload: res.data.user
            })
            dispatch(setLoading(GET_USER_LOADING, false))
            callback();
        })
        .catch(err => {
            dispatch(setLoading(GET_USER_LOADING, false))
            dispatch(getErrors(err.response.data))
        })
}

export const addOrUpdateProfile = (profileData, history) => dispatch => {
    dispatch(setLoading(SAVE_PROFILE_LOADING,true))
    axios.post(`/api/profile/`, profileData)
        .then(res => {
            dispatch(setMessage({message: res.data.message, isSuccess: res.data.success, isMessageShowing: true}));
            dispatch({
                type: SET_USER_PROFILE,
                payload: res.data.profile
            })
            // history.push("/account")
            dispatch(clearErrors())
            dispatch(setLoading(SAVE_PROFILE_LOADING,false))
        })
        .catch(err => {
            dispatch(setMessage({message: err.response.data.message, isSuccess: err.response.data.success, isMessageShowing: true}));
            dispatch(getErrors(err.response.data))
            dispatch(setLoading(SAVE_PROFILE_LOADING, false))
        })
}

export const updateUserTitles = (newTitles) => dispatch => {
    dispatch(setLoading(UPDATE_USER_TITLES_LOADING,true))
    axios.post(`/api/user/titles`, newTitles)
        .then(res => {
            dispatch(setMessage({message: res.data.message, isSuccess: res.data.success, isMessageShowing: true}));
            dispatch({
                type: UPDATE_USER_TITLES,
                payload: res.data.titles
            })
            // history.push("/account")
            dispatch(clearErrors())
            dispatch(setLoading(UPDATE_USER_TITLES_LOADING,false))
        })
        .catch(err => {
            dispatch(setMessage({message: err.response.data.message, isSuccess: err.response.data.success, isMessageShowing: true}));
            dispatch(getErrors(err.response.data))
            dispatch(setLoading(UPDATE_USER_TITLES_LOADING,false))
        })
}

export const updateUserTechnologies = (newTechnologies) => dispatch => {
    dispatch(setLoading(UPDATE_USER_TECHNOLOGIES_LOADING,true))
    axios.post(`/api/user/technologies`, newTechnologies)
        .then(res => {
            dispatch(setMessage({message: res.data.message, isSuccess: res.data.success, isMessageShowing: true}));
            dispatch({
                type: UPDATE_USER_TECHNOLOGIES,
                payload: res.data.technologies
            })
            // history.push("/account")
            dispatch(clearErrors())
            dispatch(setLoading(UPDATE_USER_TECHNOLOGIES_LOADING,false))
        })
        .catch(err => {
            dispatch(setMessage({message: err.response.data.message, isSuccess: err.response.data.success, isMessageShowing: true}));
            dispatch(getErrors(err.response.data))
            dispatch(setLoading(UPDATE_USER_TECHNOLOGIES_LOADING,false))
        })
}