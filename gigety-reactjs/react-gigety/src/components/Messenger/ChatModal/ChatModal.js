import React, { useContext, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
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
import { GIGETY_MESSENGER_WS_URL } from '../../../constants/index';

const ChatModal = ({ profile }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, profile.userId);
	console.log('MESSSAGES :::::::: ', messages);
	const contact = mapProfileToContact(profile, giguser);
	const sendChatMessage = useRef(null);
	//useMessenger(giguser, contact);
	//const { stomp, SockJS } = useContext(StompClientContext);
	const dispatch = useDispatch();
	useEffect(() => {
		let subId = '';
		const sockJS = new SockJS(GIGETY_MESSENGER_WS_URL);
		const stompClient = Stomp.over(() => sockJS);

		const onConnect = () => {
			const onMessageRecieved = (msg) => {
				const notification = JSON.parse(msg.body);
				if (contact.contactId === notification.senderId) {
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const { id } = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
			subId = id;
		};
		const onError = (error) => {
			console.log('ERRRRRRRRRRRRRRR : ', error);
		};
		stompClient.reconnectDelay = 1000;
		stompClient.debug = (str) => console.log(str);
		stompClient.connect({}, onConnect, onError);
		sendChatMessage.current = (message) => {
			console.log('LOSSSSSER : ', stompClient);
			try {
				console.log('SENDDDDDDDDDING ', message);
				stompClient.send('/msg/chat', {}, JSON.stringify(message));
				dispatch(updateChatMessages(message));
			} catch (error) {
				console.log.error('Errrrrror sending message ', error);
			}
		};

		return () => {
			console.log('u suk');
			if (stompClient.connected) {
				console.log('Unsubscribing :: ', subId);
				stompClient.unsubscribe(subId);
			}
		};
	}, [giguser, contact, dispatch]);

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
											<List.Item key={msg.id}>
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
