import { DEVELOPERS_LOADING, GET_DEVELOPERS } from './types'
import axios from 'axios';

// Register User
export const getDevelopers = () => dispatch => {
    dispatch(setDevelopersLoading(true))
    axios.get('/api/user/')
        .then(res => {
            const developers = res.data.users;
            
            dispatch({
                type: GET_DEVELOPERS,
                payload: developers
            })
            dispatch(setDevelopersLoading(false));
        })
}

// Set Developers loading
export const setDevelopersLoading = (isLoading) => {
    
    return {
        type: DEVELOPERS_LOADING,
        payload: isLoading
    }
}

