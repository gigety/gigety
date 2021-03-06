import { GET_CURRENT_USR, GET_ERRORS } from 'redux/actions/types';

const initialState = {};

export default function gigUserReducer(state = initialState, action) {
	switch (action.type) {
		case GET_CURRENT_USR:
			return {
				giguser: action.payload,
			};

		case GET_ERRORS:
			return {
				...state,
				errors: action.payload,
			};
		default:
			return state;
	}
}
