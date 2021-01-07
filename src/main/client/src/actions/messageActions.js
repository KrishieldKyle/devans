import { SET_MESSAGE, CLEAR_MESSAGE } from './types'

// clear message
export const clearMessage = () => {
    return {
        type: CLEAR_MESSAGE
    }
}

// set message
export const setMessage = (message) => {
    return {
        type: SET_MESSAGE,
        payload: message
    }
}
