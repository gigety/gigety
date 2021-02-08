import { SET_CURRENT_USR } from 'redux/actions/types';

const initialState = {
	validToken: false,
	user: {},
};

export default function authenticationReducer(state = initialState, action) {
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
