import { DEVELOPERS_LOADING, GET_DEVELOPERS } from './types'
import axios from 'axios';

import {setLoading} from "./commonActions"

// Register User
export const getDevelopers = () => dispatch => {
    dispatch(setLoading(DEVELOPERS_LOADING, true))
    axios.get('/api/user/')
        .then(res => {
            const developers = res.data.users;
            
            dispatch({
                type: GET_DEVELOPERS,
                payload: developers
            })
            dispatch(setLoading(DEVELOPERS_LOADING, false));
        })
}