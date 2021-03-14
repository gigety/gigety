import React, { useContext, useEffect, useState } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages, useMessenger } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat } from 'redux/actions/messagesAction';
import { StompClientContext } from 'contexts/StompClientContext';
const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);
	console.log('GGGGGGGGGGGGGGGGGGGGGGGGGGGGGLEN');
	activeContact = activeContact ? activeContact : {};
	const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	//useMessenger(giguser, activeContact);
	const { stompClient } = useContext(StompClientContext);
	const dispatch = useDispatch();
	console.log('1111111111112222223333334344444444');
	useEffect(() => {
		if (stompClient.connected) {
			const onMessageRecieved = (msg) => {
				console.log('RECEIVED MESSAGE +++++++++++++++++++', activeContact);
				const notification = JSON.parse(msg.body);
				if (activeContact.contactId === notification.senderId) {
					console.log('WE FOUND A MATCH');
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const { id } = stompClient.subscribe(`/user/${giguser.id}/topic/messages`, onMessageRecieved);

			return () => {
				console.log(`here we unsubscibe to id ${id}, you best check this is proper way to unsubscribe`);
				stompClient.unsubscribe(id);
			};
		}
	}, [giguser, activeContact, stompClient, dispatch]);
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
