import { GET_ERRORS, GET_CURRENT_USR } from './types';
import gigety from '../apis/gigety';
export const getCurrentUser = () => async dispatch => {
	try {
		console.log('getting current user...');
		const response = await gigety.get('/user/me');
		console.log('Found User :: ', response);
		dispatch({
			type: GET_CURRENT_USR,
			payload: response.data,
		});
	} catch (error) {
		console.log('Dispatching errors', error.response.data);
		dispatch({
			type: GET_ERRORS,
			payload: error.response.data,
		});
	}
};
