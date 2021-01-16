import { GET_USER, GET_ERRORS, SET_USER_PROFILE, SAVE_PROFILE_LOADING, GET_USER_LOADING } from './types';

import { setMessage } from "./messageActions";
import { clearErrors } from "./errorActions";

import axios from "axios";

// get user
export const getUser = (userId, callback) => dispatch => {
    dispatch(setGetUserLoading(true))
    axios.get(`/api/user/${userId}`)
        .then(res => {
            dispatch({
                type: GET_USER,
                payload: res.data.user
            })
            dispatch(setGetUserLoading(false))
            callback();
        })
        .catch(err => {
            dispatch(setGetUserLoading(false))
            dispatch({
                type: GET_ERRORS,
                payload: err.response.data
            })
        })
}

export const addOrUpdateProfile = (profileData, history) => dispatch => {
    dispatch(setSaveProfileLoading(true))
    axios.post(`/api/profile/`, profileData)
        .then(res => {
            dispatch(setMessage({message: res.data.message, isSuccess: res.data.success, isMessageShowing: true}));
            dispatch({
                type: SET_USER_PROFILE,
                payload: res.data.profile
            })
            // history.push("/account")
            dispatch(clearErrors())
            dispatch(setSaveProfileLoading(false))
        })
        .catch(err => {
            dispatch(setMessage({message: err.response.data.message, isSuccess: err.response.data.success, isMessageShowing: true}));
            dispatch({
                type: GET_ERRORS,
                payload: err.response.data
            })
            dispatch(setSaveProfileLoading(false))
        })
}


// set save profile loading
export const setSaveProfileLoading = (isLoading) => {
    return {
        type: SAVE_PROFILE_LOADING,
        payload: isLoading
    }
}

// set get user loading
export const setGetUserLoading = (isLoading) => {
    return {
        type: GET_USER_LOADING,
        payload: isLoading
    }
}