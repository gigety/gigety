import React, { createContext } from 'react';
import EventEmitter from 'eventemitter3';
import { GIGETY_MESSENGER_STOMP_URL } from '../constants';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const StompClientContext = createContext(null);
export { StompClientContext };

/**
 * Context to provide methods to activate, get, remove, add / remove listeners
 * to a Client object of the @stomp/stompjs library
 * @param {*} param0
 */
const MessageContext = ({ children }) => {
	let _stompClient = null;
	let wrappedStompClient = null;
	const log = (str) => console.log(str);
	const warn = (str) => console.warn(str);
	const error = (str) => console.error(str);
	const stompEvent = new EventEmitter();
	const stompEventTypes = {
		Connect: 0,
		Disconnect: 1,
		Error: 2,
		WebSocketClose: 3,
		WebSocketError: 4,
	};
	const activateStompClient = ({
		url = GIGETY_MESSENGER_STOMP_URL,
		login,
		passcode,
		host,
		debug = (str) => log(str),
	}) => {
		log('Creating StompClient ...');

		_stompClient = new Client({
			brokerURL: url,
			connectHeaders: { login, passcode, host },
			debug: debug,
			reconnectDelay: 200,
			heartbeatIncoming: 500,
			heartbeatOutgoing: 4000,
			logRawCommunication: false,
			webSocketFactory: () => {
				return SockJS(url);
			},
			onStompError: (frame) => {
				error(`Gigety Stomp Error :: ${frame}`);
				error(frame);
				stompEvent.emit(stompEventTypes.Error);
			},
			onConnect: (frame) => {
				log(`Gigety Stomp Connect :: ${frame}`);
				log(frame);
				stompEvent.emit(stompEventTypes.Connect);
			},
			onDisconnect: (frame) => {
				warn(`Gigety Stomp Disconnect :: ${frame}`);
				warn(frame);
				stompEvent.emit(stompEventTypes.Disconnect);
			},
			onWebSocketClose: (frame) => {
				log(`Gigety WebSocket Close :: ${frame}`);
				log(frame);
				stompEvent.emit(stompEventTypes.WebSocketClose);
			},
			onWebSocketError: (frame) => {
				error(`Gigety WebSocket Error :: ${frame}`);
				error(frame);
				stompEvent.emit(stompEventTypes.WebSocketError);
			},
		});
		_stompClient.activate();
		return _stompClient;
	};
	const deactivateStompClient = () => {
		if (_stompClient) {
			log('Deactivating StompClient');
			_stompClient.deactivate();
		}
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
	const addStompEventListener = ({ eventType, emitted, context, isOnce }) => {
		log(`Adding Event Type ${eventType}`);
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
		deactivateStompClient,
		stompEventTypes,
	};

	log(`CONFIGURATION:::: wrappedStompClient = ${wrappedStompClient}`);
	log('Activating StompClient in StompClientContext ...');
	activateStompClient({ debug: (f) => f });
	return <StompClientContext.Provider value={wrappedStompClient}>{children}</StompClientContext.Provider>;
};

export default MessageContext;
