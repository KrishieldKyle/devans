import { GET_USER, SET_USER_PROFILE, SAVE_PROFILE_LOADING, GET_USER_LOADING, UPDATE_USER_TITLES, UPDATE_USER_TITLES_LOADING, UPDATE_USER_TECHNOLOGIES, UPDATE_USER_TECHNOLOGIES_LOADING } from '../actions/types';

const initialState = {
    profile: {},
    titles: [],
    technologies: [],
    isSaveProfileLoading: false,
    isGetUserLoading: false,
    isUpdateUserTitlesLoading: false,
    isUpdateUserTechnologiesLoading: false,
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
        case UPDATE_USER_TITLES: 
            return {
                ...state,
                titles: action.payload
            }
        case UPDATE_USER_TECHNOLOGIES: 
            return {
                ...state,
                technologies: action.payload
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
        case UPDATE_USER_TITLES_LOADING: 
            return {
                ...state,
                isUpdateUserTitlesLoading: action.payload
            }
        case UPDATE_USER_TECHNOLOGIES_LOADING: 
            return {
                ...state,
                isUpdateUserTechnologiesLoading: action.payload
            }
        default:
            return state;
    }
}