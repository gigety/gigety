import { SET_CURRENT_USR } from 'redux/actions/types';

const initialState = {
	validToken: false,
	user: {},
};

export default function(state = initialState, action) {
	switch (action.type) {
		case SET_CURRENT_USR:
			console.log('useruseruser::: ', action.payload);
			console.log(action.payload ? true : false);
			return {
				...state,
				validToken: action.payload ? true : false,
				user: action.payload,
			};
		default:
			return state;
	}
}
