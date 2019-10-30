import { GET_ERRORS, SET_CURRENT_USR } from './types';
import { setJwtTokenHeader } from '../utils/jwtUtil';
import jwt_decode from 'jwt-decode';

export const loginAction = token => async dispatch => {
	try {
		console.log('login action called');

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
		console.log('Dispatching errors', error.response.data);
		dispatch({
			type: GET_ERRORS,
			payload: error.response.data,
		});
	}
};
// export const loginAction = () => async dispatch => {
// 	try {
// 		console.log('login action called');
// 		const token = await gigety.post(FACEBOOK_AUTH_URL);
// 		console.log('TOKEN:::::', token);
// 		//store token in local storage
// 		localStorage.setItem('jwtToken', token);

// 		//set token in header
// 		setJwtTokenHeader(token);

// 		//decode token on React
// 		const decoded = jwt_decode(token);

// 		//dispatch to security reducer
// 		dispatch({
// 			type: SET_CURRENT_USR,
// 			payload: decoded,
// 		});
// 	} catch (error) {
// 		console.log('Dispatching errors', error);
// 		dispatch({
// 			type: GET_ERRORS,
// 			payload: error,
// 		});
// 	}
// };

export const logout = () => dispatch => {
	console.log('Loggin out...');
	localStorage.removeItem('jwtToken');
	setJwtTokenHeader(false);
	dispatch({
		type: SET_CURRENT_USR,
		payload: null,
	});
};
