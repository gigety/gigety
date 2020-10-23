import { SET_GIG_CREATED_SUCCESS, SET_GIG_CREATED_ERROR, SET_GIG_CREATED_WAITING } from './types';
import gigety from 'apis/gigety';
export const postGig = (gig, history) => async (dispatch) => {
	const data = new FormData();
	data.append('gig', gig);
	try {
		const postedGig = await gigety.post('/gigs/create', JSON.parse(gig));
		//TODO: Still needs to handle slow posts. I am thinking maybe have a spinner in a
		//newly allocated profileLable on account page
		dispatch({ type: SET_GIG_CREATED_WAITING, payload: false });
		dispatch({ type: SET_GIG_CREATED_SUCCESS, payload: postedGig });
	} catch (error) {
		console.error('Error creating gig : ', error);
		//TODO: THis needs to be handled on UI
		dispatch({ type: SET_GIG_CREATED_ERROR, payload: error });
		throw error;
	}
	dispatch({ type: SET_GIG_CREATED_WAITING, payload: true });
	history.push('/gigs');
};
