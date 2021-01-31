import { CLEAR_ERRORS, GET_ERRORS } from './types'

// clear errors
export const clearErrors = () => {
    return {
        type: CLEAR_ERRORS
    }
}

// clear errors
export const getErrors = (errors) => {
    return {
        type: GET_ERRORS,
        payload: errors
    }
}
