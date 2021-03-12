import React, { useContext, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import ScrollToBottom from 'react-scroll-to-bottom';
import { Button, Input, List } from 'semantic-ui-react';
import 'reactjs-popup/dist/index.css';
import UserLabel from '../../User/UserLabel';
import ProfileUserImage from '../../Profile/ProfileUserImage';
import './ChatModal.css';
import { StompClientContext } from 'contexts/StompClientContext';
import { use121ChatMessages, useMessenger } from 'redux/hooks/useMessages';
import { mapProfileToContact } from 'utils/objectMapper';

const ChatModal = ({ profile }) => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = use121ChatMessages(giguser.id, profile.userId);
	const [text, setText] = useState('');
	const { sendChatMessage } = useContext(StompClientContext);
	const contact = mapProfileToContact(profile, giguser);
	useMessenger(giguser, contact);
	const sendTheMessage = (msg) => {
		if (msg.trim() !== '') {
			const message = {
				senderId: giguser.id,
				recipientId: profile.userId,
				senderName: giguser.name,
				recipientName: profile.email,
				content: msg,
				timestamp: new Date(),
			};
			sendChatMessage(message);
		}
	};
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
