import {
	SET_PROFILE_CREATED_SUCCESS,
	SET_PROFILE_CREATED_ERROR,
	SET_PROFILE_CREATED_WAITING,
	GET_USER_PROFILE,
} from './types';
import gigety from 'apis/gigety';

export const postGigProfile = (userProfile, images = [], history) => async (dispatch) => {
	const data = new FormData();
	data.append('userProfile', userProfile);
	data.append('file', images[0]);
	try {
		const postedProfile = await gigety.post('/profiles/createProfile', data, {
			headers: { 'content-type': 'multipart/form-data' },
		});
		//TODO: Still needs to handle slow posts. I am thinking maybe have a spinner in a
		//newly allocated profileLable on account page
		dispatch({ type: SET_PROFILE_CREATED_WAITING, payload: false });
		dispatch({ type: SET_PROFILE_CREATED_SUCCESS, payload: postedProfile });
	} catch (error) {
		console.error('Error creating user profile : ', error);
		//TODO: THis needs to be handled on UI
		dispatch({ type: SET_PROFILE_CREATED_ERROR, payload: error });
		throw error;
	}
	dispatch({ type: SET_PROFILE_CREATED_WAITING, payload: true });
	history.push('/user/account');
};

export const addProfileToProfileList = (userProfile) => (dispatch, getState) => {
	const { profileList } = getState().userAccount;
};

export const getUserProfile = (id) => async (dispatch, getState) => {
	try {
		const response = await gigety.get(`/pnode/profiles/${id}`);
		dispatch({ type: GET_USER_PROFILE, payload: response.data });
	} catch (error) {
		throw error;
	}
};
