import { GET_ERRORS, UPDATE_CHAT_MESSAGES, UPDATE_MESSAGE_NOTIFICATIONS, UPDATE_NEW_USER_MESSAGES } from './types';
import gigetyMessenger from 'apis/gigetyMessenger';
import _ from 'lodash';
export const updateChatMessages = (message) => (dispatch, getState) => {
	//temp setting msg.id only to use as key for list in ChatMessenger.
	//This id does not propagate to mongo
	message.id = _.random(6, 12, true);
	const messages = [...getState().messages.messages, message];
	dispatch({
		type: UPDATE_CHAT_MESSAGES,
		payload: messages,
	});
};

export function sendMessage(msg_id, msg) {
	//use stompclient to send message
	return {};
}

export const findMessagesFor121Chat = (currentUserId, profileId) => async (dispatch) => {
	try {
		const response = await gigetyMessenger.get(`/messages/${currentUserId}/${profileId}`);
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

export const updateUserMessageNotifications = (notification) => (dispatch) => {
	console.log('UPDATING NOTIFICATION ----- ', notification);
	dispatch({
		type: UPDATE_MESSAGE_NOTIFICATIONS,
		payload: notification,
	});
};

export const findUserMessageNotifications = (userId) => async (dispatch) => {
	try {
		const response = await gigetyMessenger.get(`/user/${userId}/queue/messages`);
		console.log('RESPONSEEEE :: ', response);
		dispatch({
			type: UPDATE_MESSAGE_NOTIFICATIONS,
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

export const findNewUserMessages = (userId) => async (dispatch) => {
	try {
		const response = await gigetyMessenger.get(`/messages/status/RECEIVED/${userId}`);
		console.log('Found new user messages: ', response.data);
		dispatch({
			type: UPDATE_NEW_USER_MESSAGES,
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
