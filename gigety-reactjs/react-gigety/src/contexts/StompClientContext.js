import React, { createContext } from 'react';
import EventEmitter from 'eventemitter3';
import { GIGETY_MESSENGER_STOMP_URL } from '../constants';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const StompClientContext = createContext(null);
export { StompClientContext };

const MessageContext = ({ children }) => {
	let _stompClient = null;
	let wrappedStompClient = null;
	const log = (str) => console.log(str);
	const stompEvent = new EventEmitter();
	const stompEventTypes = {
		Connect: 0,
		Disconnect: 1,
		Error: 2,
		WebSocketClose: 3,
		WebSocketError: 4,
	};
	const activateStompClient = (url = GIGETY_MESSENGER_STOMP_URL, login, passcode, host) => {
		log('Creating StompClient ...');

		_stompClient = new Client({
			brokerURL: url,
			connectHeaders: { login, passcode, host },
			debug: (str) => str,
			reconnectDelay: 200,
			heartbeatIncoming: 500,
			heartbeatOutgoing: 4000,
			logRawCommunication: false,
			webSocketFactory: () => {
				return SockJS(url);
			},
			onStompError: (frame) => {
				log('Gigety Stomp Error', frame);
				stompEvent.emit(stompEventTypes.Error);
			},
			onConnect: (frame) => {
				log('Gigety Stomp Connect', frame);
				stompEvent.emit(stompEventTypes.Connect);
			},
			onDisconnect: (frame) => {
				log('Gigety Stomp Disconnect', frame);
				stompEvent.emit(stompEventTypes.Disconnect);
			},
			onWebSocketClose: (frame) => {
				log('Gigety WebSocket Close', frame);
				stompEvent.emit(stompEventTypes.WebSocketClose);
			},
			onWebSocketError: (frame) => {
				log('Gigety WebSocket Error', frame);
				stompEvent.emit(stompEventTypes.WebSocketError);
			},
		});
		_stompClient.activate();
		return _stompClient;
	};
	const getStompClient = () => {
		return _stompClient;
	};
	const removeStompClient = () => {
		if (_stompClient) {
			log('Deacitvating StompClient');
			_stompClient.deactivate();
			_stompClient = null;
		}
	};
	const addStompEventListener = (eventType, emitted, context, isOnce) => {
		log('Adding Event Type {}', eventType);
		if (isOnce) {
			stompEvent.once(eventType, emitted, context);
		} else {
			stompEvent.on(eventType, emitted, context);
		}
	};
	const removeStompEventListener = (eventType, emmited, context) => {
		log('Removing Event Listener for {}', eventType);
		stompEvent.removeListener(eventType, emmited, context);
	};
	wrappedStompClient = {
		activateStompClient,
		removeStompClient,
		getStompClient,
		addStompEventListener,
		removeStompEventListener,
		stompEventTypes,
	};

	log(`CONFIGURATION:::: wrappedStompClient = ${wrappedStompClient}`);
	log('Activating StompClient in StompClientContext ...');
	activateStompClient();
	return <StompClientContext.Provider value={wrappedStompClient}>{children}</StompClientContext.Provider>;
};

export default MessageContext;
