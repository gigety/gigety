import React, { useEffect, useRef } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import { GIGETY_MESSENGER_WS_URL } from '../../../constants';
const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	//useMessenger(giguser, activeContact);
	//onst { stompClient } = useContext(StompClientContext);
	const sendChatMessage = useRef(null);
	const dispatch = useDispatch();
	const stompClient = useRef(null);
	useEffect(() => {
		let subId = '';
		const sockJS = new SockJS(GIGETY_MESSENGER_WS_URL);
		stompClient.current = Stomp.over(() => sockJS);

		const onConnect = () => {
			const onMessageRecieved = (msg) => {
				console.log('RECEIVED :::', msg);
				const notification = JSON.parse(msg.body);
				if (activeContact.contactId === notification.senderId) {
					console.log('updating messages');
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const { id } = stompClient.current.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
			subId = id;
		};
		const onError = (error) => {
			console.log('ERRRRRRRRRRRRRRR : ', error);
		};
		stompClient.current.reconnectDelay = 1000;
		stompClient.current.debug = (str) => console.log(str);
		stompClient.current.connect({}, onConnect, onError);
		sendChatMessage.current = (message) => {
			console.log('SENDDDDDDDDDING ', message);
			stompClient.current.send('/msg/chat', {}, JSON.stringify(message));
			dispatch(updateChatMessages(message));
		};

		return () => {
			console.log('u suk');
			if (stompClient.current) {
				console.log('Unsubscribing :: ', subId);
				stompClient.current.unsubscribe(subId);
			}
		};
	}, [giguser, dispatch, activeContact, stompClient]);
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
	}),
};
ChatMessenger.defaultProps = {
	activeContact: { contactId: '0' },
};
export default ChatMessenger;
