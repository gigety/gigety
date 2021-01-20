import React, { useContext, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import UserLabel from '../../User/UserLabel';
import ProfileUserImage from '../../Profile/ProfileUserImage';
import ScrollToBottom from 'react-scroll-to-bottom';
import { Button, Image, Input, List } from 'semantic-ui-react';
import './ChatModal.css';
import { StompClientContext } from 'contexts/StompClientContext';
import { use121ChatMessages } from 'redux/hooks/useMessages';

const ChatModal = ({ profile }) => {
	console.log(profile);
	const { giguser } = useSelector((state) => state.giguser);
	console.log(`giguser :: `, giguser);
	const messages = use121ChatMessages(giguser.id, profile.id);
	const [text, setText] = useState('');
	const { stompClient, sendChatMessage } = useContext(StompClientContext);
	useEffect(() => {
		const id = stompClient.subscribe(`/user/${profile.id}/queue/messages`, onMessageRecieved);
		//const id = stompClient.subscribe(`/queue/messages/${profile.id}`, onMessageRecieved);
		console.log(id);
		return () => {
			console.log(`here we unsubscibe to id ${id}, you best check this is proper way to unsubscribe`);
			stompClient.unsubscribe(id);
		};
	}, [profile, stompClient]);

	const onMessageRecieved = (msg) => {
		console.log(`received ::::: msg ${msg}`, msg);
		const notification = JSON.parse(msg.body);
		console.log('Notification :: ', notification);
	};
	const sendTheMessage = (msg) => {
		if (msg.trim() !== '') {
			const message = {
				senderId: giguser.id,
				recipientId: profile.id,
				senderName: giguser.name,
				recipientName: profile.email,
				content: msg,
				timestamp: new Date(),
			};
			console.log('sending msg: ', msg);
			sendChatMessage(message);
			console.log('msg sent', message);
		}
	};
	const messageSent = () => {};
	console.log(profile);
	console.log('messages :::: ', messages);
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

						<Input placeholder="Enter Message" action fluid>
							<input value={text} onChange={(e) => setText(e.target.value)} />
							<Button
								onClick={() => {
									sendTheMessage(text);
									setText('');
								}}
							>
								Send Message
							</Button>
						</Input>
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
