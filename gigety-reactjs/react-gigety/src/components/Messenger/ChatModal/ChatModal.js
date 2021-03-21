import React, { useContext, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import ScrollToBottom from 'react-scroll-to-bottom';
import { Button, List } from 'semantic-ui-react';
import 'reactjs-popup/dist/index.css';
import UserLabel from '../../User/UserLabel';
import ProfileUserImage from '../../Profile/ProfileUserImage';
import './ChatModal.css';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { mapProfileToContact } from 'utils/objectMapper';
import { findMessagesFor121Chat, updateChatMessages } from 'redux/actions/messagesAction';
import MessageInput from '../MessageInput/MessageInput';
import { GIGETY_MESSENGER_URL } from '../../../constants/index';
import { StompClientContext } from 'contexts/StompClientContext';

const ChatModal = ({ profile }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, profile.userId);
	const contact = mapProfileToContact(profile, giguser);
	//useMessenger(giguser, contact);
	const { getStompClient, addStompEventListener, stompEventTypes } = useContext(StompClientContext);
	const sendChatMessage = useRef(null);
	const dispatch = useDispatch();
	useEffect(() => {
		const stompClient = getStompClient();
		console.log('Got Stomp Client', stompClient);
		let subId = '';
		sendChatMessage.current = (message) => {
			stompClient.publish({ destination: '/msg/chat', body: JSON.stringify(message) });
			dispatch(updateChatMessages(message));
		};

		const onConnected = () => {
			const onMessageRecieved = (msg) => {
				//TODO: get the contact and user from getState() and make this a custom hook or context
				console.log('++++++RECIEVED MSG++++++', msg);
				const notification = JSON.parse(msg.body);
				if (contact.contactId === notification.senderId) {
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			console.log('Gigety SubScribing .......');
			const { id } = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
			subId = id;
		};
		addStompEventListener(stompEventTypes.Connect, onConnected);
		return () => {
			if (stompClient) {
				console.log('UNSUBSCRIBING ...');
				stompClient.unsubscribe(subId);
			}
		};
	}, [giguser, dispatch, contact, addStompEventListener, getStompClient, stompEventTypes]);

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
									? messages.map((msg) => (
											<List.Item>
												<ProfileUserImage size="mini" profile={profile} />
												<List.Content>
													<List.Header as="a">{profile.profileName}</List.Header>
													<List.Description>{msg.content}</List.Description>
												</List.Content>
											</List.Item>
									  ))
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
						<Button
							className="button"
							onClick={() => {
								console.log('modal closed ');
								close();
							}}
						>
							Close
						</Button>
					</div>
				</div>
			)}
		</Popup>
	);
};

export default ChatModal;
