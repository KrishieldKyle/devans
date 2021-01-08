import { CLEAR_MESSAGE, SET_MESSAGE, CLOSE_MESSAGE } from '../actions/types';

const getDefaultState = () => {
    return {
        message: "",
        isSuccess: true,
        isMessageShowing: false
    }
  };

const initialState = getDefaultState();


export default function (state = initialState, action) {
    switch (action.type) {
        case SET_MESSAGE:
            return action.payload
        case CLOSE_MESSAGE:
            return getDefaultState();
        default:
            return state;
    }
}