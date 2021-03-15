import React, { useContext, useEffect, useState, useCallback } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages, useMessenger } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat } from 'redux/actions/messagesAction';
import { StompClientContext } from 'contexts/StompClientContext';
import { GIGETY_MESSENGER_URL } from '../../../constants';
import { useActiveContact } from 'redux/hooks/useContacts';
const ChatMessenger = () => {
	const { giguser } = useSelector((state) => state.giguser);
	let activeContact = useActiveContact();
	activeContact = activeContact ? activeContact : {};
	console.log('Active COntact :::: ', activeContact);
	const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	//useMessenger(giguser, activeContact);
	//onst { stompClient } = useContext(StompClientContext);
	const dispatch = useDispatch();

	useEffect(() => {
		const stomp = require('stompjs');
		let SockJS = require('sockjs-client');
		SockJS = new SockJS(GIGETY_MESSENGER_URL + '/ws');
		const stompClient = stomp.over(SockJS);
		const onError = (error) => {
			console.log('ERRRRRRRRRRRRRRR : ', error);
		};

		const onConnected = () => {
			const onMessageRecieved = (msg) => {
				const notification = JSON.parse(msg.body);
				console.log('Active COntact where are you ??????? ', activeContact);
				console.log('Notification :::::::::::::: ', notification);
				if (activeContact.contactId === notification.senderId) {
					console.log('WE FOUND A MATCH');
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const { id } = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
			console.log('IIIISSSS COOONN NNEEECCCCTTTTED v wwwwwwaaaaayyyy:: ', stompClient.connected);
			return () => {
				console.log(`here we unsubscibe to id ${id}, you best check this is proper way to unsubscribe`);
				stompClient.unsubscribe(id);
			};
		};
		stompClient.connect({}, onConnected, onError);
	}, [giguser, dispatch, activeContact]);
	return (
		<>
			<ScrollToBottom className="messages">
				<List>
					{messages
						? messages.map((msg) => (
								<List.Item>
									<ContactAvatar size="med" contact={activeContact} />
									<List.Content>
										<List.Description>{msg.content}</List.Description>
									</List.Content>
								</List.Item>
						  ))
						: ''}
				</List>
			</ScrollToBottom>
			<MessageInput activeContact={activeContact} giguser={giguser}></MessageInput>
		</>
	);
};

ChatMessenger.propTypes = {
	activeContact: {
		contactId: PropTypes.string,
	},
};
ChatMessenger.defaultProps = {
	activeContact: { contactId: '0' },
};
export default ChatMessenger;
