import { GET_TITLES, GET_TITLES_LOADING} from './types'
import axios from 'axios';

import { getErrors} from "./errorActions";
import { setLoading} from "./commonActions";

// get titles
export const getTitles = () => dispatch => {
    dispatch(setLoading(GET_TITLES_LOADING, true));
    axios.get('/api/title/')
        .then(res => {
            dispatch({
                type: GET_TITLES,
                payload: res.data.titles
            })
            dispatch(setLoading(GET_TITLES_LOADING, false));
        })
        .catch(err => {
            dispatch(setLoading(GET_TITLES_LOADING, false));
            dispatch(getErrors(err.response.data))
        })
}