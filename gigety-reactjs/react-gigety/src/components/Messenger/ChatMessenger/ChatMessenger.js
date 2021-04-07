import React, { useContext, useEffect, useRef } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import { StompRXClientContext } from 'contexts/StompRXClientContext';
import { map } from 'rxjs/Operator';
import UserAvatar from '../UserAvatar/UserAvatar';
const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);

	const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	console.log(messages);
	const { getStompClient } = useContext(StompRXClientContext);
	const sendChatMessage = useRef(null);
	const dispatch = useDispatch();
	useEffect(() => {
		const stompClient = getStompClient();
		sendChatMessage.current = (message) => {
			stompClient.publish({ destination: '/msg/chat', body: JSON.stringify(message) });
			dispatch(updateChatMessages(message));
		};

		const onMessageRecieved = (msg) => {
			console.log(msg);
			//TODO: get the contact and user from getState() and make this a custom hook or context
			const notification = JSON.parse(msg.body);
			if (activeContact.contactId === notification.senderId) {
				dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
			}
		};
		console.log('Gigety SubScribing .......');
		const rxSubsciption = stompClient
			.watch(`/user/${giguser.id}/queue/messages`)
			.subscribe((payload) => onMessageRecieved(payload));
		return () => {
			if (stompClient) {
				console.log('UNSUBSCRIBING ...');
				rxSubsciption.unsubscribe();
			}
		};
	}, [giguser, dispatch, activeContact.contactId, getStompClient]);
	return (
		<>
			<ScrollToBottom className="messages">
				<List>
					{messages
						? messages.map((msg) => {
								const ret =
									msg.senderId.toString() === giguser.id.toString() ? (
										<List.Item key={msg.id}>
											<UserAvatar size="mini" user={giguser} />
											<List.Content>
												<List.Description>{msg.content}</List.Description>
											</List.Content>
										</List.Item>
									) : (
										<List.Item key={msg.id}>
											<ContactAvatar size="mini" contact={activeContact} />
											<List.Content>
												<List.Description>{msg.content}</List.Description>
											</List.Content>
										</List.Item>
									);
								return ret;
						  })
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
