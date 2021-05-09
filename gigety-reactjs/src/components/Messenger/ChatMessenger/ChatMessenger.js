import React, { useContext, useEffect, useMemo, useRef } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import { StompRXClientContext } from 'contexts/StompRXClientContext';
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
	const userAvatar = useMemo(() => <UserAvatar size="mini" user={giguser} />, [giguser]);
	const contactAvatar = useMemo(() => <ContactAvatar size="mini" contact={activeContact} />, [activeContact]);
	return (
		<>
			<ScrollToBottom className="messages">
				<List>
					{messages
						? messages.map((msg) => {
								const avatar =
									msg.senderId.toString() === giguser.id.toString() ? userAvatar : contactAvatar;
								return (
									<List.Item key={msg.id}>
										{avatar}
										<List.Content>
											<List.Description>{msg.content}</List.Description>
										</List.Content>
									</List.Item>
								);
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
