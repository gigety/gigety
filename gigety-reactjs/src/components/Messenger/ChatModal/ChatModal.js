import React, { useContext, useEffect, useRef, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import ScrollToBottom from 'react-scroll-to-bottom';
import { Button, List } from 'semantic-ui-react';
import 'reactjs-popup/dist/index.css';
import UserLabel from '../../User/UserLabel';
import './ChatModal.css';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { mapProfileToContact } from 'utils/objectMapper';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import MessageInput from '../MessageInput/MessageInput';
import { StompRXClientContext } from 'contexts/StompRXClientContext';
import { useMessenger } from '../../../redux/hooks/useMessages';
import UserAvatar from '../UserAvatar/UserAvatar';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
const ChatModal = ({ profile }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, profile.userId);
	const contact = useMemo(() => mapProfileToContact(profile, giguser), [profile, giguser]);
	const { getStompClient } = useContext(StompRXClientContext);
	const sendChatMessage = useRef(null);
	//	useMessenger(giguser, contact, sendChatMessage);
	const dispatch = useDispatch();
	useEffect(() => {
		const stompClient = getStompClient();
		sendChatMessage.current = (message) => {
			stompClient.publish({ destination: '/msg/chat', body: JSON.stringify(message) });
			dispatch(updateChatMessages(message));
		};

		const onMessageRecieved = (msg) => {
			//TODO: get the contact and user from getState() and make this a custom hook or context
			const notification = JSON.parse(msg.body);
			if (contact.contactId === notification.senderId) {
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
	}, [giguser, dispatch, contact.contactId, getStompClient]);

	const userAvatar = useMemo(() => <UserAvatar size="mini" user={giguser} />, [giguser]);
	const contactAvatar = useMemo(() => <ContactAvatar size="mini" contact={contact} />, [contact]);

	return (
		<Popup
			trigger={
				<div>
					<UserLabel userImageUrl={profile.userImageUrl} email={profile.email} />
				</div>
			}
			modal
			nested
		>
			{(close) => (
				<div className="modal">
					<div className="header">Send Direct Message to {profile.email} </div>
					<div className="content">
						<ScrollToBottom className="messages">
							<List>
								{messages
									? messages.map((msg) => {
											const avatar =
												msg.senderId.toString() === giguser.id.toString()
													? userAvatar
													: contactAvatar;
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
							activeContact={contact}
							giguser={giguser}
							sendChatMessage={sendChatMessage.current}
						></MessageInput>
					</div>
					<div className="actions">
						<Button className="button"> Go to Messages </Button>
						<Button className="button" onClick={close}>
							Close
						</Button>
					</div>
				</div>
			)}
		</Popup>
	);
};

export default ChatModal;
