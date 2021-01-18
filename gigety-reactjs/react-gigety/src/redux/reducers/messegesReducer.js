import { UPDATE_CHAT_MESSAGES } from '../actions/types';

export default function messagesReducer(state, acion) {
	switch (action.type) {
		case UPDATE_CHAT_MESSAGES: {
			return {
				...state,
				messages: action.payload,
			};
		}
	}
}
