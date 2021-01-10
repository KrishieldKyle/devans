import { GET_USER, GET_ERRORS, SET_USER_PROFILE } from './types'

import axios from "axios";

// get user
export const getUser = (userId) => dispatch => {
    axios.get(`/api/user/${userId}`)
        .then(res => {
            dispatch({
                type: GET_USER,
                payload: res.data.user
            })
        })
        .catch(err => {
            dispatch({
                type: GET_ERRORS,
                payload: err.response.data
            })
        })
}

export const addOrUpdateProfile = (profileData) => dispatch => {
    axios.post(`/api/profile/`, profileData)
        .then(res => {
            dispatch({
                type: SET_USER_PROFILE,
                payload: res.data.profile
            })
        })
        .catch(err => {
            dispatch({
                type: GET_ERRORS,
                payload: err.response.data
            })
        })
}

