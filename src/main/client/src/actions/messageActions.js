import { SET_MESSAGE, CLOSE_MESSAGE } from './types'

// set message
export const setMessage = (message) => {
    return {
        type: SET_MESSAGE,
        payload: message
    }
}

// close message
export const closeMessage = (isMessageClosed) => {
    return {
        type: CLOSE_MESSAGE,
        payload: isMessageClosed
    }
}