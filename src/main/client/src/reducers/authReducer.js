import isEmpty from '../validation/is-empty';
import { SET_CURRENT_USER, AUTH_LOADING } from '../actions/types'


const initialState = {
    isAuthenticated: false,
    user: {},
    isLoading: false
}

export default function (state = initialState, action) {
    switch (action.type) {
        case SET_CURRENT_USER:
            return {
                ...state,
                isAuthenticated: !isEmpty(action.payload),
                user: action.payload
            }
        case AUTH_LOADING:
            return {
                ...state,
                isLoading: action.payload
            }
        default:
            return state;
    }
}
