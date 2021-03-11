import { UPDATE_ACTIVE_CONTACT, UPDATE_CONTACT_LIST } from 'redux/actions/types';

const initialState = { contacts: [], activeContact: null };
export default function contactsReducer(state = initialState, action) {
	switch (action.type) {
		case UPDATE_CONTACT_LIST: {
			return {
				...state,
				contacts: action.payload,
			};
		}
		case UPDATE_ACTIVE_CONTACT: {
			return {
				...state,
				activeContact: action.payload,
			};
		}
		default:
			return state;
	}
}
