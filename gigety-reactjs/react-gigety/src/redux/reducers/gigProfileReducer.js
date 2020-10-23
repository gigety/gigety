import { GET_USER_PROFILE, SEARCH_ALL_PROFILES, FOUND_PROFILE_SEARCH_RESULTS } from '../actions/types';

const initialState = {};
export default function (state = initialState, action) {
	switch (action.type) {
		case GET_USER_PROFILE:
			return {
				...state,
				giguserProfile: action.payload,
			};
		case SEARCH_ALL_PROFILES:
			return {
				profileSearchTerm: action.payload,
			};
		case FOUND_PROFILE_SEARCH_RESULTS:
			return {
				profileSearchResults: action.payload,
			};
		default:
			return state;
	}
}
