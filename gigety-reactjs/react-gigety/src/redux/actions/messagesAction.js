import { UPDATE_CHAT_MESSAGES } from './types';
export function updateChatMessages(messages) {
	return {
		type: UPDATE_CHAT_MESSAGES,
		payload: messages,
	};
}

export function sendMessage(msg_id, msg) {
	//use stompclient to send message
	return {};
}
