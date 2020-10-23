import { GET_USER_PROFILE, FOUND_PROFILE_SEARCH_RESULTS } from '../types';
import gigety from 'apis/gigety';

export const addProfileToProfileList = (userProfile) => (dispatch, getState) => {
	const { profileList } = getState().userAccount;
};

export const getUserProfileForGuest = (id) => async (dispatch, getState) => {
	try {
		const response = await gigety.get(`/pnode/profiles/${id}`);
		dispatch({ type: GET_USER_PROFILE, payload: response.data });
	} catch (error) {
		throw error;
	}
};

export const searchAllProfiles = (searchTerm) => async (dispatch) => {
	try {
		//TODO: Pagination currently hardcoded, should be handled correct instead
		const response = await gigety.post(`/pnode/profiles/100/0`, {
			searchTerm,
		});
		console.log('Found Profiles: ', response);
		dispatch({ type: FOUND_PROFILE_SEARCH_RESULTS, payload: response.data });
	} catch (error) {
		console.error(error);
		throw error;
	}
};
