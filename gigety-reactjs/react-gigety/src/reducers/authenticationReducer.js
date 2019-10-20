import { SET_CURRENT_USR } from '../actions/types';

const initialState = {
	validToken: false,
	user: {},
};

export default function(state = initialState, action) {
	switch (action.type) {
		case SET_CURRENT_USR:
			return {
				...state,
				validToken: action.payload ? true : false,
				user: action.payload,
			};
		default:
			return state;
	}
}
