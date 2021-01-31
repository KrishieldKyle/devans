import { GET_TECHNOLOGIES, GET_TECHNOLOGIES_LOADING} from './types'
import axios from 'axios';

import { getErrors} from "./errorActions";
import { setLoading} from "./commonActions";

// get technologies
export const getTechnologies = () => dispatch => {
    dispatch(setLoading(GET_TECHNOLOGIES_LOADING, true));
    axios.get('/api/technology/')
        .then(res => {
            dispatch({
                type: GET_TECHNOLOGIES,
                payload: res.data.technologies
            })
            dispatch(setLoading(GET_TECHNOLOGIES_LOADING, false));
        })
        .catch(err => {
            dispatch(setLoading(GET_TECHNOLOGIES_LOADING, false));
            dispatch(getErrors(err.response.data))
        })
}