import { CLEAR_MESSAGE, SET_MESSAGE } from '../actions/types';

const getDefaultState = () => {
    return {
        message: "",
        isSuccess: true
    }
  };

const initialState = getDefaultState();


export default function (state = initialState, action) {
    switch (action.type) {
        case SET_MESSAGE:
            return action.payload
        case CLEAR_MESSAGE:
            return getDefaultState();
        default:
            return state;
    }
}