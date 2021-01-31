import { GET_TECHNOLOGIES, GET_TECHNOLOGIES_LOADING } from '../actions/types'

const initialState = {
    technologies: [],
    isGetTechnologiesLoading: false
}

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_TECHNOLOGIES:
            return {
                ...state,
                technologies: action.payload
            }
        case GET_TECHNOLOGIES_LOADING:
            return {
                ...state,
                isGetTechnologiesLoading: action.payload
            }
        default:
            return state;
    }
}