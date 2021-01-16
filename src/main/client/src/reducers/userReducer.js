import { GET_USER, SET_USER_PROFILE, SAVE_PROFILE_LOADING, GET_USER_LOADING } from '../actions/types';

const initialState = {
    profile: {},
    titles: [],
    technologies: [],
    isSaveProfileLoading: false,
    isGetUserLoading: false
}

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_USER:
            return action.payload
        case SET_USER_PROFILE:
            return {
                ...state,
                profile: action.payload
            }
        case SAVE_PROFILE_LOADING: 
            return {
                ...state,
                isSaveProfileLoading: action.payload
            }
        case GET_USER_LOADING: 
            return {
                ...state,
                isGetUserLoading: action.payload
            }
        default:
            return state;
    }
}