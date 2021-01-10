import { GET_USER, SET_USER_PROFILE } from '../actions/types';

const initialState = {
    profile: {},
    titles: [],
    technologies: [] 
}

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_USER:
            return action.payload
        case SET_USER_PROFILE:

        default:
            return state;
    }
}