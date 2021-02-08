import { GET_CURRENT_ADDRESS, SET_CURRENT_ADDRESS } from '../actions/types';

const initialState = {};

export default function locationReducer(state = initialState, action) {
	switch (action.type) {
		case GET_CURRENT_ADDRESS:
			return {
				...state,
				currentAddress: action.payload,
			};
		case SET_CURRENT_ADDRESS:
			return {
				...state,
				currentAddress: action.payload,
			};

		default:
			return state;
	}
}
