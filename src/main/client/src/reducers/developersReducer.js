import { GET_DEVELOPERS, DEVELOPERS_LOADING } from '../actions/types'


const initialState = {
    developers: [],
    isLoading: false
}

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_DEVELOPERS:
            return {
                ...state,
                developers: action.payload
            }
        case DEVELOPERS_LOADING:
            return {
                ...state,
                isLoading: action.payload
            }
        default:
            return state;
    }
}
