import { GET_TITLES, GET_TITLES_LOADING } from '../actions/types'

const initialState = {
    titles: [],
    isGetTitlesLoading: false
}

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_TITLES:
            return {
                ...state,
                titles: action.payload
            }
        case GET_TITLES_LOADING:
            return {
                ...state,
                isGetTitlesLoading: action.payload
            }
        default:
            return state;
    }
}