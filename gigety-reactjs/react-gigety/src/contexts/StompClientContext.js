import React, { createContext } from 'react';
import { useDispatch } from 'react-redux';
import { GIGETY_MESSENGER_URL } from '../constants';
import { updateChatMessages } from '../redux/actions/messagesAction';

const StompClientContext = createContext(null);
export { StompClientContext };
const MessageContext = ({ children }) => {
	let stompClient = null;
	let wrappedStompClient = null;

	const onConnected = () => {
		console.log('SockJS iiiiiiissssss COnnected to STOMP protocol');
		console.log('may be a good place to subscribe to user specific messages so they can be notified');
		//stompClient.subscribe(`/user/${profile.id}/queue/messages`, onMessageRecieved);
		//stompClient.subscribe(`/messenger/user`, onMessageRecieved);
	};

	const onError = (error) => {
		console.error('GIGETY ERROR in STompCLientContext');
		console.error(error);
	};

	//Connect stompClient to gigety-ws-service
	const stomp = require('stompjs');
	let SockJS = require('sockjs-client');
	SockJS = new SockJS(GIGETY_MESSENGER_URL + '/ws');
	//SockJS = new SockJS('http://localhost:7070/messenger/ws', null, {});
	stompClient = stomp.over(SockJS);
	stompClient.connect({}, onConnected, onError);

	const dispatch = useDispatch();

	const sendChatMessage = (message) => {
		console.log(`sending Message ${message}`);

		stompClient.send('/msg/chat', {}, JSON.stringify(message));
		dispatch(updateChatMessages(message));
	};
	wrappedStompClient = {
		stompClient,
		sendChatMessage,
	};

	console.log(`CONFIGURATION:::: wrappedStompClient = ${wrappedStompClient}`);
	return <StompClientContext.Provider value={wrappedStompClient}>{children}</StompClientContext.Provider>;
};

export default MessageContext;
