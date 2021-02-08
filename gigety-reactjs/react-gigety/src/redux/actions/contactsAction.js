import gigety from 'apis/gigety';
import { GET_ERRORS, UPDATE_CONTACT_LIST } from './types';

export const findUserContacts = (userId) => async (dispatch) => {
	try {
		const response = await gigety.get(`/contacts/${userId}`);
		dispatch({
			type: UPDATE_CONTACT_LIST,
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
