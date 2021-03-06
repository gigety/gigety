import { UPDATE_CHAT_MESSAGES, UPDATE_MESSAGE_NOTIFICATIONS, UPDATE_NEW_USER_MESSAGES } from '../actions/types';
const initialState = {};

export default function messagesReducer(state = initialState, action) {
	switch (action.type) {
		case UPDATE_CHAT_MESSAGES: {
			return {
				...state,
				messages: action.payload,
			};
		}
		case UPDATE_NEW_USER_MESSAGES: {
			return { ...state, newMessages: action.payload };
		}
		case UPDATE_MESSAGE_NOTIFICATIONS: {
			return { ...state, messageNotifications: action.payload };
		}
		default:
			return state;
	}
}
