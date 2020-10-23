import { GET_ERRORS, GET_CURRENT_USR, GET_CURRENT_USR_ACCOUNT } from './types';
import gigety from 'apis/gigety';
import { setJwtTokenHeader } from 'utils/jwtUtil';
export const getCurrentUser = () => async (dispatch) => {
	try {
		console.log('GETTING USER');
		const response = await gigety.get('/user/me');
		dispatch({
			type: GET_CURRENT_USR,
			payload: response.data,
		});
	} catch (error) {
		console.error('Error getting user info from /user/me :: ', error);
		localStorage.removeItem('jwtToken');
		setJwtTokenHeader(false);
		dispatch({
			type: GET_ERRORS,
			payload: error,
		});
	}
};
export const getUserAccount = () => async (dispatch) => {
	try {
		console.log('GETTING USER ACCOUNT');
		const response = await gigety.get('/userAccount');
		dispatch({
			type: GET_CURRENT_USR_ACCOUNT,
			payload: response.data,
		});
	} catch (error) {
		console.error('ERROR :: ', error);
		dispatch({
			type: GET_ERRORS,
			payload: error,
		});
	}
};
