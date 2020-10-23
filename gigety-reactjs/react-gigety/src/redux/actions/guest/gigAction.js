import gigety from 'apis/gigety';
import { FOUND_GIG_SEARCH_RESULTS, GET_GIG } from '../types';

export const searchAllGigs = (searchTerm) => async (dispatch) => {
	try {
		//TODO: Pagination currently hardcoded, should be handled correct instead
		const response = await gigety.post(`/pnode/gigs/100/0`, {
			searchTerm,
		});
		console.log('Found gigs: ', response);
		dispatch({ type: FOUND_GIG_SEARCH_RESULTS, payload: response.data });
	} catch (error) {
		console.error(error);
		throw error;
	}
};

export const getGigForGuest = (id) => async (dispatch, getState) => {
	try {
		console.log('sdfbsdfbsdfbsadfbsdfb');
		const response = await gigety.get(`/pnode/gigs/${id}`);
		dispatch({ type: GET_GIG, payload: response.data });
	} catch (error) {
		console.error(`Error retrieving gig with id : ${id}`, error);
		throw error;
	}
};
