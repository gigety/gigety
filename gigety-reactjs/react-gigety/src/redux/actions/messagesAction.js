import { GET_ERRORS, UPDATE_CHAT_MESSAGES } from './types';
import gigetyMessenger from 'apis/gigetyMessenger';

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

export const findMessagesFor121Chat = (currentUserId, profileId) => async (dispatch) => {
	try {
		console.log('OFFFFF TO FIND SOME MESSAGES');
		const response = await gigetyMessenger.get(`/messages/${currentUserId}/${profileId}`);
		console.log('FOUND MESSSAGES ::');
		console.log(response.data);
		dispatch({
			type: UPDATE_CHAT_MESSAGES,
			payload: response.data,
		});
	} catch (error) {
		console.error('ERROR :: ', error);
		dispatch({
			type: GET_ERRORS,
			payload: error,
		});
	}
};
