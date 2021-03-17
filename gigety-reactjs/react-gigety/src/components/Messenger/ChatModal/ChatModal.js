import React, { useContext, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import ScrollToBottom from 'react-scroll-to-bottom';
import { Button, List } from 'semantic-ui-react';
import 'reactjs-popup/dist/index.css';
import UserLabel from '../../User/UserLabel';
import ProfileUserImage from '../../Profile/ProfileUserImage';
import './ChatModal.css';
import { StompClientContext } from 'contexts/StompClientContext';
import { use121ChatMessages } from 'redux/hooks/useMessages';
import { mapProfileToContact } from 'utils/objectMapper';
import { findMessagesFor121Chat } from 'redux/actions/messagesAction';
import MessageInput from '../MessageInput/MessageInput';
import { GIGETY_MESSENGER_URL } from '../../../constants/index';

const ChatModal = ({ profile }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, profile.userId);
	const contact = mapProfileToContact(profile, giguser);
	//useMessenger(giguser, contact);
	//const { stomp, SockJS } = useContext(StompClientContext);
	const dispatch = useDispatch();
	let subId;
	useEffect(() => {
		const stomp = require('stompjs');
		let SockJS = require('sockjs-client');
		SockJS = new SockJS(GIGETY_MESSENGER_URL + '/ws');
		SockJS.onclose = () => {
			console.log(`here we unsubscibe to id ${subId}, you best check this is proper way to unsubscribe`);
			stompClient.unsubscribe(subId);
		};
		const stompClient = stomp.over(SockJS);
		const onError = (error) => {
			console.log('ERRRRRRRRRRRRRRR : ', error);
		};
		const onConnected = () => {
			const onMessageRecieved = (msg) => {
				console.log('RECEIVED MESSAGE +++++++++++++++++++', contact);
				const notification = JSON.parse(msg.body);
				if (contact.contactId === notification.senderId) {
					console.log('WE FOUND A MATCH');
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const { id } = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);
			console.log('IIIISSSS COOONN NNEEECCCCTTTTED v:: ', stompClient.connected);
			subId = id;
		};
		stompClient.connect({}, onConnected, onError);
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
						<MessageInput activeContact={contact} giguser={giguser}></MessageInput>
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
