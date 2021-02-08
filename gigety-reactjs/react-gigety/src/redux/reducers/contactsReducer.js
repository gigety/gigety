import { UPDATE_CONTACT_LIST } from 'redux/actions/types';

const initialState = {};
export default function contactsReducer(state = initialState, action) {
	switch (action.type) {
		case UPDATE_CONTACT_LIST: {
			return {
				...state,
				contacts: action.payload,
			};
		}
		default:
			return state;
	}
}
