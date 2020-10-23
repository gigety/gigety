import { GET_CURRENT_USR_ACCOUNT, GET_ERRORS } from 'redux/actions/types';

const initialState = {};

export default function (state = initialState, action) {
	switch (action.type) {
		case GET_CURRENT_USR_ACCOUNT:
			return {
				giguserAccount: action.payload,
			};
		case GET_ERRORS:
			return {
				errors: action.payload,
			};
		default:
			return state;
	}
}
