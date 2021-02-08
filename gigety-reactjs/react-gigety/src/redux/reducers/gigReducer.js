import { GET_GIG, SEARCH_ALL_GIGS, FOUND_GIG_SEARCH_RESULTS } from '../actions/types';

const initialState = {};
export default function gigReducer(state = initialState, action) {
	switch (action.type) {
		case GET_GIG:
			return {
				...state,
				gig: action.payload,
			};
		case SEARCH_ALL_GIGS:
			return {
				gigSearchTerm: action.payload,
			};
		case FOUND_GIG_SEARCH_RESULTS:
			return {
				gigSearchResults: action.payload,
			};
		default:
			return state;
	}
}
