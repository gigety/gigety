import axios from 'axios';
import { GET_ERRORS, SET_CURRENT_USR } from './types';
import { setJwtTokenHeader } from 'utils/jwtUtil';
import jwt_decode from 'jwt-decode';

export const createNewUser = (newUser, history) => async dispatch => {
	try {
		await axios.post('/api/users/register', newUser);
		history.push('/login');
		dispatch({
			type: GET_ERRORS,
			payload: {},
		});
	} catch (error) {
		dispatch({
			type: GET_ERRORS,
			payload: error.response.data,
		});
	}
};

export const login = LoginRequest => async dispatch => {
	try {
		//post => Login Request
		const res = await axios.post('/api/users/login', LoginRequest);

		//extract token
		const { token } = res.data;

		//store token in local storage
		localStorage.setItem('jwtToken', token);

		//set token in header
		setJwtTokenHeader(token);

		//decode token on React
		const decoded = jwt_decode(token);

		//dispatch to security reducer
		dispatch({
			type: SET_CURRENT_USR,
			payload: decoded,
		});
	} catch (error) {
		console.log('Dispatching errors');
		console.dir(error.response.data);
		dispatch({
			type: GET_ERRORS,
			payload: error.response.data,
		});
	}
};

export const logout = () => dispatch => {
	localStorage.removeItem('jwtToken');
	setJwtTokenHeader(false);
	dispatch({
		type: SET_CURRENT_USR,
		payload: {},
	});
};
