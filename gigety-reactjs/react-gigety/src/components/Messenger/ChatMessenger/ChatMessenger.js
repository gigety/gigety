import React, { useContext, useEffect, useRef } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import { StompClientContext } from 'contexts/StompClientContext';
const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);

	console.log('Active COntact :::: ', activeContact);
	const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	const { getStompClient } = useContext(StompClientContext);
	const sendChatMessage = useRef(null);
	const dispatch = useDispatch();
	useEffect(() => {
		const stompClient = getStompClient();
		console.log('Got Stomp Client', stompClient);
		sendChatMessage.current = (message) => {
			stompClient.publish({ destination: '/msg/chat', body: JSON.stringify(message) });
			dispatch(updateChatMessages(message));
		};

		const onMessageRecieved = (msg) => {
			//TODO: get the contact and user from getState() and make this a custom hook or context
			console.log('++++++RECIEVED MSG++++++', msg);
			const notification = JSON.parse(msg.body);
			if (activeContact.contactId === notification.senderId) {
				dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
			}
		};
		console.log('Gigety SubScribing .......');
		const { id } = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
		return () => {
			if (stompClient) {
				console.log('UNSUBSCRIBING ...');
				stompClient.unsubscribe(id);
			}
		};
	}, [giguser, dispatch, activeContact.contactId, getStompClient]);
	return (
		<>
			<ScrollToBottom className="messages">
				<List>
					{messages
						? messages.map((msg) => (
								<List.Item key={msg.id}>
									<ContactAvatar size="large" contact={activeContact} />
									<List.Content>
										<List.Description>{msg.content}</List.Description>
									</List.Content>
								</List.Item>
						  ))
						: ''}
				</List>
			</ScrollToBottom>
			<MessageInput
				activeContact={activeContact}
				giguser={giguser}
				sendChatMessage={sendChatMessage.current}
			></MessageInput>
		</>
	);
};

ChatMessenger.propTypes = {
	activeContact: PropTypes.shape({
		contactId: PropTypes.string,
		contactName: PropTypes.string,
		contactImageUrl: PropTypes.string,
	}),
};
ChatMessenger.defaultProps = {
	activeContact: { contactId: '0' },
};
export default ChatMessenger;
