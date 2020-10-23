import { GET_ERRORS, SET_CURRENT_USR } from './types';
import { setJwtTokenHeader } from 'utils/jwtUtil';
import jwt_decode from 'jwt-decode';

export const loginAction = (token) => async (dispatch) => {
	try {
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
		console.error('Dispatching errors', error.response.data);
		dispatch({
			type: GET_ERRORS,
			payload: error.response.data,
		});
	}
};

export const logout = () => (dispatch) => {
	localStorage.removeItem('jwtToken');
	setJwtTokenHeader(false);
	dispatch({
		type: SET_CURRENT_USR,
		payload: null,
	});
};
