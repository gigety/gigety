import {
	GET_USER_PROFILE,
	SEARCH_ALL_PROFILES,
	FOUND_PROFILE_SEARCH_RESULTS,
	REMOVE_PROFILE_SUCCESS,
} from '../actions/types';

const initialState = {};
export default function gigProfileReducer(state = initialState, action) {
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
		case REMOVE_PROFILE_SUCCESS:
			return {
				profileRemovedMessage: action.payload,
			};
		default:
			return state;
	}
}
