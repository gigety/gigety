import { GET_CURRENT_USR } from 'redux/actions/types';

const initialState = {};

export default function(state = initialState, action) {
	console.log('gigUserReducer action: ', action);
	console.log('state: ', state);
	switch (action.type) {
		case GET_CURRENT_USR:
			console.log('useruseruser::: ', action.payload);
			console.log(action.payload ? true : false);
			return {
				giguser: action.payload,
			};
		default:
			return state;
	}
}
