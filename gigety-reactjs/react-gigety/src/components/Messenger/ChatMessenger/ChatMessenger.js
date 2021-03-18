import React, { useEffect, useRef } from 'react';
import { PropTypes } from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import { GIGETY_MESSENGER_URL } from '../../../constants';
const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);
	console.log('Active COntact :::: ', activeContact);
	const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	//useMessenger(giguser, activeContact);
	//onst { stompClient } = useContext(StompClientContext);
	const sendChatMessage = useRef(null);
	const dispatch = useDispatch();
	useEffect(() => {
		let subId = '';
		const stomp = require('stompjs');
		let SockJS = require('sockjs-client');
		SockJS = new SockJS(GIGETY_MESSENGER_URL + '/ws');
		SockJS.onopen = () => {
			console.log('SOCKJS is CONNECTED AND OPEN FOR MESSAGING');
		};
		SockJS.onmessage = (message) => {
			console.log('SOCKJS RECIEVED A MESSAGE :: ', message);
		};
		SockJS.onclose = () => {
			stompClient.unsubscribe(subId);
		};
		const stompClient = stomp.over(SockJS);
		stompClient.debug = (f) => f;
		const onError = (error) => {
			console.log('ERRRRRRRRRRRRRRR : ', error);
		};
		sendChatMessage.current = (message) => {
			stompClient.send('/msg/chat', {}, JSON.stringify(message));
			dispatch(updateChatMessages(message));
		};
		const onConnected = () => {
			const onMessageRecieved = (msg) => {
				const notification = JSON.parse(msg.body);
				if (activeContact.contactId === notification.senderId) {
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const { id } = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
			subId = id;
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
			<MessageInput
				activeContact={activeContact}
				giguser={giguser}
				sendChatMessage={sendChatMessage.current}
			></MessageInput>
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
