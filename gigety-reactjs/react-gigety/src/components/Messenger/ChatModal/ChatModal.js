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

const ChatModal = ({ profile }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, profile.userId);
	const contact = mapProfileToContact(profile, giguser);
	//useMessenger(giguser, contact);
	const { stompClient } = useContext(StompClientContext);
	const dispatch = useDispatch();
	console.log('1111111111112222223333334344444444');
	useEffect(() => {
		if (stompClient.connected) {
			const onMessageRecieved = (msg) => {
				console.log('RECEIVED MESSAGE +++++++++++++++++++', contact);
				const notification = JSON.parse(msg.body);
				if (contact.contactId === notification.senderId) {
					console.log('WE FOUND A MATCH');
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const id = stompClient.subscribe(`/user/${giguser.id}/topic/messages`, onMessageRecieved);
			console.log('SUBSCRIPTION ID -- ', id);
			return () => {
				console.log(`here we unsubscibe to id ${id}, you best check this is proper way to unsubscribe`);
				stompClient.unsubscribe(id.id);
			};
		}
	}, [giguser, contact, stompClient, dispatch]);

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
